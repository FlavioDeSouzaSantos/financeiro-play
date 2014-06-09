/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financeiro.models.entity;

import br.com.financeiro.models.enums.SituacaoGasto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author User
 */
@Entity
@Table(name="gasto")
@TableGenerator(name = "chaveGasto", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "gasto", allocationSize = 1)
public class Gasto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="chaveGasto")
    private Integer id;
    
    @NotNull(message="O usuário não foi informado.")
    @JoinColumn(name="id_usuario", referencedColumnName="id", nullable=false)
    @ManyToOne(fetch= FetchType.LAZY)
    private Usuario usuario;
    
    @NotNull(message="O fornecedor não foi informado.")
    @JoinColumn(name="id_fornecedor", referencedColumnName="id", nullable=false)
    @ManyToOne(fetch= FetchType.LAZY)
    private Fornecedor fornecedor;
    
    @Size(min=5, max=200, message="A descrição deve possuir no mínimo 5 e no máximo 200 caracteres.")
    @Column(name="descricao", length=200, nullable=false)
    private String descricao;
    
    @NotNull(message="O tipo do gasto não foi informado.")
    @JoinColumn(name="id_tipo", referencedColumnName="id", nullable=false)
    @ManyToOne(fetch= FetchType.LAZY)
    private TipoGasto tipo;
    
    @NotNull(message="A categoria não foi informada.")
    @JoinColumn(name="id_categoria", referencedColumnName="id", nullable=false)
    @ManyToOne(fetch= FetchType.LAZY)
    private CategoriaGasto categoria;
    
    @NotNull(message="A data da realização não foi informada.")
    @Column(name="realizacao", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date realizacao;
    
    @Column(name="valor", precision=2, nullable=false)
    private double valor;
    
    @Column(name="valor_juros", precision=2)
    private double valorJuros;
    
    @Column(name="valor_desconto", precision=2)
    private double valorDesconto;
    
    @Column(name="valor_pago", precision=2)
    private double valorPago;
    
    @Enumerated(EnumType.STRING)
    @Column(name="situacao", length=20, nullable=false)
    private SituacaoGasto situacao;

    @OneToMany(mappedBy="gasto", fetch= FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval=true)
    private List<ParcelaGasto> parcelas;

    public Gasto() {
    }

    public Gasto(Integer id) {
        this.id = id;
    }

    public void gerarParcelas(int numParcelas) throws Exception{
        if(realizacao != null && valor > 0 && tipo != null){
            if(numParcelas == 0){
                throw new Exception("A quantidade de parcelas informada não pode ser igual a zero.");
            }
            
            if(parcelas != null){
                parcelas.clear();
            }else{
                parcelas = new ArrayList<ParcelaGasto>();
            }
            
            if(tipo.isLiquido()){
                this.setValorPago(valor - valorDesconto);
                //criando a parcela
                ParcelaGasto parcela = new ParcelaGasto();
                parcela.setGasto(this);
                parcela.setNumero(1);
                parcela.setVencimento(realizacao);
                parcela.setValor(valor);
                parcela.setValorDesconto(valorDesconto);
                //criando o pagamento da parcela
                PagamentoParcelaGasto pagamento = new PagamentoParcelaGasto();
                pagamento.setPagamento(realizacao);                
                pagamento.setValorPago(valorPago);                
                parcela.addPagamento(pagamento);
                parcelas.add(parcela);
                situacao = SituacaoGasto.LIQUIDADO;
            }else{                
                Calendar cVencimento = Calendar.getInstance();
                cVencimento.setTime(realizacao);
                
                double valorParcela = valor / numParcelas;
                double valorDescontoParcela = valorDesconto / numParcelas;
                
                for(int i=0; i<numParcelas; i++){
                    cVencimento.add(Calendar.DAY_OF_MONTH, 30);
                    
                    ParcelaGasto parcela = new ParcelaGasto();
                    parcela.setGasto(this);
                    parcela.setNumero(i+1);
                    parcela.setVencimento(cVencimento.getTime());
                    parcela.setValor(valorParcela);
                    parcela.setValorDesconto(valorDescontoParcela);
                    parcelas.add(parcela);
                }
                situacao = SituacaoGasto.ABERTO;
            }            
        }
    }
    
    public void excluirParcela(ParcelaGasto parcela){
        if(parcela != null && situacao != null && situacao.equals(SituacaoGasto.ABERTO) 
                && parcelas != null && !parcelas.isEmpty()){
            if(parcelas.remove(parcela)){
                //recalculando valores
                double valorDasParcelas = 0.0;
                double valorDescontoDasParcelas = 0.0;
                double valorJurosDasParcelas = 0.0;
                double valorPagoDasParcelas = 0.0;

                for (ParcelaGasto parcelaGasto : parcelas) {
                    valorDasParcelas += parcelaGasto.getValor();
                    valorDescontoDasParcelas += parcelaGasto.getValorDesconto();
                    valorJurosDasParcelas += parcelaGasto.getValorJuros();
                    valorPagoDasParcelas += parcelaGasto.getValorPago();
                }

                this.valor = valorDasParcelas;
                this.valorDesconto = valorDescontoDasParcelas;
                this.valorJuros = valorJurosDasParcelas;
                this.valorPago = valorPagoDasParcelas;   
                calculaSituacao();
            }
        }
    }
    
    public void calculaSituacao(){
        if(situacao != null && !situacao.equals(SituacaoGasto.CANCELADO)
                && parcelas != null && !parcelas.isEmpty()){
            int qtdPagas=0;
            for (ParcelaGasto parcelaGasto : parcelas){
                if(parcelaGasto.getValorEmAberto() == 0){
                    qtdPagas ++;
                }
            }
            if(qtdPagas == parcelas.size()){
                situacao = SituacaoGasto.LIQUIDADO;
            }else{
                situacao = SituacaoGasto.ABERTO;
            }
        }
    }
    
    public boolean isComRegistroPagamento(){
        if(parcelas != null && !parcelas.isEmpty()){
            boolean possui = false;
            for(ParcelaGasto parcelaGasto : parcelas){
                if(parcelaGasto.getPagamentos() != null && !parcelaGasto.getPagamentos().isEmpty()){
                    possui = true;
                    break;
                }
            }
            return possui;
        }else{
            return false;
        }
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CategoriaGasto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaGasto categoria) {
        this.categoria = categoria;
    }

    public TipoGasto getTipo() {
        return tipo;
    }

    public void setTipo(TipoGasto tipo) {
        this.tipo = tipo;
    }

    public Date getRealizacao() {
        return realizacao;
    }

    public void setRealizacao(Date realizacao) {
        this.realizacao = realizacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(double valorJuros) {
        this.valorJuros = valorJuros;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public SituacaoGasto getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoGasto situacao) {
        this.situacao = situacao;
    }

    public List<ParcelaGasto> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<ParcelaGasto> parcelas) {
        this.parcelas = parcelas;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Gasto other = (Gasto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}

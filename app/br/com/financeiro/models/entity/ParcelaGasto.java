/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financeiro.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 *
 * @author User
 */
@Entity
@Table(name="parcela_gasto")
@TableGenerator(name = "chaveParcelaGasto", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "parcela_gasto", allocationSize = 1)
public class ParcelaGasto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="chaveParcelaGasto")
    private Integer id;
    
    @JoinColumn(name="id_gasto", referencedColumnName="id", nullable=false)
    @ManyToOne(fetch= FetchType.LAZY)
    private Gasto gasto;
    
    @Column(name="numero", nullable=false)
    private int numero;
    
    @Column(name="vencimento", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date vencimento;
    
    @Column(name="valor", precision=2, nullable=false)
    private double valor;
    
    @Column(name="valor_juros", precision=2, nullable=false)
    private double valorJuros;
    
    @Column(name="valor_desconto", precision=2, nullable=false)
    private double valorDesconto;
    
    @OneToMany(mappedBy="parcelaGasto", fetch= FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval=true)
    private List<PagamentoParcelaGasto> pagamentos;
    
    public ParcelaGasto() {
    }

    public ParcelaGasto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
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

    public List<PagamentoParcelaGasto> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<PagamentoParcelaGasto> pagamentos) {
        this.pagamentos = pagamentos;
    }

    //metodos calculados
    public boolean estaPago(){
        return getValorPago() >= getValorAPagar();
    }
    
    //retorna a data do ultimo pagamento realizado
    public Date getPagamento() {
        if(pagamentos != null && !pagamentos.isEmpty()){
            Date ultimoPagamento=null;
            for(PagamentoParcelaGasto pagamentoParcelaGasto : pagamentos){
                if(ultimoPagamento == null){
                    ultimoPagamento = pagamentoParcelaGasto.getPagamento();
                }else if(pagamentoParcelaGasto.getPagamento().after(ultimoPagamento)){
                    ultimoPagamento = pagamentoParcelaGasto.getPagamento();
                }
            }
            return ultimoPagamento;
        }else{
            return null;
        }
    }

    //calcula o total pago da parcela
    public double getValorPago() {
        double valorPago=0.0;
        if(pagamentos != null && !pagamentos.isEmpty()){
            for(PagamentoParcelaGasto pagamentoParcelaGasto : pagamentos){
                valorPago += pagamentoParcelaGasto.getValorPago();
            }
        }
        return valorPago;
    }

    //calcula o total em aberto da parcela
    public double getValorEmAberto(){
        if(getValorAPagar() > getValorPago()){
            return getValorAPagar() - getValorPago();
        }else{
            return 0.0;
        }
    }
    
    //calcula o valor que tem que ser pago
    public double getValorAPagar(){
        return (valor - valorDesconto)+valorJuros;
    }
    
    //adiciona um pagamento para a parcela
    public void addPagamento(PagamentoParcelaGasto pagamentoParcelaGasto){
        if(pagamentoParcelaGasto == null){
            return;
        }
        
        if(pagamentos == null){
            pagamentos = new ArrayList<PagamentoParcelaGasto>();
        }
        pagamentoParcelaGasto.setParcelaGasto(this);
        pagamentos.add(pagamentoParcelaGasto);
        calculaValorJuros();
    }
    
    //calcula o valor pago dos juros
    public void calculaValorJuros(){
        if(getValorPago() > (valor - valorDesconto)){
            this.valorJuros = getValorPago() - (valor - valorDesconto);
        }else{
            this.valorJuros = 0.0;
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final ParcelaGasto other = (ParcelaGasto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}

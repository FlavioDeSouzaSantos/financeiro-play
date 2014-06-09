/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financeiro.models.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author User
 */
@Entity
@Table(name="pagamento_parcela_gasto")
@TableGenerator(name = "chavePagamentoParcelaGasto", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "pagamento_parcela_gasto", allocationSize = 1)
public class PagamentoParcelaGasto implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="chavePagamentoParcelaGasto")
    private Integer id;
    
    @JoinColumn(name="id_parcela_gasto", referencedColumnName="id", nullable=false)
    @ManyToOne(fetch= FetchType.LAZY)
    private ParcelaGasto parcelaGasto;
    
    @Column(name="pagamento", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date pagamento;
    
    @Column(name="valor_pago", precision=2, nullable=false)
    private double valorPago;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ParcelaGasto getParcelaGasto() {
        return parcelaGasto;
    }

    public void setParcelaGasto(ParcelaGasto parcelaGasto) {
        this.parcelaGasto = parcelaGasto;
    }

    public Date getPagamento() {
        return pagamento;
    }

    public void setPagamento(Date pagamento) {
        this.pagamento = pagamento;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final PagamentoParcelaGasto other = (PagamentoParcelaGasto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }        
}

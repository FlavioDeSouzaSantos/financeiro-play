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
@Table(name="receita")
@TableGenerator(name = "chaveReceita", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "receita", allocationSize = 1)
public class Receita implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="chaveReceita")
    private Integer id;
    
    @JoinColumn(name="id_usuario", referencedColumnName="id", nullable=false)
    @ManyToOne(fetch= FetchType.LAZY)
    private Usuario usuario;
    
    @JoinColumn(name="id_origem_receita", referencedColumnName="id", nullable=false)
    @ManyToOne(fetch= FetchType.LAZY)
    private OrigemReceita origem;
    
    @Column(name="recebimento")
    @Temporal(TemporalType.DATE)
    private Date recebimento;
    
    @Column(name="realizacao", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date realizacao;
    
    @Column(name="valor", precision=2, nullable=false)
    private Double valor;

    public Receita() {
    }

    public Receita(Integer id) {
        this.id = id;
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

    public OrigemReceita getOrigem() {
        return origem;
    }

    public void setOrigem(OrigemReceita origem) {
        this.origem = origem;
    }

    public Date getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(Date recebimento) {
        this.recebimento = recebimento;
    }

    public Date getRealizacao() {
        return realizacao;
    }

    public void setRealizacao(Date realizacao) {
        this.realizacao = realizacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Receita other = (Receita) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
        
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financeiro.models.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Size;

/**
 *
 * @author User
 */
@Entity
@Table(name="tipo_gasto")
@TableGenerator(name = "chaveTipoGasto", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "tipo_gasto", allocationSize = 1)
public class TipoGasto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="chaveTipoGasto")
    private Integer id;
    
    @Size(min=5, max=50, message="A descrição deve possuir no mínimo 5 e no máximo 50 caracteres.")
    @Column(name="descricao", length=50, nullable=false)
    private String descricao;
    
    @Column(name="liquido")
    private boolean liquido;
    
    @OneToMany(mappedBy="tipo", fetch= FetchType.LAZY)
    private List<Gasto> gastos;
    
    public TipoGasto() {
    }

    public TipoGasto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public boolean isLiquido() {
        return liquido;
    }

    public void setLiquido(boolean liquido) {
        this.liquido = liquido;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final TipoGasto other = (TipoGasto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}

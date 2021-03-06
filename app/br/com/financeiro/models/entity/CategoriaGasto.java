/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financeiro.models.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.financeiro.models.enums.TipoCategoriaGasto;

/**
 *
 * @author User
 */
@Entity
@Table(name="categoria_gasto")
@TableGenerator(name = "chaveCategoriaGasto", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "categoria_gasto", allocationSize = 1)
public class CategoriaGasto implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="chaveCategoriaGasto")
    private Integer id;
    
    @Size(min=5, max=30, message="A descrição deve possuir no mínimo 5 e no máximo 30 caracteres.")
    @Column(name="descricao", length=30, nullable=false)
    private String descricao;
    
    @NotNull(message="O tipo não foi informado.")
    @Column(name="tipo", length=8)
    private TipoCategoriaGasto tipo;
    
    @JoinColumn(name="id_categoria_pai", referencedColumnName="id")
    @ManyToOne(fetch= FetchType.LAZY)
    private CategoriaGasto categoriaPai;
    
    @OneToMany(mappedBy="categoriaPai", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    private List<CategoriaGasto> categoriasFilhas;
    
    public CategoriaGasto() {
    }

    public CategoriaGasto(Integer id) {
        this.id = id;
    }

    public CategoriaGasto(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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

    public TipoCategoriaGasto getTipo() {
		return tipo;
	}

	public void setTipo(TipoCategoriaGasto tipo) {
		this.tipo = tipo;
	}

	public CategoriaGasto getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(CategoriaGasto categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public List<CategoriaGasto> getCategoriasFilhas() {
		return categoriasFilhas;
	}

	public void setCategoriasFilhas(List<CategoriaGasto> categoriasFilhas) {
		this.categoriasFilhas = categoriasFilhas;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final CategoriaGasto other = (CategoriaGasto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}

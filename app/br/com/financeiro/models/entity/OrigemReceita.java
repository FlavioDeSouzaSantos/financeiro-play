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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author User
 */
@Entity
@Table(name="origem_receita")
@TableGenerator(name = "chaveOrigemReceita", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "origem_receita", allocationSize = 1)
public class OrigemReceita implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="chaveOrigemReceita")
    private Integer id;
   
    @Column(name="descricao", length=70, nullable=false)
    private String descricao;
    
    @Column(name="telefone", length=13)
    private String telefone;
    
    @JoinColumn(name="id_endereco", referencedColumnName="id")
    @ManyToOne(fetch= FetchType.LAZY)
    private Endereco endereco;

    @OneToMany(mappedBy="origem", fetch= FetchType.LAZY)
    private List<Receita> receitas;
    
    public OrigemReceita() {
    }

    public OrigemReceita(Integer id) {
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final OrigemReceita other = (OrigemReceita) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author User
 */
@Entity
@Table(name="bairro")
@TableGenerator(name = "chaveBairro", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "bairro", allocationSize = 1)
public class Bairro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "chaveBairro")
    private Integer id;
    
    @Size(min=5, max=70, message="O nome deve possuir no mínimo 5 e no máximo 70 caracteres.")
    @Column(name="nome", length=70, nullable=false)
    private String nome;
    
    @NotNull(message="O município não foi informado.")
    @JoinColumn(name="id_municipio", referencedColumnName="id")
    @ManyToOne(fetch= FetchType.LAZY)
    private Municipio municipio;
    
    @OneToMany(mappedBy="bairro", fetch= FetchType.LAZY)
    private List<Endereco> enderecos;

    public Bairro() {
    }

    public Bairro(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Bairro other = (Bairro) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}

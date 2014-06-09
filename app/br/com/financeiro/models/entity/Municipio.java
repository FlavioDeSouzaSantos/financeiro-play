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
@Table(name="municipio")
@TableGenerator(name = "chaveMunicipio", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "municipio", allocationSize = 1)
public class Municipio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "chaveMunicipio")
    private Integer id;
    
    @Column(name="nome", length=70, nullable=false)
    private String nome;
    
    @JoinColumn(name="id_estado", referencedColumnName="id", nullable=false)
    @ManyToOne(fetch= FetchType.LAZY)
    private Estado estado;

    @OneToMany(mappedBy="municipio", fetch= FetchType.LAZY)
    private List<Bairro> bairros;

    public Municipio() {
    }

    public Municipio(Integer id) {
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Bairro> getBairros() {
        return bairros;
    }

    public void setBairros(List<Bairro> bairros) {
        this.bairros = bairros;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Municipio other = (Municipio) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
        
}

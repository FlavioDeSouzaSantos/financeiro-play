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
@Table(name="endereco")
@TableGenerator(name = "chaveEndereco", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "endereco", allocationSize = 1)
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="chaveEndereco")
    private Integer id;
    
    @Size(min=5, max=70, message="O logradouro deve possuir no mínimo 5 e no máximo 70 caracteres.")
    @Column(name="logradouro", length=70, nullable=false)
    private String logradouro;
    
    @Column(name="numero")
    private Integer numero;
    
    @Column(name="cep", length=9)
    private String cep;
    
    @Column(name="complemento", length=70)
    private String complemento;
    
    @Column(name="referencia", length=250)
    private String referencia;
    
    @Column(name="latitude", length=20)
    private String latitude;
    
    @Column(name="longitude", length=20)
    private String longitude;
    
    @NotNull(message="O bairro não foi informado.")
    @JoinColumn(name="id_bairro", referencedColumnName="id", nullable=false)
    @ManyToOne(fetch= FetchType.LAZY)
    private Bairro bairro;

    @OneToMany(mappedBy="endereco", fetch= FetchType.LAZY)
    private List<OrigemReceita> origemReceitas;
    
    @OneToMany(mappedBy="endereco", fetch= FetchType.LAZY)
    private List<Fornecedor> fornecedores;
    
    public Endereco() {
    }

    public Endereco(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public List<OrigemReceita> getOrigemReceitas() {
        return origemReceitas;
    }

    public void setOrigemReceitas(List<OrigemReceita> origemReceitas) {
        this.origemReceitas = origemReceitas;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
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
        final Endereco other = (Endereco) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return logradouro != null ? logradouro : ""
                + numero != null ? ", "+numero : "" 
                + cep != null ? " - "+cep : "" 
                + complemento != null ? " - "+complemento : ""
                + referencia != null ? " - "+referencia : "" 
                + bairro != null ? " "+bairro.getNome()
                +(bairro.getMunicipio() != null ? " "+bairro.getMunicipio().getNome()
                +(bairro.getMunicipio().getEstado() != null ? " "
                + bairro.getMunicipio().getEstado().getSigla() : "") : "") : "";
    }
    
}

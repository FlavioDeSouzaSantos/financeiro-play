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
import javax.validation.constraints.Size;

/**
 *
 * @author User
 */
@Entity
@Table(name="fornecedor")
@TableGenerator(name = "chaveFornecedor", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "fornecedor", allocationSize = 1)
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="chaveFornecedor")
    private Integer id;
    
    @Column(name="razao_social", length=70, nullable=false)
    private String razaoSocial;
    
    @Size(min=5, max=70, message="O nome fantasia deve possuir no mínimo 5 e no máximo 70 caracteres.")
    @Column(name="nome_fantasia", length=70)
    private String nomeFantasia;
    
    @Column(name="cnpj", length=18)
    private String cnpj;
    
    @Column(name="telefone", length=13)
    private String telefone;
    
    @JoinColumn(name="id_endereco", referencedColumnName="id")
    @ManyToOne(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
    private Endereco endereco;

    @OneToMany(mappedBy="fornecedor", fetch= FetchType.LAZY)
    private List<Gasto> gastos;
    
    public Fornecedor() {
    }

    public Fornecedor(Integer id) {
        this.id = id;
    }
    
    public String getEnderecoFormatado(){
        if(getEndereco() != null){
            return getEndereco().toString();
        }else{
            return "";
        }
    }
    
    public String getIdNomeFantasia(){
        return id+" - "+nomeFantasia;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Fornecedor other = (Fornecedor) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}

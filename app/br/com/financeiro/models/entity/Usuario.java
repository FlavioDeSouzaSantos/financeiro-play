/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financeiro.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import br.com.financeiro.models.enums.FuncaoUsuario;

/**
 *
 * @author User
 */
@Entity
@Table(name="usuario")
@TableGenerator(name = "chaveUsuario", table = "chave", pkColumnName = "tabela",
    valueColumnName = "valor", pkColumnValue = "usuario", allocationSize = 1)
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.TABLE, generator="chaveUsuario")
    private Integer id;
    
    @Size(min=5, max=70, message="O nome deve possuir no mínimo 5 e no máximo 70 caracteres.")
    @Column(name="nome", length=70, nullable=false)
    private String nome;
    
    @Size(min=5, max=20, message="O login deve possuir no mínimo 5 e no máximo 20 caracteres.")
    @Column(name="login", length=20, nullable=false)
    private String login;
        
    @Size(min=5, message="A senha deve possuir no mínimo 5 caracteres.")
    @Lob
    @Type(type="text")
    @Column(name="senha", nullable=false)
    private String senha;
    
    @Column(name="ativo", nullable=false)
    private Boolean ativo;
    
    @Column(name="data_bloqueio")
    @Temporal(TemporalType.DATE)
    private Date dataBloqueio;
    
    @NotNull(message="A função não foi informada.")
    @Enumerated(EnumType.STRING)
    @Column(name="funcao", length=20, nullable=false)
    private FuncaoUsuario funcao;

    @OneToMany(mappedBy="usuario", fetch= FetchType.LAZY)
    private List<Receita> receitas;
    
    @OneToMany(mappedBy="usuario", fetch= FetchType.LAZY)
    private List<Gasto> gastos;
    
    public Usuario() {
    }

    public Usuario(Integer id) {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataBloqueio() {
        return dataBloqueio;
    }

    public void setDataBloqueio(Date dataBloqueio) {
        this.dataBloqueio = dataBloqueio;
    }

    public FuncaoUsuario getFuncao() {
        return funcao;
    }

    public void setFuncao(FuncaoUsuario funcao) {
        this.funcao = funcao;
    }

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
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
        hash = 11 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Usuario other = (Usuario) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}

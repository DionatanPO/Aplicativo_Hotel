package com.example.app.model;

import java.io.Serializable;

public class Funcionario implements Serializable {
    private Long id;
    private String cpf;
    private String nome;
    private String codidentificacao;
    private String senha;
    private String cargo;
    private String estado;
    private String token;

    public Funcionario() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodidentificacao() {
        return codidentificacao;
    }

    public void setCodidentificacao(String codidentificacao) {
        this.codidentificacao = codidentificacao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "nome='" + nome + '\'' +
                '}';
    }
}

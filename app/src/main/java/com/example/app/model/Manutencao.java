package com.example.app.model;

import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;

import java.sql.Date;

/**
 *
 * @author Dionatan
 */
public class Manutencao {
    private Long id;
    private Date data_solicitacao;
    private Funcionario funcionario;
    private String descricao;
    private Apartamento apartamento;

    public Manutencao() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData_solicitacao() {
        return data_solicitacao;
    }

    public void setData_solicitacao(Date data_solicitacao) {
        this.data_solicitacao = data_solicitacao;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }
    
    
}

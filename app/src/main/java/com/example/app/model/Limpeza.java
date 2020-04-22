package com.example.app.model;


import java.sql.Date;
import java.util.List;

public class Limpeza {

    private Long id;

    private Apartamento apartamento;

    private Funcionario funcionario;

    private Date data_limpeza;

    public Limpeza() {
    }

    public Limpeza(Long id, Apartamento apartamento, Funcionario funcionario, Date data_limpeza) {
        this.id = id;
        this.apartamento = apartamento;
        this.funcionario = funcionario;
        this.data_limpeza = data_limpeza;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Date getData_limpeza() {
        return data_limpeza;
    }

    public void setData_limpeza(Date data_limpeza) {
        this.data_limpeza = data_limpeza;
    }
}

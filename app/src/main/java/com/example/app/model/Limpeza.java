package com.example.app.model;


import java.sql.Date;
import java.util.List;

public class Limpeza {

    private Long l_id;

    private Apartamento apartamento;

    private Funcionario funcionario;

    private Date data_limpeza;

    public Limpeza() {
    }

    public Long getL_id() {
        return l_id;
    }

    public void setL_id(Long l_id) {
        this.l_id = l_id;
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

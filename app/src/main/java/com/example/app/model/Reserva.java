
package com.example.app.model;

import com.example.app.model.Apartamento;
import com.example.app.model.Hospede;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author Dionatan
 */
public class Reserva implements Serializable {
    private Long id;
    private Date data_entrada;
    private Date data_saida;
    private Hospede hospede;
    private Apartamento apartamento;
    private Funcionario funcionario;
    private String estado;
    private int n_pessoas;
    private float valor;


    public Reserva() {
    }

    public Reserva(Long id, Date data_entrada, Date data_saida, Hospede hospede, Apartamento apartamento, String estado, int n_pessoas) {
        this.id = id;
        this.data_entrada = data_entrada;
        this.data_saida = data_saida;
        this.hospede = hospede;
        this.apartamento = apartamento;
        this.estado = estado;
        this.n_pessoas = n_pessoas;
    }

    public Reserva(Long id, Date data_entrada, Date data_saida, Hospede hospede, Apartamento apartamento, Funcionario funcionario, String estado, int n_pessoas) {
        this.id = id;
        this.data_entrada = data_entrada;
        this.data_saida = data_saida;
        this.hospede = hospede;
        this.apartamento = apartamento;
        this.funcionario = funcionario;
        this.estado = estado;
        this.n_pessoas = n_pessoas;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getEstado() {
        return estado;
    }

    public int getN_pessoas() {
        return n_pessoas;
    }

    public void setN_pessoas(int n_pessoas) {
        this.n_pessoas = n_pessoas;
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

    public Date getData_entrada() {
        return data_entrada;
    }

    public void setData_entrada(Date data_entrada) {
        this.data_entrada = data_entrada;
    }

    public Date getData_saida() {
        return data_saida;
    }

    public void setData_saida(Date data_saida) {
        this.data_saida = data_saida;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }


}

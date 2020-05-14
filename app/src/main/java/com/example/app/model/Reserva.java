
package com.example.app.model;

import com.example.app.model.Apartamento;
import com.example.app.model.Hospede;

import java.sql.Date;

/**
 * @author Dionatan
 */
public class Reserva {
    private Long id;
    private Date data_entrada;
    private Date data_saida;
    private Hospede hospede;
    private Apartamento apartamento;
    private String estado;

    public Reserva() {
    }

    public Reserva(Long id, Date data_entrada, Date data_saida, Hospede hospede, Apartamento apartamento, String estado) {
        this.id = id;
        this.data_entrada = data_entrada;
        this.data_saida = data_saida;
        this.hospede = hospede;
        this.apartamento = apartamento;
        this.estado = estado;
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

package com.example.app.model;


import java.util.Date;

public class Hospedagem {
    private Long id;
    private Hospede hospede;
    private Date data_entrada;
    private Date data_saida;
    private Long funcionario_id;
    private int n_pessoas;
    private Apartamento apartamento;
    private String tipo_pagamento;
    private String estado;
    private Float valor_hospedagem;

    public Hospedagem() {
    }

    public Hospedagem(Long id, Hospede hospede, Date data_entrada, Date data_saida, Long funcionario_id, int n_pessoas, Apartamento apartamento, String tipo_pagamento, String estado, Float valor_hospedagem) {
        this.id = id;
        this.hospede = hospede;
        this.data_entrada = data_entrada;
        this.data_saida = data_saida;
        this.funcionario_id = funcionario_id;
        this.n_pessoas = n_pessoas;
        this.apartamento = apartamento;
        this.tipo_pagamento = tipo_pagamento;
        this.estado = estado;
        this.valor_hospedagem = valor_hospedagem;
    }

    public int getN_pessoas() {
        return n_pessoas;
    }

    public void setN_pessoas(int n_pessoas) {
        this.n_pessoas = n_pessoas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getFuncionario_id() {
        return funcionario_id;
    }

    public void setFuncionario_id(Long funcionario_id) {
        this.funcionario_id = funcionario_id;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
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


    public String getTipo_pagamento() {
        return tipo_pagamento;
    }

    public void setTipo_pagamento(String tipo_pagamento) {
        this.tipo_pagamento = tipo_pagamento;
    }

    public Float getValor_hospedagem() {
        return valor_hospedagem;
    }

    public void setValor_hospedagem(Float valor_hospedagem) {
        this.valor_hospedagem = valor_hospedagem;
    }
}

package com.example.app.controller;

import android.content.Context;

import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.model.Hospedagem;
import com.example.app.model.Hospede;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class CheckinController {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();

    Context ctx;
    Funcionario funcionario = new Funcionario();
    Apartamento apartamento = new Apartamento();
    Hospede hospede = new Hospede();
    Hospedagem hospedagem = new Hospedagem();
    String json;

    public CheckinController(Context ctx) {
        this.ctx = ctx;
    }


    public String valirar_checkin(Funcionario funcionario,Apartamento apartamento, String cpf,
                                  String nome, String telefone, String data_entrada, String data_saida, String tipo_pagamento, float valor_hospedagem, int n_pessoas) throws ParseException {
        if (cpf.equals("")||apartamento == null || nome.equals("") || data_entrada.equals("") || data_saida.equals("") || tipo_pagamento.equals("") || valor_hospedagem == 0) {
            json = null;
        } else {

            java.util.Date d = new java.util.Date();

            DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            d = fmt.parse(data_entrada);
            java.sql.Date entrada = new java.sql.Date(d.getTime());
            d = fmt.parse(data_saida);
            java.sql.Date saida = new java.sql.Date(d.getTime());


            hospede.setNome(nome);
            hospede.setTelefone(telefone);
            hospede.setCpf(cpf);

            hospedagem.setApartamento(apartamento);
            hospedagem.setHospede(hospede);
            hospedagem.setFuncionario(funcionario);
            hospedagem.setData_saida(saida);
            hospedagem.setData_entrada(entrada);
            hospedagem.setTipo_pagamento(tipo_pagamento);
            hospedagem.setValor_hospedagem(valor_hospedagem);
            hospedagem.setN_pessoas(n_pessoas);
            if(tipo_pagamento.equals("Não Pago")){
                hospedagem.setEstado("Pg não efetuado");
            }else{
                hospedagem.setEstado("OK");
            }


           json = gson.toJson(hospedagem);


        }
        return json;
    }


    public String valirar_checkin_altera(Long id, Apartamento apartamento, String cpf,
                                  String nome, String telefone, String data_entrada, String data_saida, String tipo_pagamento, float valor_hospedagem, int n_pessoas) throws ParseException {
        if (cpf.equals("")||apartamento == null || nome.equals("") || data_entrada.equals("") || data_saida.equals("") || tipo_pagamento.equals("") || valor_hospedagem == 0) {
            json = null;
        } else {

            java.util.Date d = new java.util.Date();

            DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            d = fmt.parse(data_entrada);
            java.sql.Date entrada = new java.sql.Date(d.getTime());
            d = fmt.parse(data_saida);
            java.sql.Date saida = new java.sql.Date(d.getTime());


            hospede.setNome(nome);
            hospede.setTelefone(telefone);
            hospede.setCpf(cpf);
            hospedagem.setId(id);
            hospedagem.setApartamento(apartamento);
            hospedagem.setHospede(hospede);
            hospedagem.setData_saida(saida);
            hospedagem.setData_entrada(entrada);
            hospedagem.setTipo_pagamento(tipo_pagamento);
            hospedagem.setValor_hospedagem(valor_hospedagem);
            hospedagem.setN_pessoas(n_pessoas);
            if(tipo_pagamento.equals("Não Pago")){
                hospedagem.setEstado("Pg não efetuado");
            }else{
                hospedagem.setEstado("OK");
            }


            json = gson.toJson(hospedagem);


        }
        return json;
    }

    public String converter_hospedagem_json(Hospedagem hospedagem) {
        String json = gson.toJson(hospedagem);
        return json;
    }

    public Hospedagem converter_json_hospedagem(String json) {
        hospedagem = new Gson().fromJson(json, Hospedagem.class);
        return hospedagem;
    }
}

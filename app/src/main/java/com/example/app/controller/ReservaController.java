package com.example.app.controller;

import android.content.Context;

import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.model.Reserva;
import com.example.app.model.Hospede;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class ReservaController {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();

    Context ctx;
    Funcionario funcionario = new Funcionario();
    Apartamento apartamento = new Apartamento();
    Hospede hospede = new Hospede();
    Reserva reserva = new Reserva();
    String json;

    public ReservaController(Context ctx) {
        this.ctx = ctx;
    }


    public String valirar_reserva(Apartamento apartamento, String cpf,
                                  String nome, String telefone, String data_entrada, String data_saida, String tipo_pagamento, float valor_reserva, int n_pessoas) throws ParseException {
        if (cpf.equals("")||apartamento == null || nome.equals("") || data_entrada.equals("") || data_saida.equals("") || tipo_pagamento.equals("") || valor_reserva == 0) {
            json = null;
        } else {

            java.util.Date d = new java.util.Date();

            DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            d = fmt.parse(data_entrada);
            Date entrada = new Date(d.getTime());
            d = fmt.parse(data_saida);
            Date saida = new Date(d.getTime());


            hospede.setNome(nome);
            hospede.setTelefone(telefone);
            hospede.setCpf(cpf);

            reserva.setApartamento(apartamento);
            reserva.setHospede(hospede);
            reserva.setData_saida(saida);
            reserva.setData_entrada(entrada);
//
//            reserva.setN_pessoas(n_pessoas);



           json = gson.toJson(reserva);


        }
        return json;
    }


    public String valirar_reserva_altera(Long id, Apartamento apartamento, String cpf,
                                  String nome, String telefone, String data_entrada, String data_saida, String tipo_pagamento, float valor_reserva, int n_pessoas) throws ParseException {
        if (cpf.equals("")||apartamento == null || nome.equals("") || data_entrada.equals("") || data_saida.equals("") || tipo_pagamento.equals("") || valor_reserva == 0) {
            json = null;
        } else {

            java.util.Date d = new java.util.Date();

            DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            d = fmt.parse(data_entrada);
            Date entrada = new Date(d.getTime());
            d = fmt.parse(data_saida);
            Date saida = new Date(d.getTime());


            hospede.setNome(nome);
            hospede.setTelefone(telefone);
            hospede.setCpf(cpf);
            reserva.setId(id);
            reserva.setApartamento(apartamento);
            reserva.setHospede(hospede);
            reserva.setData_saida(saida);
            reserva.setData_entrada(entrada);

//            reserva.setN_pessoas(n_pessoas);



            json = gson.toJson(reserva);


        }
        return json;
    }

    public String converter_reserva_json(Reserva reserva) {
        String json = gson.toJson(reserva);
        return json;
    }

    public Reserva converter_json_reserva(String json) {
        reserva = new Gson().fromJson(json, Reserva.class);
        return reserva;
    }
}

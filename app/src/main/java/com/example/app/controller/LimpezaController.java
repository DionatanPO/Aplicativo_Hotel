package com.example.app.controller;

import android.content.Context;

import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.model.Limpeza;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LimpezaController {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();
    Context ctx;
    String json;
    Limpeza limpeza = new Limpeza();

    public LimpezaController(Context ctx) {
        this.ctx = ctx;
    }


    public String valirar_cadastro_lipenza(Apartamento apartamento, Funcionario funcionario) {
        Date dataHoraAtual = new Date();
        Date d = new Date();
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

        try {
            d = fmt.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date data_limpeza = new java.sql.Date(d.getTime());
            limpeza.setFuncionario(funcionario);
            limpeza.setApartamento(apartamento);
            limpeza.setData_limpeza(data_limpeza);
            String json = gson.toJson(limpeza);
            return json;

    }


    public String valirar_alterar_Limpeza(Long id, String identificacao, String estado, String descricao) {
        if (identificacao.equals("") || estado.equals("")) {

            return null;

        } else {

            String json = gson.toJson(limpeza);
            return json;

        }
    }

    public String converter_limpeza_json(Limpeza limpeza) {
        String json = gson.toJson(limpeza);
        return json;
    }

    public Limpeza converter_json_limpeza_(String json) {
        limpeza = new Gson().fromJson(json, Limpeza.class);
        return limpeza;
    }
}

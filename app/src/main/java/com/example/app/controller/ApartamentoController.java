package com.example.app.controller;

import android.content.Context;

import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.request.Apartamento_Request;
import com.google.gson.Gson;

public class ApartamentoController {
    Gson gson = new Gson();
    Context ctx;
    Apartamento ap = new Apartamento();
    String json;

    public ApartamentoController(Context ctx) {
        this.ctx = ctx;
    }


    public String valirar_cadastro_Apartamento(Funcionario funcionario, String identificacao, String estado, String descricao) {
        if (identificacao.equals("") || estado.equals("")) {

            return null;

        } else {
            ap.setFuncionario(funcionario);
            ap.setIdentificacao(identificacao);
            ap.setEstado(estado);
            ap.setDescricao(descricao);

            String json = gson.toJson(ap);
            return json;

        }
    }


    public String valirar_alterar_Apartamento(Funcionario funcionario, Long id,String identificacao, String estado, String descricao) {
        if (identificacao.equals("") || estado.equals("")) {

            return null;

        } else {
            ap.setFuncionario(funcionario);
            ap.setId(id);
            ap.setIdentificacao(identificacao);
            ap.setEstado(estado);
            ap.setDescricao(descricao);

            String json = gson.toJson(ap);
            return json;

        }
    }

    public String converter_apartamento_json(Apartamento apartamento){
        String json = gson.toJson(apartamento);
        return  json;
    }

    public Apartamento converter_json_apartamento_(String json){
        ap = new Gson().fromJson(json, Apartamento.class);
        return  ap;
    }
}

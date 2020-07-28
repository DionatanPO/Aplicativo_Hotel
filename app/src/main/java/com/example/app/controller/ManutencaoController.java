package com.example.app.controller;

import android.content.Context;

import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.model.Manutencao;
import com.google.gson.Gson;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class ManutencaoController {

    private Context context;
    private Manutencao manutencao;
    private Gson gson = new Gson();


    public ManutencaoController(Context ctx) {
        this.context = ctx;
    }

    public String cadastrar(Funcionario funcionario, String observacao, Apartamento apartamento) {
        if (apartamento == null || observacao.equals("")) {
            return null;
        } else {
            manutencao = new Manutencao();
            manutencao.setApartamento(apartamento);
            manutencao.setFuncionario(funcionario);
            manutencao.setEstado("Ativado");
            manutencao.setObservacao(observacao);


            java.util.Date dataHoraAtual = new java.util.Date();
            java.util.Date d = new java.util.Date();
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            try {
                d = fmt.parse(data);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.sql.Date data_limpeza = new java.sql.Date(d.getTime());
            manutencao.setData_solicitacao(data_limpeza);

            return converter_manutencao_json(manutencao);
        }
    }

    public String alterar(Long id, String estado, Date data, Funcionario funcionario, String observacao, Apartamento apartamento) {
        if (apartamento == null || observacao.equals("")) {
            return null;
        } else {

            manutencao = new Manutencao();
            manutencao.setId(id);
            manutencao.setApartamento(apartamento);
            manutencao.setFuncionario(funcionario);
            manutencao.setEstado(estado);
            manutencao.setObservacao(observacao);
            manutencao.setData_solicitacao(data);
            return converter_manutencao_json(manutencao);
        }
    }

    public String converter_manutencao_json(Manutencao m) {
        String json = gson.toJson(m);
        return json;
    }

    public Manutencao converter_json_manutencao(String json) {
        manutencao = new Gson().fromJson(json, Manutencao.class);
        return manutencao;
    }
}

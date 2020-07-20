package com.example.app.controller;

import android.content.Context;

import com.example.app.model.Funcionario;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;


public class FuncionarioController {

    private Context context;
    Gson gson = new Gson();
    Funcionario funcionario = new Funcionario();

    public FuncionarioController() {
    }

    public FuncionarioController(Context ctx) {
        this.context = ctx;
    }


    public boolean validateLogin(String email, String senha) {
        if (email.equals("") || senha.equals("")) {
            return false;
        } else {
            funcionario.setCodidentificacao(email);
            funcionario.setSenha(senha);
            return true;
        }
    }


    public Funcionario create_account(Context context, String nome, String email, String cpf, String senha, String senha2) {
        if (cpf.equals("") || nome.equals("") || email.equals("") ||  senha.equals("") || senha2.equals("")) {
            if (senha.equals(senha2)) {
                funcionario.setNome(nome);
                funcionario.setCodidentificacao(email);
                funcionario.setSenha(senha);
                funcionario.setCargo("Gerente");
                funcionario.setCpf(cpf);
                funcionario.setEstado("Abilitado");
                return funcionario;
            } else {
                viewToastAlerta(context, "As senhas não correspondem");
                return null;
            }
        }else {
            return  null;
        }
    }

    public Funcionario cadastrar_funcionario(String nome, String email, String cpf, String cargo, String senha, String senha2) {
        if (cpf.equals("") || nome.equals("") || email.equals("") || cargo.equals("") || senha.equals("") || senha2.equals("")) {

            return null;

        } else {
            funcionario.setNome(nome);
            funcionario.setCodidentificacao(email);
            funcionario.setSenha(senha);
            funcionario.setCargo(cargo);
            funcionario.setCpf(cpf);
            funcionario.setEstado("Abilitado");
            return funcionario;
        }
    }

    public String valirar_alterar_funcionario(Long id, String nome, String email, String cpf, String cargo, String senha, String senha2) {
        if (cpf.equals("") || nome.equals("") || email.equals("") || cargo.equals("") || senha.equals("") || senha2.equals("")) {

            return null;

        } else {
            funcionario.setId(id);
            funcionario.setCodidentificacao(email);
            funcionario.setSenha(senha);
            funcionario.setCargo(cargo);
            funcionario.setCpf(cpf);
            funcionario.setNome(nome);
            funcionario.setEstado("Abilitado");


            String json = gson.toJson(funcionario);
            return json;

        }
    }

    public List<Funcionario> getAll() {
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();

        return funcionarios;
    }

    public String converter_funcionario_json(Funcionario fum) {
        String json = gson.toJson(fum);
        return json;
    }

    public Funcionario converter_json_funcionario(String json) {
        funcionario = new Gson().fromJson(json, Funcionario.class);
        return funcionario;
    }
}

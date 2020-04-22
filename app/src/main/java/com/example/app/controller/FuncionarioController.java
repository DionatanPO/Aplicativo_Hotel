package com.example.app.controller;

import android.content.Context;

import com.example.app.model.Funcionario;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


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

        funcionario.setEmail(email);
        funcionario.setSenha(senha);


        return true;


    }

    public String valirar_cadastro_Funcionario(String nome, String email, String cpf, String cargo, String senha, String senha2) {
        if (cpf.equals("")||nome.equals("") || email.equals("") || cargo.equals("") || senha.equals("") || senha2.equals("")) {

            return null;

        } else {
            if (senha.equals(senha2)) {
                funcionario.setEmail(email);
                funcionario.setSenha(senha);
                funcionario.setCargo(cargo);
                funcionario.setCpf(cpf);
                funcionario.setNome(nome);
                funcionario.setEstado("Abilitado");

                String json = gson.toJson(funcionario);
                return json;
            } else {
                return "senha";
            }

        }

    }

    public Funcionario create_account(String user_name, String email, String senha, String cargo) {
        funcionario.setNome(user_name);
        funcionario.setEmail(email);
        funcionario.setSenha(senha);

        funcionario.setCargo("Gerente");


        return funcionario;

    }

    public String valirar_alterar_funcionario(Long id,String nome, String email, String cpf, String cargo, String senha, String senha2) {
        if (cpf.equals("")||nome.equals("") || email.equals("") || cargo.equals("") || senha.equals("") || senha2.equals("")) {

            return null;

        } else {
            funcionario.setId(id);
            funcionario.setEmail(email);
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
    public Funcionario converter_json_funcionario(String json){
        funcionario = new Gson().fromJson(json, Funcionario.class);
        return  funcionario;
    }
}

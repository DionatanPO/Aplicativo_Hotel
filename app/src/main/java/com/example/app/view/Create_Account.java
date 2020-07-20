package com.example.app.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.controller.FuncionarioController;
import com.example.app.request.Funcionario_Request;

public class Create_Account extends AppCompatActivity {
    private Button btn_create_account;
    private Context context;
    private TextView namee, email, senha, criar_conta_cpf, editTextSenha2;
    private FuncionarioController funC;
    private Funcionario_Request funcionario_request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        context = this;

        btn_create_account = findViewById(R.id.btnCreate);
        namee = findViewById(R.id.editTextUserName);
        email = findViewById(R.id.editTextEmail);
        senha = findViewById(R.id.editTextPassword);
        criar_conta_cpf = findViewById(R.id.criar_conta_cpf);
        editTextSenha2 = findViewById(R.id.editTextSenha2);

        funC = new FuncionarioController(context);
        funcionario_request = new Funcionario_Request(context);

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (funC.create_account(context, namee.getText().toString(), email.getText().toString(), criar_conta_cpf.getText().toString(), senha.getText().toString(), editTextSenha2.getText().toString()) != null) {
                   String json = funC.converter_funcionario_json(funC.create_account(context, namee.getText().toString(), email.getText().toString(), criar_conta_cpf.getText().toString(), senha.getText().toString(), editTextSenha2.getText().toString()));
                    funcionario_request.cadastrarFuncionario(json, context);
                    Intent i = new Intent(Create_Account.this,LoginActivity.class);
                    startActivity(i);

                }
            }
        });

    }
}

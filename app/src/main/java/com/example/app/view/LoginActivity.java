package com.example.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.app.R;
import com.example.app.controller.FuncionarioController;
import com.example.app.model.Funcionario;
import com.example.app.request.LoginRequest;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;

public class LoginActivity extends Activity {
    private Button btn_create_account;
    private Button btn_enter;
    private TextView email, password;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context = this;
        btn_create_account = findViewById(R.id.buttonCreate_account);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btn_enter = findViewById(R.id.buttonEnter);

        final FuncionarioController FunC = new FuncionarioController(this.getApplicationContext());


        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, Create_Account.class);
                startActivity(i);
            }
        });

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FunC.validateLogin(email.getText().toString(), password.getText().toString()) == true) {
                    Funcionario funcionario = new Funcionario();
                    funcionario.setCodidentificacao(email.getText().toString());
                    funcionario.setSenha(password.getText().toString());

                    LoginRequest loginRequest = new LoginRequest(context);
                    loginRequest.login(FunC.converter_funcionario_json(funcionario), context);

                } else {
                    viewToastAlerta(context, "Preencha todos os campos");
                }
            }
        });
    }
}

package com.example.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.app.R;
import com.example.app.controller.FuncionarioController;
import com.example.app.model.Funcionario;
import com.example.app.request.LoginRequest;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;

public class LoginActivity extends Activity {
    private Button btn_create_account,btn_enter, btn_ajuda;
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
        btn_ajuda = findViewById(R.id.btn_ajuda);

        final FuncionarioController FunC = new FuncionarioController(this.getApplicationContext());


        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, Create_Account.class);
                startActivity(i);
            }
        });
        btn_ajuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Ajuda");
                alertDialogBuilder
                        .setMessage("Criar conta: Permite que proprietários/ gerentes de hotéis crie " +
                                "uma conta de administrador. Esta conta prove acesso as demais funcionalidades do aplicativo. " +
                                "A opção de efetuar reserva e destinada a clientes que desejam hospedar-se no hotel.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {

                                Intent i = new Intent(context, LoginActivity.class);
                                startActivity(i);
                                finishAffinity();

                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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

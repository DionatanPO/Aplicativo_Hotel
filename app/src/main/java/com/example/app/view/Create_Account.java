package com.example.app.view;

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


public class Create_Account extends AppCompatActivity {
    Button btn_create_account;
    TextView namee, email, senha;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        btn_create_account = findViewById(R.id.btnCreate);
        namee = findViewById(R.id.editTextUserName);
        email = findViewById(R.id.editTextEmail);
        senha = findViewById(R.id.editTextPassword);


        final FuncionarioController funC = new FuncionarioController(this.getApplicationContext());

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (funC.create_account(
                        namee.getText().toString(), email.getText().toString(), senha.getText().toString(), "") != null) {
                    Toast.makeText(Create_Account.this.getApplicationContext(), "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(Create_Account.this,
                            PainelActivity.class);
                    startActivity(i);
                }
            }
        });

    }
}

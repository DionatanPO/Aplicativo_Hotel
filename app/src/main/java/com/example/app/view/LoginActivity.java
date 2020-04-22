package com.example.app.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.app.R;
import com.example.app.controller.FuncionarioController;

public class LoginActivity extends Activity {
    Button btn_create_account;
    Button btn_enter;
    TextView email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btn_create_account = findViewById(R.id.buttonCreate_account);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btn_enter = findViewById(R.id.buttonEnter);


        final FuncionarioController FunC = new FuncionarioController(this.getApplicationContext());


        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,
                        Create_Account.class);
                startActivity(i);
            }
        });

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(FunC.validateLogin(email.getText().toString(),password.getText().toString()) == true){

                    Intent i = new Intent(LoginActivity.this,
                            PainelActivity.class);
                    startActivity(i);

                }else{

                }
            }
        });

    }
}

package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import com.example.app.R;

public class PainelActivity extends Activity {
    TextView btn_funcionario, btn_check_in, btn_limpeza, btn_apartamento, btn_hospedagem, btn_reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);


        btn_funcionario = findViewById(R.id.buttonFuncionarios);
        btn_check_in = findViewById(R.id.btn_check_in);
        btn_limpeza = findViewById(R.id.btn_limpeza);
        btn_apartamento = findViewById(R.id.btn_apartamento);
        btn_hospedagem = findViewById(R.id.btn_hospedagem);
        btn_reserva = findViewById(R.id.btn_reserva);

//       btn_funcionario.setVisibility(View.INVISIBLE);
        btn_funcionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, FuncionarioActivity.class);
                startActivity(i);
            }
        });
        btn_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, CheckinActivity.class);
                startActivity(i);
            }
        });

        btn_limpeza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, LimpezaActivity.class);
                startActivity(i);
            }
        });
        btn_apartamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, ApartamentoActivity.class);
                startActivity(i);
            }
        });

        btn_hospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, HospedagemActivity.class);
                startActivity(i);
            }
        });

        btn_reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, ReservaActivity.class);
                startActivity(i);
            }
        });
    }
}

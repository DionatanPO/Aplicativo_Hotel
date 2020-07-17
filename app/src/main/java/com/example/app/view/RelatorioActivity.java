package com.example.app.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.request.RelatorioRequest;


public class RelatorioActivity extends AppCompatActivity {
    private Button btn_export;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio);


        btn_export = findViewById(R.id.btn_export);
        btn_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RelatorioActivity.this);

                // set title
                alertDialogBuilder.setTitle("Exportar Dados");

                // set dialog message
                alertDialogBuilder
                        .setMessage(" seus dados serão convertidos para uma planilha no formato xlsx. Deseja fazer o download da planilha?")
                        .setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                RelatorioRequest relatorioRequest = new RelatorioRequest(RelatorioActivity.this);
//                                relatorioRequest.export(produtor.getId());

                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

    }

}

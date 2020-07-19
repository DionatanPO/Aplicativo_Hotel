package com.example.app.view;

import android.content.DialogInterface;

import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.model.Apartamento;
import com.example.app.model.Hospedagem;
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Hospedagem_Request;
import com.example.app.request.RelatorioRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.WHITE;


public class RelatorioActivity extends AppCompatActivity {
    private Button btn_export;
    private List<Apartamento> apartamentos;
    private List<Hospedagem> hospedagemList;
    private Apartamento_Request apartamento_request;
    private Hospedagem_Request hospedagem_request;
    private TextView relatorio_td, relatorio_tc, relatorio_tt;

    int apSujo = 0, apDisponivel = 0, apReservados = 0, apManuntencao = 0, apOcupado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio);

        relatorio_td = findViewById(R.id.relatorio_td);
        relatorio_tc = findViewById(R.id.relatorio_tc);
        relatorio_tt = findViewById(R.id.relatorio_tt);

        apartamento_request = new Apartamento_Request(this);
        hospedagem_request = new Hospedagem_Request(this);
        apartamento_request.bsucarTodos(this);
        hospedagem_request.bsucarTodosAtivos(this);

        btn_export = findViewById(R.id.btn_export);
        btn_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RelatorioActivity.this);

                alertDialogBuilder.setTitle("Exportar Dados");

                alertDialogBuilder
                        .setMessage(" seus dados serão convertidos para uma planilha no formato xlsx. Deseja fazer o download da planilha?")
                        .setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int id) {
                                RelatorioRequest relatorioRequest = new RelatorioRequest(RelatorioActivity.this);
                                relatorioRequest.export((long) 1);

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

    public void request(List<Apartamento> apartamentoList) {

        for (int i = 0; i < apartamentoList.size(); i++) {
            if (apartamentoList.get(i).getEstado().equals("Disponível")) {
                apDisponivel++;
            }
            if (apartamentoList.get(i).getEstado().equals("Ocupado")) {
                apOcupado++;
            }
            if (apartamentoList.get(i).getEstado().equals("Sujo")) {
                apSujo++;
            }
            if (apartamentoList.get(i).getEstado().equals("Manutenção")) {
                apManuntencao++;
            }
            if (apartamentoList.get(i).getEstado().equals("Reservado")) {
                apReservados++;
            }
        }

        final PieChart pieChart = findViewById(R.id.grafico1);

        pieChart.setVisibility(View.GONE);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(apSujo, "Sujo"));
        pieEntries.add(new PieEntry(apDisponivel, "Disponível"));
        pieEntries.add(new PieEntry(apOcupado, "Ocupado"));
        pieEntries.add(new PieEntry(apManuntencao, "Manuntenção"));
        pieEntries.add(new PieEntry(apReservados, "Reservado"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        pieDataSet.setColors(new int[]{R.color.red, R.color.grafico1, R.color.roxo, R.color.grafico2, R.color.grafico3}, RelatorioActivity.this);
        pieDataSet.setValueTextColor(WHITE);
        pieDataSet.setValueTextSize(11f);
        pieDataSet.setFormSize(11f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);

        pieChart.setCenterText("Apartamento");

        pieChart.animate();

        pieChart.setVisibility(View.VISIBLE);

    }

    public void request2(List<Hospedagem> hospedagemList) {
        int dinheiro = 0, cartao = 0;
        float totalc = 0, totald = 0;


        for (int i = 0; i < hospedagemList.size(); i++) {
            if (hospedagemList.get(i).getTipo_pagamento().equals("PG Dinheiro")) {
                dinheiro++;
                totald += hospedagemList.get(i).getValor_hospedagem();
            }
            if (hospedagemList.get(i).getTipo_pagamento().equals("PG Cartão")) {
                totalc += hospedagemList.get(i).getValor_hospedagem();
                cartao++;
            }
        }

        relatorio_td.setText(relatorio_td.getText().toString()+String.valueOf(totald));
        relatorio_tc.setText(relatorio_tc.getText().toString()+String.valueOf(totalc));
        relatorio_tt.setText(String.valueOf(totalc+totald));

        final PieChart pieChart = findViewById(R.id.grafico2);

        pieChart.setVisibility(View.GONE);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(cartao, "Cartão"));
        pieEntries.add(new PieEntry(dinheiro, "Dinheiro"));


        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");

        pieDataSet.setColors(new int[]{R.color.grafico2, R.color.grafico3}, RelatorioActivity.this);
        pieDataSet.setValueTextColor(WHITE);
        pieDataSet.setValueTextSize(11f);
        pieDataSet.setFormSize(11f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);

        pieChart.setCenterText("Pagamento");

        pieChart.animate();

        pieChart.setVisibility(View.VISIBLE);
    }
}

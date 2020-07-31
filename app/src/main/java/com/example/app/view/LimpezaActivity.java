package com.example.app.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.FuncionarioController;
import com.example.app.controller.LimpezaController;
import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Funcionario_Request;
import com.example.app.request.Limpeza_Request;
import com.example.app.view.adapter.LimpezaAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LimpezaActivity extends AppCompatActivity  implements PopupMenu.OnMenuItemClickListener {
    private RecyclerView recyclerView;
    private LimpezaAdapter limpezaAdapter;
    private Apartamento_Request apr;
    private Limpeza_Request limpeza_request;
    private TextView quantidade_ap_sujo, msn;
    private LimpezaController limpezaController;
    private Funcionario funcionario;
    private Button button_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limpeza);
        button_menu = findViewById(R.id.painel_btn_menu);
        msn = findViewById(R.id.msn);


        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        quantidade_ap_sujo = findViewById(R.id.txt_quantidade_sujo);

        FuncionarioController fc = new FuncionarioController();

        recyclerView = (RecyclerView) findViewById(R.id.limpeza_recycler_view);

        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        apr = new Apartamento_Request(this);
        limpeza_request = new Limpeza_Request(this);
        List<Apartamento> apartamentoList = new ArrayList<>();

        limpezaAdapter = new LimpezaAdapter(this, apartamentoList, funcionario, msn, quantidade_ap_sujo);

        if (funcionario.getAdministrador_id() == null) {
            limpeza_request.buscar_Apartamentos_Sujos(limpezaAdapter, quantidade_ap_sujo, funcionario.getId(), msn);
        } else {
            limpeza_request.buscar_Apartamentos_Sujos(limpezaAdapter, quantidade_ap_sujo, funcionario.getAdministrador_id(), msn);
        }


        recyclerView.setAdapter(limpezaAdapter);

        limpezaController = new LimpezaController(this);

        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(LimpezaActivity.this, view);
                popup.setOnMenuItemClickListener(LimpezaActivity.this);
                popup.inflate(R.menu.menu_limpeza);
                popup.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.perfil:
                msn.setVisibility(View.GONE);
                Date dataHoraAtual = new Date();
                Date d = new Date();
                DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);




                if (funcionario.getAdministrador_id() == null) {
                    limpezaAdapter.getApartamentosList().clear();
                    limpezaAdapter.notifyDataSetChanged();
                    limpeza_request.buscar_Limpezas_pordata(limpezaAdapter, quantidade_ap_sujo, funcionario.getId(), data, quantidade_ap_sujo);
                } else {
                    limpezaAdapter.getApartamentosList().clear();
                    limpezaAdapter.notifyDataSetChanged();
                    limpeza_request.buscar_Limpezas_pordata(limpezaAdapter, quantidade_ap_sujo, funcionario.getAdministrador_id(), data, quantidade_ap_sujo);
                }

                return true;
            case R.id.perfil2:

                if (funcionario.getAdministrador_id() == null) {
                    limpezaAdapter.getApartamentosList().clear();
                    limpezaAdapter.notifyDataSetChanged();
                    limpeza_request.buscar_Apartamentos_Sujos(limpezaAdapter, quantidade_ap_sujo, funcionario.getId(), msn);
                } else {
                    limpezaAdapter.getApartamentosList().clear();
                    limpezaAdapter.notifyDataSetChanged();
                    limpeza_request.buscar_Apartamentos_Sujos(limpezaAdapter, quantidade_ap_sujo, funcionario.getAdministrador_id(), msn);
                }
                return true;
            case R.id.perfil3:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Ajuda");
                alertDialogBuilder
                        .setMessage("Após efetuar a limpeza de um apartamento, para alterar o estado " +
                                "do apartamento, basta clicar no respectivo apartamento que surgira " +
                                "a opção de alterar o estado para disponível ou outro estado.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {

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
                return true;
            default:
                return false;
        }
    }
}


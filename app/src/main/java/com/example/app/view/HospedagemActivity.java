package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.ApartamentoController;
import com.example.app.controller.CheckinController;
import com.example.app.model.Cal_Data;
import com.example.app.model.Funcionario;
import com.example.app.model.Hospedagem;
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Hospedagem_Request;
import com.example.app.view.adapter.HospedagemAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.sql.Date;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;
import static com.example.app.view.CustonToast.viewToastErro;

public class HospedagemActivity extends Activity implements PopupMenu.OnMenuItemClickListener {

    private HospedagemAdapter hospedagemAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CheckinController checkinController;
    private Hospedagem_Request hospedagem_request;
    private Button btn_add, button_menu;
    private String json;
    private ProgressBar progressBar;
    private Context context;
    private List<Hospedagem> fumList = new ArrayList<>();
    private Funcionario funcionario;
    private Apartamento_Request apartamento_request;
    private ApartamentoController apartamentoController;
    private TextView msn_func;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospedagem);
        context = this;
        msn_func = findViewById(R.id.msn_fun);
        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        button_menu = findViewById(R.id.painel_btn_menu);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        hospedagem_request = new Hospedagem_Request(this);

        hospedagemAdapter = new HospedagemAdapter(this, fumList, funcionario);

        if (funcionario.getAdministrador_id() == null) {
            hospedagem_request.bsucarTodosAtivos(hospedagemAdapter, funcionario.getId(), msn_func);
        } else {
            hospedagem_request.bsucarTodosAtivos(hospedagemAdapter, funcionario.getAdministrador_id(), msn_func);
        }

        btn_add = findViewById(R.id.fab_add);

        recyclerView = (RecyclerView) findViewById(R.id.hospedagem_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(hospedagemAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HospedagemActivity.this, CheckinActivity.class);
                i.putExtra("funcionario", funcionario);
                startActivity(i);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new HospedagemActivity.ItemTouchHandler(0,
                        ItemTouchHelper.LEFT)
        );
        helper.attachToRecyclerView(recyclerView);

        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, view);
                popup.setOnMenuItemClickListener(HospedagemActivity.this);
                popup.inflate(R.menu.menu_hospedagem);
                popup.show();
            }
        });

    }

    public class ItemTouchHandler extends ItemTouchHelper.SimpleCallback {

        public ItemTouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("WrongConstant")
        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            apartamento_request = new Apartamento_Request(context);
            apartamentoController = new ApartamentoController(context);

            checkinController = new CheckinController(HospedagemActivity.this);
            hospedagemAdapter.getHospedagemsList().get(position).setEstado("Desabilitado");

            json = checkinController.converter_hospedagem_json(hospedagemAdapter.getHospedagemsList().get(position));

            hospedagem_request.alterar_hospedagem(json, hospedagemAdapter.getHospedagemsList().get(position).getId());

            hospedagemAdapter.getHospedagemsList().get(position).getApartamento().setEstado("Disponível");

            String apjson = apartamentoController.converter_apartamento_json(hospedagemAdapter.getHospedagemsList().get(position).getApartamento());
            apartamento_request.alterar_Apartamento(apjson, hospedagemAdapter.getHospedagemsList().get(position).getApartamento().getId());

            hospedagemAdapter.getHospedagemsList().remove(position);
            hospedagemAdapter.notifyItemRemoved(position);

            if (hospedagemAdapter.getHospedagemsList().size() <= 0) {
                msn_func.setVisibility(View.VISIBLE);
            }
            viewToast(context, "Hospedagem apagada!");

        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(HospedagemActivity.this, R.color.red))
                    .addActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.perfil:
                msn_func.setVisibility(View.GONE);
                Cal_Data cal_data = new Cal_Data();

                String data = cal_data.cal_data_entrada_saida().get(0);
                java.util.Date d = new java.util.Date();

                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");


                try {
                    d = fmt.parse(data);
                    java.sql.Date d1 = new java.sql.Date(d.getTime());

                    if (funcionario.getAdministrador_id() == null) {
                        hospedagemAdapter.getHospedagemsList().clear();
                        hospedagemAdapter.notifyDataSetChanged();
                        hospedagem_request.bsucarTodosEntreData(hospedagemAdapter, funcionario.getId(), msn_func, d1, d1);
                    } else {
                        hospedagemAdapter.getHospedagemsList().clear();
                        hospedagemAdapter.notifyDataSetChanged();
                        hospedagem_request.bsucarTodosEntreData(hospedagemAdapter, funcionario.getAdministrador_id(), msn_func, d1, d1);

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    viewToastErro(context, "Ops! Algo não deu certo");
                }


//

                return true;
            case R.id.perfil2:
                final AlertDialog alerta;

                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.busca_entre_datas, null);
                Button btn_concluir = layout.findViewById(R.id.btnConcluir_hospedagem);

                final EditText data1 = layout.findViewById(R.id.data1);
                final EditText data2 = layout.findViewById(R.id.data2);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(layout);
                alerta = builder.create();
                alerta.show();

                btn_concluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data1.getText().toString().equals("")) {
                            viewToastAlerta(context, "Preencha pelomenos o campo data 1");
                        } else {
                            if (data2.getText().toString().equals("")) {
                                data2.setText(data1.getText().toString());
                            } else {


                            }
                            java.util.Date d = new java.util.Date();

                            DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

                            try {
                                d = fmt.parse(data1.getText().toString());
                                java.sql.Date d1 = new java.sql.Date(d.getTime());
                                d = fmt.parse(data2.getText().toString());
                                java.sql.Date d2 = new java.sql.Date(d.getTime());

                                if (funcionario.getAdministrador_id() == null) {
                                    hospedagemAdapter.getHospedagemsList().clear();
                                    hospedagemAdapter.notifyDataSetChanged();
                                    hospedagem_request.bsucarTodosEntreData(
                                            hospedagemAdapter, funcionario.getId(), msn_func, d1, d2);

                                } else {
                                    hospedagemAdapter.getHospedagemsList().clear();
                                    hospedagemAdapter.notifyDataSetChanged();
                                    hospedagem_request.bsucarTodosEntreData(
                                            hospedagemAdapter, funcionario.getAdministrador_id(), msn_func, d1, d2);

                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                viewToastErro(context, "Ops! Algo não deu certo");
                            }

                        }
                        alerta.cancel();
                    }
                });


                return true;
            default:
                return false;
        }
    }

}



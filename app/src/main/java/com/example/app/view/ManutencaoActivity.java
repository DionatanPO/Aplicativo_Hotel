package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.ApartamentoController;
import com.example.app.controller.ManutencaoController;
import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.model.Manutencao;
import com.example.app.model.Manutencao;
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Manutencao_Request;
import com.example.app.view.adapter.ManutencaoAdapter;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;


public class ManutencaoActivity extends Activity {

    private ManutencaoAdapter manutencaoAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ManutencaoController manutencaoController;
    private Manutencao_Request manutencao_request;
    private Button btn_add;
    private String json;
    private Spinner spinner_apartamento;
    private ArrayAdapter spinnerAdapter;
    private View view;
    private AlertDialog alerta;
    private ProgressBar progressBar;
    private List<Manutencao> manutencaoList = new ArrayList<>();
    private List<Apartamento> apartamentoList = new ArrayList<>();
    private TextView textView;
    private Context context;
    private Funcionario funcionario;
    private Apartamento_Request apartamento_request;
    private ApartamentoController apartamentoController;
    private Apartamento apartamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manutencaos);
        context = this;

        apartamento_request = new Apartamento_Request(context);
        apartamentoController = new ApartamentoController(context);

        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textView = findViewById(R.id.msn_fun);

        manutencao_request = new Manutencao_Request(context);

        manutencaoAdapter = new ManutencaoAdapter(this, manutencaoList, textView, funcionario);

        manutencao_request.bsucarTodosAtivos(manutencaoAdapter, progressBar, funcionario.getId());

        btn_add = findViewById(R.id.fab_add);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(manutencaoAdapter);

        TextView msn_func = findViewById(R.id.msn_fun);

        if (manutencaoAdapter.getManutencaoslist().size() > 0) {
            msn_func.setVisibility(View.GONE);
        } else {
            msn_func.setVisibility(View.VISIBLE);
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = getLayoutInflater();

                view = li.inflate(R.layout.alert_add_manutencao, null);

                view.findViewById(R.id.btnCadAp);
                spinner_apartamento = view.findViewById(R.id.apartamento_spinner);
                final EditText observacao = view.findViewById(R.id.add_manutencao_observacao);

                spinnerAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, apartamentoList);
                apartamento_request.buscarDisponiveis(apartamentoList, spinnerAdapter, funcionario.getAdministrador_id());

                spinner_apartamento.setAdapter(spinnerAdapter);

                spinner_apartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        apartamento = (Apartamento) parent.getItemAtPosition(position);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        apartamento = null;
                    }

                });

                view.findViewById(R.id.btn_Consluir_Fun).setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("WrongConstant")
                    public void onClick(View arg0) {

                        manutencaoController = new ManutencaoController(ManutencaoActivity.this);
                        manutencao_request = new Manutencao_Request(ManutencaoActivity.this);

                        json = manutencaoController.cadastrar(funcionario, observacao.getText().toString(), apartamento);

                        if (json != null) {
                            manutencao_request.cadastrarManutencao(json, manutencaoAdapter);

                        } else {
                            viewToastAlerta(context, "Prencha todos os campos!");
                        }
                    }
                });


                AlertDialog.Builder builder = new AlertDialog.Builder(ManutencaoActivity.this);
                builder.setView(view);
                alerta = builder.create();
                alerta.show();
            }
        });
        ItemTouchHelper helper = new ItemTouchHelper(
                new ManutencaoActivity.ItemTouchHandler(0,
                        ItemTouchHelper.LEFT)
        );
        helper.attachToRecyclerView(recyclerView);

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

            manutencaoController = new ManutencaoController(ManutencaoActivity.this);

            manutencaoAdapter.getManutencaoslist().get(position).setEstado("Desabilitado");

            json = manutencaoController.converter_manutencao_json(manutencaoAdapter.getManutencaoslist().get(position));

            manutencao_request.alterar_manutencao(json, manutencaoAdapter.getManutencaoslist().get(position).getId());

            manutencaoAdapter.getManutencaoslist().get(position).getApartamento().setEstado("Disponível");
            String apjson = apartamentoController.converter_apartamento_json(manutencaoAdapter.getManutencaoslist().get(position).getApartamento());
            apartamento_request.alterar_Apartamento(apjson, manutencaoAdapter.getManutencaoslist().get(position).getApartamento().getId());

            manutencaoAdapter.getManutencaoslist().remove(position);
            manutencaoAdapter.notifyItemRemoved(position);
            if (manutencaoAdapter.getManutencaoslist().size() <= 0) {
                textView.setVisibility(View.VISIBLE);
            }

            viewToast(context, "Manutenção apagada!");
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(ManutencaoActivity.this, R.color.red))
                    .addActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    }

}
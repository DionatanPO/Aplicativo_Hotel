package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.ApartamentoController;

import com.example.app.model.Funcionario;
import com.example.app.request.Apartamento_Request;
import com.example.app.view.adapter.ApartamentoAdapter;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;


public class ApartamentoActivity extends AppCompatActivity {

    private ApartamentoAdapter apartamentoAdapter;
    private AlertDialog alerta;
    private Apartamento_Request apr;
    private ApartamentoController apc;
    private String json, estado;
    private Button btn_add;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Spinner spinner;
    private Funcionario funcionario;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartamento);
        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        context = this;
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        btn_add = findViewById(R.id.fab_add_ap);

        apr = new Apartamento_Request(this);

        apartamentoAdapter = new ApartamentoAdapter(this, apr.bsucarTodosAtivos(progressBar,funcionario.getAdministrador_id()),funcionario);

        recyclerView = findViewById(R.id.apartamento_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(apartamentoAdapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (funcionario.getCargo().equals("Camareira") || funcionario.getCargo().equals("Recepcionista")) {
                    viewToastAlerta(context, "Funcionários não tem acesso a essa opção");
                } else {
                    LayoutInflater li = getLayoutInflater();

                    view = li.inflate(R.layout.alert_add_apartamento, null);

                    view.findViewById(R.id.btnCadAp);
                    final View finalView = view;

                    spinner = (Spinner) view.findViewById(R.id.estados_spinner);

                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ApartamentoActivity.this, R.array.estados_array, android.R.layout.simple_spinner_item);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            estado = (String) parent.getItemAtPosition(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            estado = "";
                        }
                    });

                    view.findViewById(R.id.btnCadAp).setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("WrongConstant")
                        public void onClick(View arg0) {

                            EditText identificacao;
                            EditText descricao;

                            identificacao = finalView.findViewById(R.id.editText_identificacaoAP);
                            descricao = finalView.findViewById(R.id.editText_DescricaoAp);

                            apc = new ApartamentoController(ApartamentoActivity.this);
                            apr = new Apartamento_Request(ApartamentoActivity.this);

                            json = apc.valirar_cadastro_Apartamento(funcionario,identificacao.getText().toString(), estado, descricao.getText().toString());
                            if (json != null) {
                                apr.cadastrarApartamento(json, apartamentoAdapter);
                                viewToast(context, "Apartamento cadastrado!");
                            } else {
                                viewToastAlerta(context, "Preencha todos os campos *");
                            }
                        }
                    });

                    AlertDialog.Builder builder = new AlertDialog.Builder(ApartamentoActivity.this);
                    builder.setView(view);
                    alerta = builder.create();
                    alerta.show();

                }
            }
        });
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHandler(0, ItemTouchHelper.LEFT)
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

            if (funcionario.getCargo().equals("Camareira") || funcionario.getCargo().equals("Recepcionista")) {
                viewToastAlerta(context, "Funcionários não podem efetuar está açãp");
                apartamentoAdapter.notifyItemRemoved(position);
            } else {
                apc = new ApartamentoController(ApartamentoActivity.this);
                apartamentoAdapter.getApartamentosList().get(position).setEstado("Desabilitado");

                json = apc.converter_apartamento_json(apartamentoAdapter.getApartamentosList().get(position));

                apr.alterar_Apartamento(json, apartamentoAdapter.getApartamentosList().get(position).getId());

                apartamentoAdapter.getApartamentosList().remove(position);
                apartamentoAdapter.notifyItemRemoved(position);
                viewToast(context, "Apartamento apagado!");
            }
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder
                viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(ApartamentoActivity.this, R.color.red))
                    .addActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }


    public ApartamentoAdapter getApartamentoAdapter() {
        return apartamentoAdapter;
    }

    public void setApartamentoAdapter(ApartamentoAdapter apartamentoAdapter) {
        this.apartamentoAdapter = apartamentoAdapter;

    }


}


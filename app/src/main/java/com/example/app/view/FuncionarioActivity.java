package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Canvas;
import android.os.AsyncTask;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.FuncionarioController;
import com.example.app.model.Funcionario;
import com.example.app.request.Funcionario_Request;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class FuncionarioActivity extends Activity {
    private FuncionarioAdapter funcionarioAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    FuncionarioController funC;
    Funcionario_Request funR;
    Button btn_add;
    String json;
    Spinner spinner;
    ArrayAdapter spinnerAdapter;
    View view;
    String cargo;
    String cpf;
    private AlertDialog alerta;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    private List<Funcionario> fumList = new ArrayList<>();
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionarios);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textView = findViewById(R.id.msn_fun);

        funR = new Funcionario_Request(this);

        funcionarioAdapter = new FuncionarioAdapter(this, fumList, textView);

        funR.bsucarTodosAtivos(funcionarioAdapter, progressBar);


        btn_add = findViewById(R.id.fab_add);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(funcionarioAdapter);

        TextView msn_func = findViewById(R.id.msn_fun);

        if (funcionarioAdapter.getFuncionarioslist().size() > 0) {
            msn_func.setVisibility(View.GONE);
        } else {
            msn_func.setVisibility(View.VISIBLE);
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = getLayoutInflater();

                view = li.inflate(R.layout.alert_add_funcionario, null);


                view.findViewById(R.id.btnCadAp);
                final View finalView = view;
                final EditText fumNome = view.findViewById(R.id.editTextFunNome);
                final EditText fumcpf = view.findViewById(R.id.editTextFumCpf);
                final EditText fumEmail = view.findViewById(R.id.editTextFumEmail);

                final EditText fumSenha = view.findViewById(R.id.editTextFunSena);
                final EditText fumSenha2 = view.findViewById(R.id.editTextFumSenha2);


                spinner = (Spinner) view.findViewById(R.id.cargosFum_spinner);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(FuncionarioActivity.this,
                        R.array.cargosfum_array, android.R.layout.simple_spinner_item);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        cargo = (String) parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        cargo = "";
                    }
                });


                view.findViewById(R.id.btn_Consluir_Fun).setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("WrongConstant")
                    public void onClick(View arg0) {


                        cpf = fumcpf.getText().toString();


                        funC = new FuncionarioController(FuncionarioActivity.this);
                        funR = new Funcionario_Request(FuncionarioActivity.this);

                        json = funC.valirar_cadastro_Funcionario(
                                fumNome.getText().toString(), fumEmail.getText().toString(), cpf
                                , cargo, fumSenha.getText().toString(), fumSenha2.getText().toString());
                        if (json != null) {
                            if (json.equals("senha")) {
                                LayoutInflater inflater2 = LayoutInflater.from(FuncionarioActivity.this);
                                View layout2 = inflater2.inflate(R.layout.custom_toast, null);
                                layout2.setBackgroundResource(R.color.alerta);
                                TextView text = (TextView) layout2.findViewById(R.id.text);
                                text.setText("Essas senhas não coincidem");

                                Toast toast = new Toast(FuncionarioActivity.this);

                                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                                toast.setDuration(8000);
                                toast.setView(layout2);
                                toast.show();
                            } else {
                                funR.cadastrarFuncionario(json, funcionarioAdapter);
                                alerta.dismiss();
                                LayoutInflater inflater2 = LayoutInflater.from(FuncionarioActivity.this);
                                View layout2 = inflater2.inflate(R.layout.custom_toast, null);

                                TextView text = (TextView) layout2.findViewById(R.id.text);
                                text.setText("Funcionário cadastrado!");

                                Toast toast = new Toast(FuncionarioActivity.this);

                                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                                toast.setDuration(8000);
                                toast.setView(layout2);
                                toast.show();
                            }

                        } else {
                            LayoutInflater inflater2 = LayoutInflater.from(FuncionarioActivity.this);
                            View layout2 = inflater2.inflate(R.layout.custom_toast, null);
                            layout2.setBackgroundResource(R.color.alerta);
                            TextView text = (TextView) layout2.findViewById(R.id.text);
                            text.setText("Preencha todos os campos! *");

                            Toast toast = new Toast(FuncionarioActivity.this);

                            toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                            toast.setDuration(8000);
                            toast.setView(layout2);
                            toast.show();
                        }
                    }
                });


                AlertDialog.Builder builder = new AlertDialog.Builder(FuncionarioActivity.this);
                builder.setView(view);
                alerta = builder.create();
                alerta.show();
            }
        });
        ItemTouchHelper helper = new ItemTouchHelper(
                new FuncionarioActivity.ItemTouchHandler(0,
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


            funC = new FuncionarioController(FuncionarioActivity.this);
            funcionarioAdapter.getFuncionarioslist().get(position).setEstado("Desabilitado");

            json = funC.converter_funcionario_json(funcionarioAdapter.getFuncionarioslist().get(position));

            funR.alterrar_estado_funcionario(json, funcionarioAdapter.getFuncionarioslist().get(position).getId());


            funcionarioAdapter.getFuncionarioslist().remove(position);
            funcionarioAdapter.notifyItemRemoved(position);
            if (funcionarioAdapter.getFuncionarioslist().size() <= 0) {
                textView.setVisibility(View.VISIBLE);
            }
            LayoutInflater inflater2 = LayoutInflater.from(FuncionarioActivity.this);
            View layout2 = inflater2.inflate(R.layout.custom_toast, null);

            TextView text = (TextView) layout2.findViewById(R.id.text);
            text.setText("Funcionário apagado!");

            Toast toast = new Toast(FuncionarioActivity.this);
            toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
            toast.setDuration(8000);
            toast.setView(layout2);
            toast.show();


        }
        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(FuncionarioActivity.this, R.color.red))
                    .addActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    }


}



package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.controller.ApartamentoController;
import com.example.app.controller.CheckinController;
import com.example.app.model.Apartamento;
import com.example.app.model.Cal_Data;
import com.example.app.model.Funcionario;
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Checkin_Request;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.view.CustonToast.viewToast;


public class CheckinActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    private String pagamento, json;
    private int n_pessoas;
    private Spinner spinner, spinner2, spinner_apartamento;
    private EditText nome_hospede, cpf, telefone, data_entrada, data_saida, v_hospedagem;
    private Button btn_concluir;
    private ArrayAdapter spinnerAdapter;
    private List<Apartamento> apartamentoList;
    private Apartamento apartamento;
    private Cal_Data cal_data;
    private float valor_hospedagem;
    private TextView btn_hospedagem;
    private Context context;
    private Funcionario funcionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        btn_hospedagem = findViewById(R.id.btn_hospedagem);
        context = this;
        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        btn_hospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CheckinActivity.this, HospedagemActivity.class);
                i.putExtra("funcionario",funcionario);
                startActivity(i);
            }
        });

        spinner = findViewById(R.id.pagamento_spinner);
        spinner2 = findViewById(R.id.n_pessoas_spinner);
        spinner_apartamento = findViewById(R.id.apartamento_spinner);

        nome_hospede = findViewById(R.id.editText_hospede_nome);
        cpf = findViewById(R.id.editText_cpf);
        telefone = findViewById(R.id.editText_telefone);
        data_entrada = findViewById(R.id.editTextData_entrada);
        data_saida = findViewById(R.id.editTextData_saida);
        v_hospedagem = findViewById(R.id.editText_valor_hospedagem);

        btn_concluir = findViewById(R.id.btnConcluir_hospedagem);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pagamento_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pagamento = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pagamento = "";
            }
        });
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.n_pessoas_array, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                n_pessoas = Integer.parseInt((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                n_pessoas = 0;
            }
        });

        Apartamento_Request apartamento_request = new Apartamento_Request(this);

        apartamento = new Apartamento();
        apartamentoList = new ArrayList<>();

        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, apartamentoList);
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

        cal_data = new Cal_Data();
        List<String> horasList = new ArrayList<>();
        horasList = cal_data.cal_data_entrada_saida();

        data_entrada.setText(horasList.get(0));
        data_saida.setText(horasList.get(1));


        btn_concluir.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                if (v_hospedagem.getText().length() != 0) {
                    valor_hospedagem = Float.parseFloat(v_hospedagem.getText().toString());
                }

                CheckinController checkinController = new CheckinController(CheckinActivity.this);

                try {
                    json = checkinController.valirar_checkin(funcionario,
                            apartamento, cpf.getText().toString(), nome_hospede.getText().toString(), telefone.getText().toString(), data_entrada.getText().toString(), data_saida.getText().toString(), pagamento, valor_hospedagem, n_pessoas);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Checkin_Request checkin_request = new Checkin_Request(CheckinActivity.this);
                if (json != null) {
                    checkin_request.cadastrar_hospedagem(json);
                    apartamento.setEstado("Ocupado");
                    ApartamentoController ac = new ApartamentoController(CheckinActivity.this);

                    checkin_request.alterar_Apartamento(ac.converter_apartamento_json(apartamento), apartamento.getId());
                    spinnerAdapter.remove(apartamento);

                    spinner_apartamento.setAdapter(spinnerAdapter);
                    spinnerAdapter.notifyDataSetChanged();

                    viewToast(context, "Hospedagem cadastrada");

                } else {
                    viewToast(context, "Preencha todos os campos *");

                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

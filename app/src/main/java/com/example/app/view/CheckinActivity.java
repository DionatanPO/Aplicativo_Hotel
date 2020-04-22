package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;

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
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Checkin_Request;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class CheckinActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    String pagamento ;
    int n_pessoas ;
    Spinner spinner;
    Spinner spinner2;
    Spinner spinner_apartamento;

    EditText nome_hospede;
    EditText cpf;
    EditText telefone;
    EditText data_entrada;
    EditText data_saida;
    EditText v_hospedagem;

    Button btn_concluir;
    ArrayAdapter spinnerAdapter;

    private List<Apartamento> apartamentoList;
    private Apartamento apartamento;


    Cal_Data cal_data;
    String json;
    float valor_hospedagem;
    private AlertDialog alerta;
    TextView btn_hospedagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        btn_hospedagem = findViewById(R.id.btn_hospedagem);

        btn_hospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CheckinActivity.this, HospedagemActivity.class);
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
        apartamento_request.buscar_por_estado("Disponivel", "estado", apartamentoList, spinnerAdapter);
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
                if (v_hospedagem.getText().length() != 0){
                    valor_hospedagem = Float.parseFloat(v_hospedagem.getText().toString());
                }

                CheckinController checkinController = new CheckinController(CheckinActivity.this);

                try {
                    json = checkinController.valirar_checkin(
                            apartamento,cpf.getText().toString(), nome_hospede.getText().toString(), telefone.getText().toString(), data_entrada.getText().toString(), data_saida.getText().toString(), pagamento, valor_hospedagem, n_pessoas);
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

                    LayoutInflater inflater2 = LayoutInflater.from(CheckinActivity.this);
                    View layout2 = inflater2.inflate(R.layout.custom_toast, null);

                    TextView text = (TextView) layout2.findViewById(R.id.text);
                    text.setText("Hospedagem cadastrada!");

                    Toast toast = new Toast(CheckinActivity.this);

                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(8000);
                    toast.setView(layout2);
                    toast.show();

                } else {
                    LayoutInflater inflater2 = LayoutInflater.from(CheckinActivity.this);
                    View layout2 = inflater2.inflate(R.layout.custom_toast, null);
                    layout2.setBackgroundResource(R.color.alerta);
                    TextView text = (TextView) layout2.findViewById(R.id.text);
                    text.setText("Preencha todos os campos *");

                    Toast toast = new Toast(CheckinActivity.this);

                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(8000);
                    toast.setView(layout2);
                    toast.show();
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

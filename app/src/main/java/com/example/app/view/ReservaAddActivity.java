package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.controller.ApartamentoController;
import com.example.app.controller.ReservaController;
import com.example.app.model.Apartamento;
import com.example.app.model.Cal_Data;
import com.example.app.model.Funcionario;
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Reserva_Request;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;


public class ReservaAddActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private int n_pessoas;
    private Spinner spinner2;
    private Spinner spinner_apartamento;
    private EditText nome_hospede, telefone, data_entrada, data_saida, cpf, valor;
    private Button btn_concluir;
    private ArrayAdapter spinnerAdapter;
    private List<Apartamento> apartamentoList;
    private Apartamento apartamento;
    private Cal_Data cal_data;
    private String json;
    private TextView btn_reserva;
    private Funcionario funcionario;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reserva);
        context = this;
        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        btn_concluir = findViewById(R.id.btnConcluir_reserva);

        spinner2 = findViewById(R.id.reserva_n_pessoas_spinner);
        spinner_apartamento = findViewById(R.id.reserva_apartamento_spinner);
        nome_hospede = findViewById(R.id.reserva_hospede_nome);
        telefone = findViewById(R.id.reserva_telefone);
        data_entrada = findViewById(R.id.reservaData_entrada);
        data_saida = findViewById(R.id.reservaData_saida);
        cpf = findViewById(R.id.editText_cpf);
        valor = findViewById(R.id.editText_valor_hospedagem);

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

                ReservaController reservaController = new ReservaController(ReservaAddActivity.this);

                try {
                    json = (String) reservaController.valirar_reserva(funcionario,
                            apartamento, nome_hospede.getText().toString(), telefone.getText().toString(),
                            data_entrada.getText().toString(), data_saida.getText().toString(),
                            n_pessoas, cpf.getText().toString(),Float.parseFloat(valor.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Reserva_Request reserva_request = new Reserva_Request(ReservaAddActivity.this);

                if (json != null) {
                    reserva_request.cadastrar_reserva(json);
                    apartamento.setEstado("Reservado");
                    ApartamentoController ac = new ApartamentoController(ReservaAddActivity.this);

                    reserva_request.alterar_Apartamento(ac.converter_apartamento_json(apartamento), apartamento.getId());
                    spinnerAdapter.remove(apartamento);

                    spinner_apartamento.setAdapter(spinnerAdapter);
                    spinnerAdapter.notifyDataSetChanged();


                } else {

                    viewToastAlerta(context, "Preencha todos os campos *");
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

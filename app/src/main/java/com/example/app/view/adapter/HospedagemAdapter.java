package com.example.app.view.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.CheckinController;
import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.model.Hospedagem;
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Hospedagem_Request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;

public class HospedagemAdapter extends RecyclerView.Adapter<HospedagemAdapter.HospedagemViewHolder> {
    private Context ctx;
    private List<Hospedagem> hospedagemsList;
    private String est, json, pagamento;
    private AlertDialog alerta;
    private Hospedagem_Request hospedagem_request;
    private CheckinController checkin_controller;
    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner_apartamento;
    private int n_pessoas;
    private EditText nome_hospede, cpf, telefone, data_entrada, data_saida, v_hospedagem, valor_hospedagem;
    private ArrayAdapter spinnerAdapter;
    private List<Apartamento> apartamentoList;
    private Apartamento apartamento;
    private Funcionario funcionario;


    public HospedagemAdapter(Context ctx, List<Hospedagem> hospedagems, Funcionario funcionario) {
        this.ctx = ctx;
        hospedagemsList = hospedagems;
        this.funcionario = funcionario;
    }

    @NonNull
    @Override
    public HospedagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_hospedagem, parent, false);
        return new HospedagemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HospedagemViewHolder holder, final int position) {

        holder.apartamento_card.setText(hospedagemsList.get(position).getApartamento().getIdentificacao());
        holder.n_pessoas_card.setText(String.valueOf(hospedagemsList.get(position).getN_pessoas()));
        holder.v_hospedagem_card.setText(String.valueOf(hospedagemsList.get(position).getValor_hospedagem()));
        String data_e = new SimpleDateFormat("dd/MM/yyyy").format(hospedagemsList.get(position).getData_entrada());
        holder.data_entrada_card.setText(data_e);
        String data_s = new SimpleDateFormat("dd/MM/yyyy").format(hospedagemsList.get(position).getData_saida());
        holder.data_saida_card.setText(data_s);

        holder.telefone_card.setText(hospedagemsList.get(position).getHospede().getTelefone());
        holder.cpf_card.setText(hospedagemsList.get(position).getHospede().getCpf());
        holder.nome_hospede_card.setText(hospedagemsList.get(position).getHospede().getNome());
        holder.tipo_pagamento_card.setText(hospedagemsList.get(position).getTipo_pagamento());

        if (holder.tipo_pagamento_card.getText().equals("Não Pago")) {
            holder.tipo_pagamento_card.setTextColor(Color.RED);
        } else {
            holder.tipo_pagamento_card.setTextColor(Color.rgb(3, 130, 37));
        }


    }

    @Override
    public int getItemCount() {
        return hospedagemsList.size();
    }


    public class HospedagemViewHolder extends RecyclerView.ViewHolder {

        TextView nome_hospede_card;
        TextView cpf_card;
        TextView telefone_card;
        TextView data_entrada_card;
        TextView data_saida_card;
        TextView v_hospedagem_card;
        TextView n_pessoas_card;
        TextView tipo_pagamento_card;
        TextView apartamento_card;


        public HospedagemViewHolder(final View itemView) {
            super(itemView);

            nome_hospede_card = itemView.findViewById(R.id.editText_hospede_nome_card);
            cpf_card = itemView.findViewById(R.id.editText_hospede_cpf_card);
            telefone_card = itemView.findViewById(R.id.editText_hospede_telefone_card);
            data_entrada_card = itemView.findViewById(R.id.editTextData_entrada_card);
            data_saida_card = itemView.findViewById(R.id.editTextData_saida_card);
            v_hospedagem_card = itemView.findViewById(R.id.editText_valor_hospedagem_card);
            n_pessoas_card = itemView.findViewById(R.id.editText_n_pessoas_card);
            tipo_pagamento_card = itemView.findViewById(R.id.editText_tipo_pagamento_card);
            apartamento_card = itemView.findViewById(R.id.editText_apartamento_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nome_hospede = itemView.findViewById(R.id.editText_hospede_nome);
                    cpf = itemView.findViewById(R.id.editText_cpf);
                    telefone = itemView.findViewById(R.id.editText_telefone);
                    data_entrada = itemView.findViewById(R.id.editTextData_entrada);
                    data_saida = itemView.findViewById(R.id.editTextData_saida);
                    v_hospedagem = itemView.findViewById(R.id.editText_valor_hospedagem);


                    final int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                        LayoutInflater inflater = LayoutInflater.from(ctx);
                        View layout = inflater.inflate(R.layout.alert_add_hospedagem, null);

                        layout.findViewById(R.id.btnCadAp);
                        final View finalView = layout;

                        TextView titulo;
                        titulo = finalView.findViewById(R.id.txt_titulo);

                        titulo.setText("  Alterar dados hospedagem");

                        spinner = finalView.findViewById(R.id.pagamento_spinner);
                        spinner2 = finalView.findViewById(R.id.n_pessoas_spinner);
                        spinner_apartamento = finalView.findViewById(R.id.apartamento_spinner);

                        nome_hospede = finalView.findViewById(R.id.editText_hospede_nome);

                        nome_hospede.setText(hospedagemsList.get(pos).getHospede().getNome());

                        cpf = finalView.findViewById(R.id.editText_cpf);
                        cpf.setText(hospedagemsList.get(pos).getHospede().getCpf());

                        telefone = finalView.findViewById(R.id.editText_telefone);
                        telefone.setText(hospedagemsList.get(pos).getHospede().getTelefone());

                        String data_e = new SimpleDateFormat("dd/MM/yyyy").format(hospedagemsList.get(pos).getData_entrada());
                        data_entrada = finalView.findViewById(R.id.editTextData_entrada);
                        data_entrada.setText(data_e);

                        String data_s = new SimpleDateFormat("dd/MM/yyyy").format(hospedagemsList.get(pos).getData_saida());
                        data_saida = finalView.findViewById(R.id.editTextData_saida);
                        data_saida.setText(data_s);

                        valor_hospedagem = finalView.findViewById(R.id.editText_valor_hospedagem);

                        System.out.println(String.valueOf(hospedagemsList.get(pos).getValor_hospedagem()));

                        valor_hospedagem.setText(String.valueOf(hospedagemsList.get(pos).getValor_hospedagem()));

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx,
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
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(ctx,
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

                        Apartamento_Request apartamento_request = new Apartamento_Request(ctx);

                        apartamento = new Apartamento();
                        apartamentoList = new ArrayList<>();
                        apartamentoList.add(hospedagemsList.get(pos).getApartamento());

                        spinnerAdapter = new ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, apartamentoList);
                        apartamento_request.buscar_por_estado(apartamentoList, spinnerAdapter, funcionario.getAdministrador_id());
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


                        layout.findViewById(R.id.btnConcluir_hospedagemh).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("WrongConstant")
                            public void onClick(View arg0) {

                                Long apId;


                                apId = hospedagemsList.get(pos).getId();
                                System.out.println(apId);

                                checkin_controller = new CheckinController(ctx);
                                hospedagem_request = new Hospedagem_Request(ctx);

                                try {
                                    json = checkin_controller.valirar_checkin_altera(funcionario,apId, apartamento, cpf.getText().toString(), nome_hospede.getText().toString(), telefone.getText().toString(), data_entrada.getText().toString(), data_saida.getText().toString(), pagamento, Float.parseFloat(valor_hospedagem.getText().toString()), n_pessoas);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (json != null) {
                                    hospedagem_request.alterar_hospedagem(json, apId);
                                    alerta.dismiss();
                                    atualizar(pos, checkin_controller.converter_json_hospedagem(json));

                                    viewToast(ctx, "Hospedagem alterada");

                                } else {
                                    viewToastAlerta(ctx,"Preencha todos os campos!*");

                                }

                            }
                        });

                        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                        builder.setView(layout);
                        alerta = builder.create();
                        alerta.show();


                    }

                }
            });


        }

    }

    public List<Hospedagem> getHospedagemsList() {
        return hospedagemsList;
    }

    public void sethospedagemsList(List<Hospedagem> hospedagemsList) {
        this.hospedagemsList = hospedagemsList;
        notifyDataSetChanged();
    }

    public void addHospedagem(Hospedagem ap) {
        getHospedagemsList().add(ap);
        notifyDataSetChanged();
    }

    public void atualizar(int pos, Hospedagem ap) {
        getHospedagemsList().set(pos, ap);
        notifyItemChanged(pos);
        notifyDataSetChanged();
    }

}

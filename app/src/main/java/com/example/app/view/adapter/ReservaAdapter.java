package com.example.app.view.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.ApartamentoController;
import com.example.app.controller.ReservaController;
import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.model.Reserva;
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Reserva_Request;
import com.example.app.view.CheckinActivity;
import com.example.app.view.PainelActivity;
import com.example.app.view.RelatorioActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private Context ctx;
    private List<Reserva> reservasList;
    private String est;
    private AlertDialog alerta;
    private Reserva_Request reserva_request;
    private ReservaController reserva_controller;
    private String json, pagamento;
    private Spinner spinner, spinner2, spinner_apartamento;
    private int n_pessoas;
    private EditText nome_hospede, cpf, telefone, data_entrada, data_saida, v_reserva, valor_reserva;
    private ArrayAdapter spinnerAdapter;
    private List<Apartamento> apartamentoList;
    private Apartamento apartamento;
    private Funcionario funcionario;


    public ReservaAdapter(Context ctx, List<Reserva> reservas, Funcionario funcionario) {
        this.ctx = ctx;
        reservasList = reservas;
        this.funcionario = funcionario;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_reserva, parent, false);
        return new ReservaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReservaViewHolder holder, final int position) {

        holder.apartamento_card.setText(reservasList.get(position).getApartamento().getIdentificacao());
        holder.reserva_id.setText(holder.reserva_id.getText() + String.valueOf(reservasList.get(position).getId()));

        String data_e = new SimpleDateFormat("dd/MM/yyyy").format(reservasList.get(position).getData_entrada());
        holder.data_entrada_card.setText(data_e);
        String data_s = new SimpleDateFormat("dd/MM/yyyy").format(reservasList.get(position).getData_saida());
        holder.data_saida_card.setText(data_s);

        holder.telefone_card.setText(reservasList.get(position).getHospede().getTelefone());
        holder.nome_hospede_card.setText(reservasList.get(position).getHospede().getNome());
        holder.n_pessoas_card.setText(String.valueOf(reservasList.get(position).getN_pessoas()));


    }

    @Override
    public int getItemCount() {
        return reservasList.size();
    }


    public class ReservaViewHolder extends RecyclerView.ViewHolder {

        TextView nome_hospede_card, telefone_card, data_entrada_card, data_saida_card, n_pessoas_card, apartamento_card, reserva_id;
        ImageView btn_reserva_checkin;

        public ReservaViewHolder(final View itemView) {
            super(itemView);

            nome_hospede_card = itemView.findViewById(R.id.reserva_hospede_nome_card);
            telefone_card = itemView.findViewById(R.id.reserva_hospede_telefone_card);
            data_entrada_card = itemView.findViewById(R.id.reserva_Data_entrada_card);
            data_saida_card = itemView.findViewById(R.id.reserva_Data_saida_card);
            n_pessoas_card = itemView.findViewById(R.id.reserva_n_pessoas_card);
            apartamento_card = itemView.findViewById(R.id.reserva_apartamento_card);
            btn_reserva_checkin = itemView.findViewById(R.id.reserva_checkin);
            reserva_id = itemView.findViewById(R.id.textViewap);

            btn_reserva_checkin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        Intent intent = new Intent(ctx, CheckinActivity.class);
                        intent.putExtra("reserva_hospede", reservasList.get(pos).getHospede());
                        intent.putExtra("reserva_data_entrada", reservasList.get(pos).getData_entrada());
                        intent.putExtra("reserva_data_saida", reservasList.get(pos).getData_saida());
                        intent.putExtra("reserva_n_pessoas", reservasList.get(pos).getN_pessoas());
                        intent.putExtra("reserva_valor", reservasList.get(pos).getValor());
                        intent.putExtra("reserva_apartamento", reservasList.get(pos).getApartamento());
                        intent.putExtra("reserva_id", reservasList.get(pos).getId());
                        intent.putExtra("funcionario_r", funcionario);
                        ctx.startActivity(intent);

                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nome_hospede = itemView.findViewById(R.id.editText_hospede_nome);
                    telefone = itemView.findViewById(R.id.editText_telefone);
                    data_entrada = itemView.findViewById(R.id.editTextData_entrada);
                    data_saida = itemView.findViewById(R.id.editTextData_saida);


                    final int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                        LayoutInflater inflater = LayoutInflater.from(ctx);
                        View layout = inflater.inflate(R.layout.alert_add_hospedagem, null);

                        layout.findViewById(R.id.btnCadAp);
                        final View finalView = layout;

                        TextView titulo;
                        titulo = finalView.findViewById(R.id.txt_titulo);

                        titulo.setText("Alterar dados da reserva");
                        final EditText valor = layout.findViewById(R.id.editText_valor_hospedagem);
                        valor.setText(String.valueOf(reservasList.get(pos).getValor()));

                        spinner = finalView.findViewById(R.id.pagamento_spinner);
                        spinner2 = finalView.findViewById(R.id.n_pessoas_spinner);
                        spinner_apartamento = finalView.findViewById(R.id.apartamento_spinner);

                        nome_hospede = finalView.findViewById(R.id.editText_hospede_nome);

                        nome_hospede.setText(reservasList.get(pos).getHospede().getNome());

                        cpf = finalView.findViewById(R.id.editText_cpf);

                        cpf.setText(reservasList.get(pos).getHospede().getCpf());

                        telefone = finalView.findViewById(R.id.editText_telefone);
                        telefone.setText(reservasList.get(pos).getHospede().getTelefone());

                        String data_e = new SimpleDateFormat("dd/MM/yyyy").format(reservasList.get(pos).getData_entrada());
                        data_entrada = finalView.findViewById(R.id.editTextData_entrada);
                        data_entrada.setText(data_e);

                        String data_s = new SimpleDateFormat("dd/MM/yyyy").format(reservasList.get(pos).getData_saida());
                        data_saida = finalView.findViewById(R.id.editTextData_saida);
                        data_saida.setText(data_s);


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

                        final Apartamento_Request apartamento_request = new Apartamento_Request(ctx);

                        apartamento = new Apartamento();
                        apartamentoList = new ArrayList<>();
                        apartamentoList.add(reservasList.get(pos).getApartamento());


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

                                apId = reservasList.get(pos).getId();
                                System.out.println(apId);

                                reserva_controller = new ReservaController(ctx);
                                reserva_request = new Reserva_Request(ctx);

                                try {
                                    json = reserva_controller.valirar_reserva_altera(
                                            funcionario, apId, apartamento, nome_hospede.getText().toString(),
                                            telefone.getText().toString(), data_entrada.getText().toString(),
                                            data_saida.getText().toString(), n_pessoas, cpf.getText().toString(), Float.parseFloat(valor.getText().toString()));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (json != null) {
                                    reserva_request.alterar_reserva(json, apId);
                                    alerta.dismiss();
                                    ApartamentoController apartamentoController = new ApartamentoController(ctx);

                                    if (reservasList.get(pos).getApartamento().getIdentificacao().equals(apartamento.getIdentificacao())) {
                                        apartamento.setEstado("Reservado");
                                        String apjson = apartamentoController.converter_apartamento_json(apartamento);
                                        apartamento_request.alterar_Apartamento(apjson, apartamento.getId());

                                    } else {
                                        reservasList.get(pos).getApartamento().setEstado("Disponível");
                                        String apjson = apartamentoController.converter_apartamento_json(reservasList.get(pos).getApartamento());
                                        apartamento_request.alterar_Apartamento(apjson, reservasList.get(pos).getId());

                                        apartamento.setEstado("Reservado");
                                        apjson = apartamentoController.converter_apartamento_json(apartamento);
                                        apartamento_request.alterar_Apartamento(apjson, apartamento.getId());

                                    }
                                    atualizar(pos, reserva_controller.converter_json_reserva(json));



                                    viewToast(ctx, "Reserva alterada!");


                                } else {

                                    viewToastAlerta(ctx, "Preencha todos os campos!");

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

    public List<Reserva> getReservasList() {
        return reservasList;
    }

    public void setreservasList(List<Reserva> reservasList) {
        this.reservasList = reservasList;
        notifyDataSetChanged();
    }

    public void addReserva(Reserva ap) {
        getReservasList().add(ap);
        notifyDataSetChanged();
    }

    public void atualizar(int pos, Reserva ap) {
        getReservasList().set(pos, ap);
        notifyItemChanged(pos);
        notifyDataSetChanged();
    }

}

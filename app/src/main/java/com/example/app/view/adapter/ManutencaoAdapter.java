package com.example.app.view.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.ApartamentoController;
import com.example.app.controller.ManutencaoController;
import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.model.Manutencao;
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Manutencao_Request;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.view.CustonToast.viewToastAlerta;

public class ManutencaoAdapter extends RecyclerView.Adapter<ManutencaoAdapter.ManutencaoViewHolder> {
    private Context ctx;
    private List<Manutencao> manutencaoslis;
    private List<Apartamento> apartamentoList;
    private AlertDialog alerta;
    private String json;
    private ManutencaoController manutencaoController;
    private Manutencao_Request manutencao_request;
    private TextView txt;
    private Apartamento apartamento;
    private ArrayAdapter spinnerAdapter;
    private Funcionario funcinario;
    private Spinner spinner_apartamento;
    private Apartamento_Request apartamento_request;
    private ApartamentoController apartamentoController;

    public ManutencaoAdapter(Context ctx, List<Manutencao> manutencaos, TextView text, Funcionario funcionario) {
        this.ctx = ctx;
        this.funcinario = funcionario;
        manutencaoslis = manutencaos;
        notifyDataSetChanged();
        txt = text;
    }

    @NonNull
    @Override
    public ManutencaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_manutencao, parent, false);
        return new ManutencaoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ManutencaoViewHolder holder, int position) {

        holder.ap.setText("AP "+manutencaoslis.get(position).getApartamento().getIdentificacao());

        String data_e = new SimpleDateFormat("dd/MM/yyyy").format(manutencaoslis.get(position).getData_solicitacao());
        holder.data.setText("Data da solicitação: "+data_e);

        holder.funcionario_nome.setText("Solicitada por: "+manutencaoslis.get(position).getFuncionario().getNome());
        holder.observacao.setText("Observação: "+manutencaoslis.get(position).getObservacao());
    }

    @Override
    public int getItemCount() {
        return manutencaoslis.size();
    }

    public class ManutencaoViewHolder extends RecyclerView.ViewHolder {

        TextView ap, data, funcionario_nome, observacao;


        public ManutencaoViewHolder(View itemView) {
            super(itemView);
            ap = itemView.findViewById(R.id.card_manutencao_ap);
            data = itemView.findViewById(R.id.card_manutencao_data);
            funcionario_nome = itemView.findViewById(R.id.card_manutencao_funcionario);
            observacao = itemView.findViewById(R.id.card_manutencao_observacao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                        LayoutInflater inflater = LayoutInflater.from(ctx);
                        View layout = inflater.inflate(R.layout.alert_add_manutencao, null);


                        layout.findViewById(R.id.btn_Consluir_Fun);
                        final View finalView = layout;

                        TextView titulo;
                        titulo = finalView.findViewById(R.id.txt_titulo);

                        titulo.setText("Alterar dados da manutenção");

                        final EditText obs = finalView.findViewById(R.id.add_manutencao_observacao);

                        obs.setText(manutencaoslis.get(pos).getObservacao());


                        apartamento = new Apartamento();
                        apartamentoList = new ArrayList<>();
                        apartamento_request = new Apartamento_Request(ctx);

                        apartamentoList.add(manutencaoslis.get(pos).getApartamento());

                        spinner_apartamento = finalView.findViewById(R.id.apartamento_spinner);

                        spinnerAdapter = new ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, apartamentoList);
                        apartamento_request.buscar_por_estado(apartamentoList, spinnerAdapter, funcinario.getAdministrador_id());
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

                        layout.findViewById(R.id.btn_Consluir_Fun).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("WrongConstant")
                            public void onClick(View arg0) {

                              manutencaoController = new ManutencaoController(ctx);
                              manutencao_request= new Manutencao_Request(ctx);
                              ApartamentoController apartamentoController = new ApartamentoController(ctx);

                                json = manutencaoController.alterar(manutencaoslis.get(pos).getId()
                                ,manutencaoslis.get(pos).getEstado(),manutencaoslis.get(pos).getData_solicitacao()
                                ,funcinario,obs.getText().toString(),apartamento);

                                if (json != null) {

                                    if (manutencaoslis.get(pos).getApartamento().getIdentificacao().equals(apartamento.getIdentificacao())) {
                                        apartamento.setEstado("Manutenção");
                                        String apjson = apartamentoController.converter_apartamento_json(apartamento);
                                        apartamento_request.alterar_Apartamento(apjson, apartamento.getId());

                                    } else {
                                        manutencaoslis.get(pos).getApartamento().setEstado("Disponível");
                                        String apjson = apartamentoController.converter_apartamento_json(manutencaoslis.get(pos).getApartamento());
                                        apartamento_request.alterar_Apartamento(apjson, manutencaoslis.get(pos).getId());

                                        apartamento.setEstado("Manutenção");
                                        apjson = apartamentoController.converter_apartamento_json(apartamento);
                                        apartamento_request.alterar_Apartamento(apjson, apartamento.getId());

                                    }
                                    manutencao_request.alterar_manutencao(json, manutencaoslis.get(pos).getId());
                                    alerta.dismiss();
                                    atualizar(pos, manutencaoController.converter_json_manutencao(json));

                                } else {
                                    viewToastAlerta(ctx, "Preencha todos os campos *");

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

    public List<Manutencao> getManutencaoslist() {
        return manutencaoslis;
    }

    public void setManutencaoslis(List<Manutencao> manutencaoslis, ProgressBar progressBar) {
        this.manutencaoslis = manutencaoslis;
        notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        if (manutencaoslis.size() > 0) {
            txt.setVisibility(View.GONE);
        }

    }

    public void addManutencaoo(Manutencao fum) {
        getManutencaoslist().add(fum);
        notifyDataSetChanged();
        if (manutencaoslis.size() > 0) {
            txt.setVisibility(View.GONE);
        }
    }

    public void atualizar(int pos, Manutencao fun) {

        notifyDataSetChanged();
        manutencaoslis.set(pos, fun);
        notifyItemChanged(pos);
        notifyDataSetChanged();
        if (manutencaoslis.size() > 0) {
            txt.setVisibility(View.GONE);
        }
    }
}

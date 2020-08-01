package com.example.app.view.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
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
import com.example.app.controller.ApartamentoController;
import com.example.app.controller.LimpezaController;
import com.example.app.controller.ManutencaoController;
import com.example.app.controller.ReservaController;
import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.model.Manutencao;
import com.example.app.request.Apartamento_Request;
import com.example.app.request.Limpeza_Request;
import com.example.app.request.Manutencao_Request;
import com.example.app.request.Reserva_Request;

import java.util.List;

import static android.view.View.GONE;
import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;

public class LimpezaAdapter extends RecyclerView.Adapter<LimpezaAdapter.ApartamentoViewHolder> {
    private Context ctx;
    private List<Apartamento> apartamentosList;
    private String est, json;
    private AlertDialog alerta;
    private Apartamento_Request apr;
    private ApartamentoController apc;
    private Funcionario funcionario;
    private TextView msn, qtdsujo;


    public LimpezaAdapter(Context ctx, List<Apartamento> apartamentos, Funcionario funcionario, TextView msn, TextView qtdsujo) {
        this.ctx = ctx;
        this.msn = msn;
        this.qtdsujo = qtdsujo;
        apartamentosList = apartamentos;
        this.funcionario = funcionario;

    }

    @NonNull
    @Override
    public ApartamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_limpeza, parent, false);
        return new ApartamentoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ApartamentoViewHolder holder, final int position) {

        holder.identificacao.setText(apartamentosList.get(position).getIdentificacao());
        holder.estado.setText(apartamentosList.get(position).getEstado());


    }

    @Override
    public int getItemCount() {
        return apartamentosList.size();
    }


    public class ApartamentoViewHolder extends RecyclerView.ViewHolder {

        TextView identificacao;
        TextView estado;

        public ApartamentoViewHolder(final View itemView) {
            super(itemView);
            identificacao = itemView.findViewById(R.id.identificacao_limpeza_card);
            estado = itemView.findViewById(R.id.estad_limpeza_cardo);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        if (apartamentosList.get(pos).getEstado().equals("")) {

                        } else {
                            LayoutInflater inflater = LayoutInflater.from(ctx);
                            View layout = inflater.inflate(R.layout.alert_limpeza_apartamento, null);

                            layout.findViewById(R.id.btnCadAp);


                            TextView titulo;
                            final TextView txtObs;
                            titulo = layout.findViewById(R.id.txt_titulo);
                            txtObs = layout.findViewById(R.id.txtObs);

                            titulo.setText("Alterar estado do Apartamento");

                            final EditText descricao = layout.findViewById(R.id.editText_DescricaoAp);

                            Spinner spinner = (Spinner) layout.findViewById(R.id.estados_spinner);

                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx,
                                    R.array.estados_array, android.R.layout.simple_spinner_item);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    est = (String) parent.getItemAtPosition(position);
                                    if (est.equals("Manutenção")) {
                                        txtObs.setVisibility(View.VISIBLE);
                                        descricao.setVisibility(View.VISIBLE);
                                    } else {
                                        txtObs.setVisibility(GONE);
                                        descricao.setVisibility(GONE);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    est = "";
                                }
                            });


                            layout.findViewById(R.id.btnCadAp).setOnClickListener(new View.OnClickListener() {
                                @SuppressLint("WrongConstant")
                                public void onClick(View arg0) {

                                    Long apId;
                                    apId = apartamentosList.get(pos).getId();
                                    System.out.println(apId);

                                    apc = new ApartamentoController(ctx);
                                    apr = new Apartamento_Request(ctx);

                                    LimpezaController limpezaController = new LimpezaController(ctx);
                                    Limpeza_Request limpeza_request = new Limpeza_Request(ctx);

                                    json = apc.valirar_alterar_Apartamento(
                                            funcionario, apartamentosList.get(pos).getId(), apartamentosList.get(pos).getIdentificacao().toString()
                                            , est, apartamentosList.get(pos).getDescricao());


                                    if (json != null) {
                                        apr.alterar_Apartamento(json, apId);

                                        if (est.equals("Manutenção")) {

                                            limpeza_request.cadastrar_limpeza(limpezaController.valirar_cadastro_lipenza(apc.converter_json_apartamento_(json), funcionario));
                                            ManutencaoController manutencaoController = new ManutencaoController(ctx);
                                            Manutencao_Request manutencao_request = new Manutencao_Request(ctx);

                                            String r = manutencaoController.cadastrar(funcionario, descricao.getText().toString(), apartamentosList.get(pos));
                                            if (r != null) {
                                                manutencao_request.cadastrarManutencao(r);
                                            }


                                        } else {

                                            limpeza_request.cadastrar_limpeza(limpezaController.valirar_cadastro_lipenza(apc.converter_json_apartamento_(json), funcionario));
                                        }


                                        alerta.dismiss();
                                        getApartamentosList().remove(pos);

                                        if (getApartamentosList().size() <= 0) {
                                            msn.setVisibility(View.VISIBLE);
                                        } else {
                                            msn.setVisibility(GONE);
                                        }

                                        qtdsujo.setText(String.valueOf(getApartamentosList().size()));

                                        notifyDataSetChanged();

                                        viewToast(ctx, "Estado do apartamento alterado!");


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

                }
            });

        }

    }

    public List<Apartamento> getApartamentosList() {
        notifyDataSetChanged();
        return apartamentosList;
    }

    public void setApartamentosList(List<Apartamento> apartamentosList) {
        this.apartamentosList = apartamentosList;
        notifyDataSetChanged();
    }

    public void addApartamento(Apartamento ap) {
        getApartamentosList().add(ap);
        notifyDataSetChanged();
    }

    public void atualizar(int pos, Apartamento ap) {
        getApartamentosList().set(pos, ap);
        notifyItemChanged(pos);
        notifyDataSetChanged();
    }

}

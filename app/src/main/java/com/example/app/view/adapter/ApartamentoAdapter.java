package com.example.app.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.ApartamentoController;
import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.request.Apartamento_Request;
import com.example.app.view.LoginActivity;

import java.util.List;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;

public class ApartamentoAdapter extends RecyclerView.Adapter<ApartamentoAdapter.ApartamentoViewHolder> {
    private Context ctx;
    private List<Apartamento> apartamentosList;
    private String est, json;
    private AlertDialog alerta;
    private Apartamento_Request apr;
    private ApartamentoController apc;
    private Funcionario funcionario;


    public ApartamentoAdapter(Context ctx, List<Apartamento> apartamentos, Funcionario funcionario) {
        this.ctx = ctx;
        apartamentosList = apartamentos;
        this.funcionario = funcionario;

    }

    @NonNull
    @Override
    public ApartamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_apartamento, parent, false);
        return new ApartamentoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ApartamentoViewHolder holder, final int position) {

        holder.identificacao.setText(apartamentosList.get(position).getIdentificacao());
        holder.descricao.setText(apartamentosList.get(position).getDescricao());
        holder.estado.setText(apartamentosList.get(position).getEstado());
    }

    @Override
    public int getItemCount() {
        return apartamentosList.size();
    }


    public class ApartamentoViewHolder extends RecyclerView.ViewHolder {

        TextView identificacao;
        TextView descricao;
        TextView estado;


        public ApartamentoViewHolder(final View itemView) {
            super(itemView);
            identificacao = itemView.findViewById(R.id.card_identificacao);
            descricao = itemView.findViewById(R.id.card_descricao);
            estado = itemView.findViewById(R.id.card_estado);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        if (apartamentosList.get(pos).getEstado().equals("Ocupado") || apartamentosList.get(pos).getEstado().equals("Reservado")
                                || apartamentosList.get(pos).getEstado().equals("Manutenção")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);

                            alertDialogBuilder
                                    .setMessage("O apartamento " + apartamentosList.get(pos).getIdentificacao() +
                                            " não pode ser alterado no momento, pois se encontra no status de: " + apartamentosList.get(pos).getEstado())
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }
                                    })
                                    .setNegativeButton("", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {

                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                            LayoutInflater inflater = LayoutInflater.from(ctx);
                            View layout = inflater.inflate(R.layout.alert_add_apartamento, null);


                            layout.findViewById(R.id.btnCadAp);
                            final View finalView = layout;

                            TextView titulo;
                            titulo = finalView.findViewById(R.id.txt_titulo);

                            titulo.setText("Alterar dados do apartamento");
                            final EditText identificacao;
                            final EditText descricao;


                            identificacao = finalView.findViewById(R.id.editText_identificacaoAP);
                            descricao = finalView.findViewById(R.id.editText_DescricaoAp);
                            identificacao.setText(apartamentosList.get(pos).getIdentificacao());
                            descricao.setText(apartamentosList.get(pos).getDescricao());

                            Spinner spinner = (Spinner) layout.findViewById(R.id.estados_spinner);

                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx,
                                    R.array.estados_array, android.R.layout.simple_spinner_item);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    est = (String) parent.getItemAtPosition(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    est = "";
                                }
                            });


                            layout.findViewById(R.id.btnCadAp).setOnClickListener(new View.OnClickListener() {
                                public void onClick(View arg0) {

                                    Long apId;


                                    apId = apartamentosList.get(pos).getId();
                                    System.out.println(apId);

                                    apc = new ApartamentoController(ctx);
                                    apr = new Apartamento_Request(ctx);

                                    json = apc.valirar_alterar_Apartamento(funcionario, apId, identificacao.getText().toString(), est, descricao.getText().toString());
                                    if (json != null) {
                                        apr.alterar_Apartamento(json, apId);
                                        alerta.dismiss();
                                        atualizar(pos, apc.converter_json_apartamento_(json));

                                        viewToast(ctx, "Apartamento alterado!");

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

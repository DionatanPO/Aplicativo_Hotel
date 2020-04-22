package com.example.app.view;

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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.FuncionarioController;
import com.example.app.model.Funcionario;
import com.example.app.request.Funcionario_Request;

import java.util.List;

public class FuncionarioAdapter extends RecyclerView.Adapter<FuncionarioAdapter.FuncionarioViewHolder> {
    Context ctx;
    List<Funcionario> funcionarioslis;
    private AlertDialog alerta;
    String json;
    FuncionarioController func;
    Funcionario_Request funR;
    String cargos;
    TextView txt;



    public FuncionarioAdapter(Context ctx, List<Funcionario> funcionarios, TextView text ) {
        this.ctx = ctx;
        funcionarioslis = funcionarios;
        notifyDataSetChanged();
        txt= text;
    }




    @NonNull
    @Override
    public FuncionarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_funcionario, parent, false);
        return new FuncionarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FuncionarioViewHolder holder, int position) {

        holder.nome.setText(funcionarioslis.get(position).getNome());
        holder.email.setText(funcionarioslis.get(position).getEmail());
        holder.cargo.setText(funcionarioslis.get(position).getCargo());
    }

    @Override
    public int getItemCount() {
        return funcionarioslis.size();
    }

    public class FuncionarioViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView email;
        TextView cargo;




        public FuncionarioViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.cardNome);
            email = itemView.findViewById(R.id.cardEmail);
            cargo = itemView.findViewById(R.id.cardCargo);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ctx);
                        LayoutInflater inflater = LayoutInflater.from(ctx);
                        View layout = inflater.inflate(R.layout.alert_add_funcionario, null);


                        layout.findViewById(R.id.btn_Consluir_Fun);
                        final View finalView = layout;

                        TextView titulo;
                        titulo = finalView.findViewById(R.id.txt_titulo);

                        titulo.setText("   Alterar dados do Funcionário");

                        final EditText fumNome =finalView.findViewById(R.id.editTextFunNome);
                        final EditText fumcpf = finalView.findViewById(R.id.editTextFumCpf);
                        final EditText fumEmail = finalView.findViewById(R.id.editTextFumEmail);

                        final EditText fumSenha = finalView.findViewById(R.id.editTextFunSena);
                        final EditText fumSenha2 = finalView.findViewById(R.id.editTextFumSenha2);

                        fumNome.setText(funcionarioslis.get(pos).getNome());
                        fumcpf.setText(String.valueOf(funcionarioslis.get(pos).getCpf()));
                        fumEmail.setText(funcionarioslis.get(pos).getEmail());



                        Spinner spinner = (Spinner) layout.findViewById(R.id.cargosFum_spinner);

                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx,
                                R.array.cargosfum_array, android.R.layout.simple_spinner_item);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                cargos = (String) parent.getItemAtPosition(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                cargos = "";
                            }
                        });

                        layout.findViewById(R.id.btn_Consluir_Fun).setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("WrongConstant")
                            public void onClick(View arg0) {



                                Long funId;


                                funId = funcionarioslis.get(pos).getId();

                                func = new FuncionarioController(ctx);
                                funR = new Funcionario_Request(ctx);

                                json = func.valirar_alterar_funcionario(funId, fumNome.getText().toString(), fumEmail.getText().toString(), fumcpf.getText().toString()
                                        , cargos, fumSenha.getText().toString(), fumSenha2.getText().toString());
                                if (json != null) {
                                    funR.alterar_funcionario(json, funId);
                                    alerta.dismiss();
                                    atualizar(pos, func.converter_json_funcionario(json));
                                    LayoutInflater inflater2 = LayoutInflater.from(ctx);
                                    View layout2 = inflater2.inflate(R.layout.custom_toast, null);
                                    TextView text = (TextView) layout2.findViewById(R.id.text);
                                    text.setText("Funcioná,rio alterado");

                                    Toast toast = new Toast(ctx);

                                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                                    toast.setDuration(9000);
                                    toast.setView(layout2);
                                    toast.show();
                                } else {
                                    LayoutInflater inflater2 = LayoutInflater.from(ctx);
                                    View layout2 = inflater2.inflate(R.layout.custom_toast, null);
                                    layout2.setBackgroundResource(R.color.alerta);
                                    TextView text = (TextView) layout2.findViewById(R.id.text);
                                    text.setText("Preencha todos os campos *");

                                    Toast toast = new Toast(ctx);

                                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                                    toast.setDuration(9000);
                                    toast.setView(layout2);
                                    toast.show();
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

    public List<Funcionario> getFuncionarioslist() {
        return funcionarioslis;
    }

    public void setFuncionarioslis(List<Funcionario> funcionarioslis, ProgressBar progressBar) {
        this.funcionarioslis = funcionarioslis;
        notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
       if(funcionarioslis.size()>0){
           txt.setVisibility(View.GONE);
       }

    }

    public void addFuncionarioo(Funcionario fum) {

        getFuncionarioslist().add(fum);
        notifyDataSetChanged();
        if(funcionarioslis.size()>0){
            txt.setVisibility(View.GONE);
        }
    }
    public void atualizar(int pos, Funcionario fun) {

        funcionarioslis.set(pos, fun);
        notifyItemChanged(pos);
        notifyDataSetChanged();
        if(funcionarioslis.size()>0){
            txt.setVisibility(View.GONE);
        }
    }
}

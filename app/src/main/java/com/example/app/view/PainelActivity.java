package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.app.R;

public class PainelActivity extends Activity implements PopupMenu.OnMenuItemClickListener {
    private TextView btn_funcionario, btn_check_in, btn_limpeza, btn_apartamento, btn_hospedagem, btn_reserva, btn_relatorio;
    private Button button_menu;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        context = this;

        btn_funcionario = findViewById(R.id.buttonFuncionarios);
        btn_check_in = findViewById(R.id.btn_check_in);
        btn_limpeza = findViewById(R.id.btn_limpeza);
        btn_apartamento = findViewById(R.id.btn_apartamento);
        btn_hospedagem = findViewById(R.id.btn_hospedagem);
        btn_reserva = findViewById(R.id.btn_reserva);
        button_menu = findViewById(R.id.painel_btn_menu);
        btn_relatorio = findViewById(R.id.btn_relatorio);

        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(PainelActivity.this, view);
                popup.setOnMenuItemClickListener(PainelActivity.this);
                popup.inflate(R.menu.menu);
                popup.show();
            }
        });

//       btn_funcionario.setVisibility(View.INVISIBLE);
        btn_funcionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, FuncionarioActivity.class);
                startActivity(i);
            }
        });
        btn_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, CheckinActivity.class);
                startActivity(i);
            }
        });

        btn_limpeza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, LimpezaActivity.class);
                startActivity(i);
            }
        });
        btn_apartamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, ApartamentoActivity.class);
                startActivity(i);
            }
        });

        btn_hospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, HospedagemActivity.class);
                startActivity(i);
            }
        });

        btn_reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, ReservaActivity.class);
                startActivity(i);
            }
        });

        btn_relatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, RelatorioActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.perfil:
                AlertDialog alerta;

                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.create_account, null);
                final TextView titulo = layout.findViewById(R.id.textView2);
                final TextView titulo2 = layout.findViewById(R.id.textview_criarconta);
                titulo.setText("Alterar seus dados");
                titulo2.setText("Informe seus dados");

            Button button_concluir_altera_conta = layout.findViewById(R.id.btnCreate);
//                final EditText editText_conta_nome = layout.findViewById(R.id.alterar_conta_nome);
//                final EditText editText_conta_fazenda = layout.findViewById(R.id.alterar_conta_fazenda);
//                final EditText editText_conta_senha = layout.findViewById(R.id.alterar_conta_senha);
//                final EditText editText_conta_identificacao = layout.findViewById(R.id.alterar_conta_identificacao);
//
//                if (users != null) {
//                    editText_conta_nome.setText(users.getNome());
//                    editText_conta_identificacao.setText(users.getIdentificacao());
//                    editText_conta_fazenda.setText(users.getFazenda());
//                    editText_conta_senha.setText("");
//
//
//                }
//

                button_concluir_altera_conta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        String json = usersController.valirar_alterar_users(users.getId(), editText_conta_nome.getText().toString(),
//                                editText_conta_fazenda.getText().toString(), editText_conta_identificacao.getText().toString(), editText_conta_senha.getText().toString());
//                        if (json != null) {
//                            usuarioRequest.alterarUsuario(json, token);
//                            users = usersController.converter_json_users(json);
//                        } else {
//                            viewToastAlerta(context, "Por favor, preencha os campos obrigatórios *");
//                        }
                    }
                });


                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setView(layout);

                alerta = builder.create();
                alerta.show();
                return true;
            case R.id.sair:
                Intent i = new Intent(PainelActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder
                .setMessage( "Deseja mesmo sair?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {

                        Intent i = new Intent(context, LoginActivity.class);
                        startActivity(i);
                        finishAffinity();

                        dialog.cancel();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}


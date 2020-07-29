package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.app.R;
import com.example.app.controller.FuncionarioController;
import com.example.app.model.Funcionario;
import com.example.app.request.Funcionario_Request;

import static android.graphics.Color.GRAY;
import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;

public class PainelActivity extends Activity implements PopupMenu.OnMenuItemClickListener {
    private TextView btn_funcionario, btn_check_in, btn_limpeza, btn_apartamento, btn_hospedagem, btn_reserva, btn_relatorio, btn_manutencao;
    private Button button_menu;
    private Context context;
    private Funcionario funcionario;
    private EditText namee, email, senha, criar_conta_cpf, editTextSenha2;
    private FuncionarioController funC;
    private Funcionario_Request funcionario_request;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        context = this;
        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");

        btn_funcionario = findViewById(R.id.buttonFuncionarios);
        btn_check_in = findViewById(R.id.btn_check_in);
        btn_limpeza = findViewById(R.id.btn_limpeza);
        btn_apartamento = findViewById(R.id.btn_apartamento);
        btn_hospedagem = findViewById(R.id.btn_hospedagem);
        btn_reserva = findViewById(R.id.btn_reserva);
        button_menu = findViewById(R.id.painel_btn_menu);
        btn_relatorio = findViewById(R.id.btn_relatorio);
        btn_manutencao = findViewById(R.id.btn_manuntecao);

        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(PainelActivity.this, view);
                popup.setOnMenuItemClickListener(PainelActivity.this);
                popup.inflate(R.menu.menu);
                popup.show();
            }
        });

        btn_funcionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (funcionario.getCargo().equals("Camareira") || funcionario.getCargo().equals("Recepcionista")) {
                    viewToastAlerta(context, "Funcionários não tem acesso a essa opção");
                } else {
                    Intent i = new Intent(PainelActivity.this, FuncionarioActivity.class);
                    i.putExtra("funcionario", funcionario);
                    startActivity(i);
                }

            }
        });
        btn_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, CheckinActivity.class);
                i.putExtra("funcionario", funcionario);
                startActivity(i);
            }
        });

        btn_limpeza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, LimpezaActivity.class);
                i.putExtra("funcionario", funcionario);
                startActivity(i);
            }
        });
        btn_apartamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, ApartamentoActivity.class);
                i.putExtra("funcionario", funcionario);
                startActivity(i);
            }
        });

        btn_hospedagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, HospedagemActivity.class);
                i.putExtra("funcionario", funcionario);
                startActivity(i);
            }
        });

        btn_reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, ReservaActivity.class);
                i.putExtra("funcionario", funcionario);
                startActivity(i);
            }
        });

        btn_relatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, RelatorioActivity.class);
                i.putExtra("funcionario", funcionario);
                startActivity(i);
            }
        });
        btn_manutencao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PainelActivity.this, ManutencaoActivity.class);
                i.putExtra("funcionario", funcionario);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.perfil:


                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.create_account, null);
                final TextView titulo = layout.findViewById(R.id.textView2);
                final TextView titulo2 = layout.findViewById(R.id.textview_criarconta);

                titulo.setText("Alterar seus dados");
                titulo2.setText("Informe seus dados");

                Button button_altera_conta = layout.findViewById(R.id.btnCreate);

                namee = layout.findViewById(R.id.editTextUserName);
                namee.setText(funcionario.getNome());
                email = layout.findViewById(R.id.editTextEmail);
                email.setText(funcionario.getCodidentificacao());
                senha = layout.findViewById(R.id.editTextPassword);
                criar_conta_cpf = layout.findViewById(R.id.criar_conta_cpf);
                criar_conta_cpf.setText(funcionario.getCpf());
                editTextSenha2 = layout.findViewById(R.id.editTextSenha2);

                funC = new FuncionarioController(context);
                funcionario_request = new Funcionario_Request(context);

                button_altera_conta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String json = funC.valirar_alterar_funcionario(funcionario.getId(), funcionario.getAdministrador_id(), namee.getText().toString(), email.getText().toString(), criar_conta_cpf.getText().toString(), funcionario.getCargo(), senha.getText().toString(), editTextSenha2.getText().toString());
                        if (json != null) {
                            funcionario_request.alterar_funcionario(json, funcionario.getId());
                            alerta.cancel();
                        }

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
                .setMessage("Deseja mesmo sair?")
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


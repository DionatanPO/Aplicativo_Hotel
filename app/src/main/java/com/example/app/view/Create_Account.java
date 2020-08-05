package com.example.app.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.controller.FuncionarioController;
import com.example.app.controller.HotelController;
import com.example.app.model.Funcionario;
import com.example.app.model.Hotel;
import com.example.app.request.Funcionario_Request;
import com.example.app.request.Hotel_Request;

public class Create_Account extends AppCompatActivity {
    private Button btn_create_account, btn_ajuda;
    private Context context;
    private EditText namee, email, senha, criar_conta_cpf, editTextSenha2, nome_hotel, telefone, endereco;
    private FuncionarioController funC;
    private Funcionario_Request funcionario_request;
    private Hotel hotel ;
    private String json;
    private   Funcionario funcionario;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        context = this;

        btn_create_account = findViewById(R.id.btnCreate);
        namee = findViewById(R.id.editTextUserName);
        email = findViewById(R.id.editTextEmail);
        senha = findViewById(R.id.editTextPassword);
        criar_conta_cpf = findViewById(R.id.criar_conta_cpf);
        editTextSenha2 = findViewById(R.id.editTextSenha2);
        nome_hotel = findViewById(R.id.editTextNomeHotel);
        telefone = findViewById(R.id.editText_telefone);
        endereco = findViewById(R.id.editTextEndereco_hotel);

        funC = new FuncionarioController(context);
        funcionario_request = new Funcionario_Request(context);

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 json = funC.create_account(
                        namee.getText().toString(), email.getText().toString(), criar_conta_cpf.getText().toString(),
                        senha.getText().toString(), editTextSenha2.getText().toString()
                        , nome_hotel.getText().toString(), telefone.getText().toString(), endereco.getText().toString());
                if (json != null) {
                    Hotel_Request hotel_request = new Hotel_Request(context);
                    HotelController hotelController = new HotelController(context);
                    Hotel hotel = new Hotel();


                    hotel.setNome(nome_hotel.getText().toString());
                    hotel.setTelefone(telefone.getText().toString());
                    hotel.setEndereco(endereco.getText().toString());


                    String jsonHotel = hotelController.converter_hotel_json(hotel);

                    hotel_request.cadastrar_hotel(jsonHotel, Create_Account.this);


                }
            }
        });

        btn_ajuda = findViewById(R.id.btn_ajuda);

        btn_ajuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Ajuda");
                alertDialogBuilder
                        .setMessage("Código de identificação é utilizado para efetuar o login no aplicativo." +
                                " Para uma melhor segurança é recomendo que utilize pelo menos 8 caracteres, " +
                                "podendo ser 1 maiúscula e 1 númererica na formulação da senha. Quanto ao campo com o símbolo de *, é obrigatório o preenchimento.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

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
            }
        });

    }

    public void request(Hotel h) {
        funcionario = new Funcionario();

        funcionario = funC.converter_json_funcionario(json);
        funcionario.setHotel(h);
        String result = funC.converter_funcionario_json(funcionario);

        funcionario_request.cadastrarFuncionario(result);
    }
}

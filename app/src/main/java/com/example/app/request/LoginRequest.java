package com.example.app.request;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.example.app.R;
import com.example.app.model.Funcionario;
import com.example.app.model.Url;
import com.example.app.view.LoginActivity;
import com.example.app.view.PainelActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastAlerta;


public class LoginRequest {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private Url url = new Url();
    private String ip = url.getUrl();

    Funcionario funcionario = new Funcionario();

    public LoginRequest(Context ctx) {
        this.mCtx = ctx;
        mRequestQueue = Volley.newRequestQueue(mCtx);
    }

    public void login(final String json, final Context context) {
        final AlertDialog alerta;
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.crregamento, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(layout);
        alerta = builder.create();
        alerta.show();

        String url = ip + "/funcionario/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    funcionario = gson.fromJson(jsonObject.toString(), Funcionario.class);

                    if (funcionario != null) {
                        Intent i = new Intent(context, PainelActivity.class);
                        i.putExtra("funcionario",funcionario);
                        context.startActivity(i);
                        alerta.cancel();
                        viewToast(context, "Logado com sucesso!");
                    } else {
                        alerta.cancel();
                        viewToastAlerta(context, "Usuário não encontrado ou senha invalida!");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    viewToastAlerta(context, "Usuário não encontrado ou senha invalida!");
                    alerta.cancel();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alerta.cancel();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return json == null ? null : json.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }

            }

        };

        mRequestQueue.add(stringRequest);

    }


}

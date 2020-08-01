package com.example.app.request;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.model.Manutencao;
import com.example.app.model.Url;
import com.example.app.view.LoginActivity;
import com.example.app.view.adapter.ManutencaoAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastErro;


public class Manutencao_Request {
    private RequestQueue mRequestQueue;
    private Context context;
    private List<Manutencao> manutencaoList = new ArrayList<>();
    private Url url = new Url();
    private String ip = url.getUrl();
    private Manutencao manutencao = new Manutencao();
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();

    public Manutencao_Request(Context ctx) {
        this.context = ctx;
        mRequestQueue = Volley.newRequestQueue(context);
    }


    public void cadastrarManutencao(final String json, final ManutencaoAdapter funadp) {

        String url = ip + "/manutencao/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    manutencao = gson.fromJson(jsonObject.toString(), Manutencao.class);
                    funadp.addManutencaoo(manutencao);
                    viewToast(context, "Manutenção cadastrada!");
                } catch (Exception e) {
                    e.printStackTrace();
                    viewToastErro(context, "Ops! Algo deu errado.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                viewToastErro(context, "Ops! Algo deu errado.");
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

    public void cadastrarManutencao(final String json) {

        String url = ip + "/manutencao/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    manutencao = gson.fromJson(jsonObject.toString(), Manutencao.class);
                    viewToast(context, "Manutenção cadastrada!");
                } catch (Exception e) {
                    e.printStackTrace();
                    viewToastErro(context, "Ops! Algo deu errado.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                viewToastErro(context, "Ops! Algo deu errado.");
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

    public List<Manutencao> bsucarTodosAtivos(final ManutencaoAdapter funap, final ProgressBar progressBar, Long id) {

        String url = ip + "/manutencao/todosAtivos/" + id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject manutencaos = jsonArray.getJSONObject(i);

                                progressBar.setVisibility(View.VISIBLE);
                                manutencao = gson.fromJson(manutencaos.toString(), Manutencao.class);
                                manutencaoList.add(manutencao);

                            }

                            funap.setManutencaoslis(manutencaoList, progressBar);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        mRequestQueue.add(request);

        return manutencaoList;
    }

    public Boolean alterar_manutencao(final String json, Long id) {

        String url = ip + "/manutencao/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    manutencao = gson.fromJson(jsonObject.toString(), Manutencao.class);
                    viewToast(context, "Dados alterados!");
                } catch (Exception e) {
                    e.printStackTrace();
                    viewToastErro(context, "Ops! Algo deu errado.");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                viewToastErro(context, "Ops! Algo deu errado.");
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

        return true;

    }
}
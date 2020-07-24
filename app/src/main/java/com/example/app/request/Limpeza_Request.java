package com.example.app.request;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.model.Apartamento;
import com.example.app.model.Limpeza;
import com.example.app.model.Url;
import com.example.app.view.adapter.LimpezaAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Limpeza_Request {
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private List<Apartamento> apList = new ArrayList<>();
    private List<Limpeza> limpezaList = new ArrayList<>();
    private Url url = new Url();
    private String ip = url.getUrl();
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();

    private Apartamento ap;
    private Limpeza limpeza = new Limpeza();

    public Limpeza_Request(Context ctx) {
        this.mCtx = ctx;
        mRequestQueue = Volley.newRequestQueue(mCtx);
    }


    public void cadastrar_limpeza(final String json) {

        String url = ip + "/limpeza/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    limpeza = new Gson().fromJson(jsonObject.toString(), Limpeza.class);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
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


    public void buscar_Apartamentos_Sujos(final LimpezaAdapter lad, final TextView tv, Long id) {


        final String url = ip + "/limpeza/todos/" + id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject produtos = jsonArray.getJSONObject(i);

                                ap = new Gson().fromJson(produtos.toString(), Apartamento.class);
                                apList.add(ap);


                            }
                            lad.setApartamentosList(apList);
                            tv.setText(String.valueOf(apList.size()));
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
    }

    public void buscar_Limpezas_pordata(final LimpezaAdapter lad, final TextView tv, Long id, String data) {

        final String url = ip + "/limpeza/todosData/" + id + "/" + data;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject l = jsonArray.getJSONObject(i);

                                limpeza =  gson.fromJson(l.toString(), Limpeza.class);

                                apList.add(limpeza.getApartamento());

                            }
                            lad.setApartamentosList(apList);
                            tv.setText(String.valueOf(apList.size()));
                            lad.notifyDataSetChanged();
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
    }
}


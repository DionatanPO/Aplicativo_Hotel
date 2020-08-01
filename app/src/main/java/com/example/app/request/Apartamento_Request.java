package com.example.app.request;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.model.Apartamento;
import com.example.app.model.Url;
import com.example.app.view.LoginActivity;
import com.example.app.view.RelatorioActivity;
import com.example.app.view.adapter.ApartamentoAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Apartamento_Request {
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private List<Apartamento> apList = new ArrayList<>();
    private Url url = new Url();
    private String ip = url.getUrl();

    Apartamento ap;

    public Apartamento_Request(Context ctx) {
        this.mCtx = ctx;
        mRequestQueue = Volley.newRequestQueue(mCtx);
    }


    public void cadastrarApartamento(final String json, final ApartamentoAdapter a) {

        ap = new Apartamento();
        String url = ip + "/apartamento/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ap = new Gson().fromJson(jsonObject.toString(), Apartamento.class);
                    a.addApartamento(ap);



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



    public List<Apartamento> bsucarTodos(final RelatorioActivity activity, Long id) {

        String url = ip + "/apartamento/todos/"+id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject apartamentos = jsonArray.getJSONObject(i);

                                ap = new Gson().fromJson(apartamentos.toString(), Apartamento.class);
                                apList.add(ap);

                            }
                            activity.request(apList);
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
        return apList;
    }



    public List<Apartamento> bsucarTodosAtivos(final ProgressBar progressBar, Long id) {

        String url = ip + "/apartamento/todos/"+id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject apartamentos = jsonArray.getJSONObject(i);

                                ap = new Gson().fromJson(apartamentos.toString(), Apartamento.class);
                                apList.add(ap);


                            }
                            progressBar.setVisibility(View.GONE);
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

        return apList;
    }

    public Boolean alterar_Apartamento(final String json, Long id) {

        String url = ip + "/apartamento/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    ap = new Gson().fromJson(jsonObject.toString(), Apartamento.class);
//


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

        return true;

    }

    public void buscar_por_estado(final List<Apartamento> list, final ArrayAdapter ar, Long id ) {

        final String url = ip + "/apartamento/todosDisponivel/"+id;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject produtos = jsonArray.getJSONObject(i);

                                ap = new Gson().fromJson(produtos.toString(), Apartamento.class);
                                apList.add(ap);
                                list.add(ap);

                            }
                            ar.notifyDataSetChanged();
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

    public void buscarDisponiveis(final List<Apartamento> list, final ArrayAdapter ar, Long id ) {

        final String url = ip + "/apartamento/todosDisponivel/"+id;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject produtos = jsonArray.getJSONObject(i);

                                ap = new Gson().fromJson(produtos.toString(), Apartamento.class);
                                apList.add(ap);
                                list.add(ap);

                            }

                            ar.notifyDataSetChanged();
                            if(apList.size()==0){
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mCtx);

                                alertDialogBuilder
                                        .setMessage("Hotel lotado! Nenhum apartamento disponível no momento.")
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
                            }

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
    public void buscarDisponiveisPOrHotel(final List<Apartamento> list, final ArrayAdapter ar, Long id ) {

        final String url = ip + "/apartamento/todosDisponivelPorHotel/"+id;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject produtos = jsonArray.getJSONObject(i);

                                ap = new Gson().fromJson(produtos.toString(), Apartamento.class);
                                apList.add(ap);
                                list.add(ap);

                            }

                            ar.notifyDataSetChanged();
                            if(apList.size()==0){
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mCtx);

                                alertDialogBuilder
                                        .setMessage("Hotel lotado! Nenhum apartamento disponível no momento.")
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
                            }

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





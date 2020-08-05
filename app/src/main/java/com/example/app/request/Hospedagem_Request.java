package com.example.app.request;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.app.model.Hospedagem;
import com.example.app.model.Url;
import com.example.app.view.HospedagemActivity;
import com.example.app.view.RelatorioActivity;
import com.example.app.view.adapter.HospedagemAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.view.CustonToast.viewToastAlerta;


public class Hospedagem_Request {
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private List<Hospedagem> hospedagemList = new ArrayList<>();
    private Url url = new Url();
    private String ip = url.getUrl();
    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();

    Hospedagem hospedagem = new Hospedagem();

    public Hospedagem_Request(Context ctx) {
        this.mCtx = ctx;
        mRequestQueue = Volley.newRequestQueue(mCtx);
    }


    public List<Hospedagem> bsucarTodos() {

        String url = ip + "/hospedagem/todos";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hospedagems = jsonArray.getJSONObject(i);

                                hospedagem = new Gson().fromJson(hospedagems.toString(), Hospedagem.class);
                                hospedagemList.add(hospedagem);

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
        return hospedagemList;
    }


    public List<Hospedagem> bsucarTodosAtivos(final HospedagemAdapter funap, Long gerente_id, final TextView textView) {

        String url = ip + "/hospedagem/todas/" + gerente_id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        try {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hospedagems = jsonArray.getJSONObject(i);

                                hospedagem = new Gson().fromJson(hospedagems.toString(), Hospedagem.class);
                                hospedagemList.add(hospedagem);

                            }
                            if (hospedagemList.size() > 0) {
                                textView.setVisibility(View.GONE);
                            } else {
                                textView.setVisibility(View.VISIBLE);
                            }
                            funap.sethospedagemsList(hospedagemList);

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

        return hospedagemList;
    }


    public List<Hospedagem> bsucarTodosEntreData(final HospedagemAdapter funap, Long id, final TextView textView, Date data1, Date data2, final HospedagemActivity activity) {

        final String url = ip + "/hospedagem/todosData/" + id + "/" + data1 + "/" + data2;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        try {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hospedagems = jsonArray.getJSONObject(i);

                                hospedagem = gson.fromJson(hospedagems.toString(), Hospedagem.class);
                                hospedagemList.add(hospedagem);

                            }
                            if (hospedagemList.size() > 0) {
                                textView.setVisibility(View.GONE);
                            } else {
                                textView.setVisibility(View.VISIBLE);
                            }
                            funap.sethospedagemsList(hospedagemList);
                            activity.request();

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


        return hospedagemList;
    }

    public List<Hospedagem> bsucarTodosAtivos(final RelatorioActivity activity, Long id) {

        String url = ip + "/hospedagem/todas/" + id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hospedagems = jsonArray.getJSONObject(i);

                                hospedagem = new Gson().fromJson(hospedagems.toString(), Hospedagem.class);
                                hospedagemList.add(hospedagem);

                            }

                            activity.request2(hospedagemList);

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

        return hospedagemList;
    }

    public Boolean alterar_hospedagem(final String json, Long id) {

        String url = ip + "/hospedagem/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    hospedagem = new Gson().fromJson(jsonObject.toString(), Hospedagem.class);

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
}
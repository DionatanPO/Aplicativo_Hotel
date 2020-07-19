package com.example.app.request;

import android.content.Context;

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
import com.example.app.view.RelatorioActivity;
import com.example.app.view.adapter.HospedagemAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Hospedagem_Request {
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private List<Hospedagem> hospedagemList = new ArrayList<>();
    private Url url = new Url();
    private String ip = url.getUrl();


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


//    public void delete_id(Long id) {
//
//        String url = ip+"/hospedagem/"+id;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(mCtx, "Apagado", Toast.LENGTH_LONG).show();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(mCtx, "Apagado", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        mRequestQueue.add(stringRequest);
//    }


    public List<Hospedagem> bsucarTodosAtivos(final HospedagemAdapter funap) {

        String url = ip + "/hospedagem/todosAtivos";

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

    public List<Hospedagem> bsucarTodosAtivos(final RelatorioActivity activity) {

        String url = ip + "/hospedagem/todosAtivos";

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
}
package com.example.app.request;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.model.Apartamento;
import com.example.app.model.Hospedagem;
import com.example.app.model.Url;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Checkin_Request {
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private List<Apartamento> apList = new ArrayList<>();
    private Url url = new Url();
    private String ip = url.getUrl();

    Apartamento ap;
    Hospedagem hospedagem = new Hospedagem();

    public Checkin_Request(Context ctx) {
        this.mCtx = ctx;
        mRequestQueue = Volley.newRequestQueue(mCtx);
    }


    public void cadastrar_hospedagem(final String json) {

        String url = ip + "/hospedagem/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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
}


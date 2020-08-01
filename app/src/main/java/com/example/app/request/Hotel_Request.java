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
import com.example.app.model.Hotel;
import com.example.app.model.Url;
import com.example.app.view.Create_Account;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Hotel_Request implements Serializable {
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private List<Apartamento> apList = new ArrayList<>();
    private List<Hotel> hotelList = new ArrayList<>();
    private Url url = new Url();
    private String ip = url.getUrl();
    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();

    private Apartamento ap;
    private Hotel hotel = new Hotel();

    public Hotel_Request(Context ctx) {
        this.mCtx = ctx;
        mRequestQueue = Volley.newRequestQueue(mCtx);
    }


    public void cadastrar_hotel(final String json, final Create_Account activity) {

        String url = ip + "/hotel/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    hotel = new Gson().fromJson(jsonObject.toString(), Hotel.class);
                    activity.request(hotel);
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

//
//    public void buscar_Apartamentos_Sujos(final HotelAdapter lad, final TextView tv, Long id, final TextView msn) {
//
//
//        final String url = ip + "/hotel/todos/" + id;
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray jsonArray) {
//                        try {
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject produtos = jsonArray.getJSONObject(i);
//
//                                ap = new Gson().fromJson(produtos.toString(), Apartamento.class);
//                                apList.add(ap);
//
//
//                            }
//                            if (apList.size() <= 0) {
//                                msn.setVisibility(View.VISIBLE);
//                            } else {
//                                msn.setVisibility(View.GONE);
//                            }
//
//                            lad.setApartamentosList(apList);
//                            tv.setText(String.valueOf(apList.size()));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(error);
//
//            }
//        });
//        mRequestQueue.add(request);
//    }
//
//    public void buscar_Hotels_pordata(final HotelAdapter lad, final TextView tv, Long id, String data, final TextView qtd) {
//
//        final String url = ip + "/hotel/todosData/" + id + "/" + data;
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray jsonArray) {
//                        try {
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject l = jsonArray.getJSONObject(i);
//
//                                hotel = gson.fromJson(l.toString(), Hotel.class);
//                                hotel.getApartamento().setEstado("");
//                                apList.add(hotel.getApartamento());
//
//                            }
//                            qtd.setText(String.valueOf(apList.size()));
//
//                            lad.setApartamentosList(apList);
//                            tv.setText(String.valueOf(apList.size()));
//                            lad.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println(error);
//
//            }
//        });
//        mRequestQueue.add(request);
//    }
}


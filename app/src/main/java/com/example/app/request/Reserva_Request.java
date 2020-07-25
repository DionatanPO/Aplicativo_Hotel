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
import com.example.app.model.Apartamento;
import com.example.app.model.Reserva;
import com.example.app.model.Url;
import com.example.app.view.adapter.ReservaAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.view.CustonToast.viewToast;

public class Reserva_Request {
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private List<Apartamento> apList = new ArrayList<>();
    private List<Reserva> reservaList = new ArrayList<>();
    private Url url = new Url();
    private String ip = url.getUrl();
    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();
    private Apartamento ap;
    private Reserva reserva = new Reserva();

    public Reserva_Request(Context ctx) {
        this.mCtx = ctx;
        mRequestQueue = Volley.newRequestQueue(mCtx);
    }


    public void cadastrar_reserva(final String json) {

        String url = ip + "/reserva/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    reserva = gson.fromJson(jsonObject.toString(), Reserva.class);
                    viewToast(mCtx, "Reserva cadastrada!");
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


    public List<Reserva> bsucarTodos() {

        String url = ip + "/reserva/todos";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject reservas = jsonArray.getJSONObject(i);

                                reserva = new Gson().fromJson(reservas.toString(), Reserva.class);
                                reservaList.add(reserva);

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
        return reservaList;
    }




    public List<Reserva> bsucarTodosAtivos(final ReservaAdapter funap, Long id) {

        String url = ip + "/reserva/todosAtivos/"+id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject reservas = jsonArray.getJSONObject(i);

                                reserva = gson.fromJson(reservas.toString(), Reserva.class);
                                reservaList.add(reserva);

                            }

                            funap.setreservasList(reservaList);

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

        return reservaList;
    }

    public Boolean alterar_reserva(final String json, Long id) {

        String url = ip + "/reserva/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    reserva = new Gson().fromJson(jsonObject.toString(), Reserva.class);
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
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
import com.example.app.model.Funcionario;
import com.example.app.model.Url;
import com.example.app.view.Create_Account;
import com.example.app.view.LoginActivity;
import com.example.app.view.adapter.FuncionarioAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.example.app.view.CustonToast.viewToast;
import static com.example.app.view.CustonToast.viewToastErro;


public class Funcionario_Request {
    private RequestQueue mRequestQueue;
    private Context context;
    private List<Funcionario> fumList = new ArrayList<>();
    private Url url = new Url();
    private String ip = url.getUrl();

    Funcionario fum = new Funcionario();

    public Funcionario_Request(Context ctx) {
        this.context = ctx;
        mRequestQueue = Volley.newRequestQueue(context);
    }


    public void cadastrarFuncionario(final String json, final FuncionarioAdapter funadp) {


        String url = ip + "/funcionario/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    fum = new Gson().fromJson(jsonObject.toString(), Funcionario.class);
                    funadp.addFuncionarioo(fum);
                    viewToast(context, "Funcionário cadastrado");
                } catch (Exception e) {
                    e.printStackTrace();
                    viewToastErro(context, "Ops! Algo deu errado");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                viewToastErro(context, "Ops! Algo deu errado");
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

    public void cadastrarFuncionario(final String json) {

        String url = ip + "/funcionario/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    fum = new Gson().fromJson(jsonObject.toString(), Funcionario.class);
                    viewToast(context, "Conta criada com sucesso");
                    Intent i = new Intent(context, LoginActivity.class);
                    context.startActivity(i);

                } catch (Exception e) {
                    e.printStackTrace();
                    viewToastErro(context, "Ops! Algo Não deu certo, tente novament");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                viewToastErro(context, "Ops Algo Não deu certo, tente novament");
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

    public List<Funcionario> bsucarTodos() {

        String url = ip + "/funcionario/todos";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject funcionarios = jsonArray.getJSONObject(i);

                                fum = new Gson().fromJson(funcionarios.toString(), Funcionario.class);
                                fumList.add(fum);

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
        return fumList;
    }


//    public void delete_id(Long id) {
//
//        String url = ip+"/funcionario/"+id;
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

    public void alterrar_estado_funcionario(final String json, Long id) {

        String url = ip + "/funcionario/" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    fum = new Gson().fromJson(jsonObject.toString(), Funcionario.class);


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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

    public List<Funcionario> bsucarTodosAtivos(final FuncionarioAdapter funap, final ProgressBar progressBar, Long id) {

        String url = ip + "/funcionario/todosAtivos/"+id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject funcionarios = jsonArray.getJSONObject(i);

                                progressBar.setVisibility(View.VISIBLE);
                                fum = new Gson().fromJson(funcionarios.toString(), Funcionario.class);
                                fumList.add(fum);

                            }

                            funap.setFuncionarioslis(fumList, progressBar);

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

        return fumList;
    }

    public Boolean alterar_funcionario(final String json, Long id) {

        String url = ip + "/funcionario/" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    fum = new Gson().fromJson(jsonObject.toString(), Funcionario.class);
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
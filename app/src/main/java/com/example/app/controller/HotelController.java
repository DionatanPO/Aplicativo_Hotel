package com.example.app.controller;

import android.content.Context;

import com.example.app.model.Apartamento;
import com.example.app.model.Funcionario;
import com.example.app.model.Hospede;
import com.example.app.model.Hotel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class HotelController {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd").create();

    Context ctx;
    Funcionario funcionario = new Funcionario();
    Hospede hospede = new Hospede();
    Hotel hotel = new Hotel();
    String json;

    public HotelController(Context ctx) {
        this.ctx = ctx;
    }


    public String converter_hotel_json(Hotel hotel) {
        String json = gson.toJson(hotel);
        return json;
    }

    public Hotel converter_json_hotel(String json) {
        hotel =  gson.fromJson(json, Hotel.class);
        return hotel;
    }
}

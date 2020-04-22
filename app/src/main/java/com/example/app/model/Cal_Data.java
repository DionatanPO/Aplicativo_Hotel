package com.example.app.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Cal_Data {
    List<String> horasList = new ArrayList<>();

    String date_entrada;
    String date_saida;
    Date dataHoraAtual = new Date();

    public List<String> cal_data_entrada_saida() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();

        String data = new SimpleDateFormat("dd-MM-yyyy").format(dataHoraAtual);

        String horacompleta = new SimpleDateFormat("HH:mm").format(dataHoraAtual);

        String[] horamin = horacompleta.split(":");

        int hora = Integer.parseInt(horamin[0]);


        if (hora < 12) {
            c.add(Calendar.DAY_OF_MONTH, -1);
            date_entrada = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
            horasList.add(0, date_entrada);


            c.add(Calendar.DAY_OF_MONTH, 1);
            date_saida = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
            horasList.add(1, date_saida);

        }
        if (hora >= 12) {

            c.setTime(date);

            date_entrada = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
            horasList.add(0, date_entrada);

            c.add(Calendar.DAY_OF_MONTH, 1);
            date_saida = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
            horasList.add(1, date_saida);



        }

        return horasList;
    }
}

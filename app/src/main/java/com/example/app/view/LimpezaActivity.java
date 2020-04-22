package com.example.app.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.FuncionarioController;
import com.example.app.controller.LimpezaController;
import com.example.app.model.Apartamento;
import com.example.app.request.Apartamento_Request;

import java.util.ArrayList;
import java.util.List;


public class LimpezaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LimpezaAdapter limpezaAdapter;
    private Apartamento_Request apr;
    TextView quantidade_ap_sujo;
    LimpezaController limpezaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limpeza);

        quantidade_ap_sujo = findViewById(R.id.txt_quantidade_sujo);

        FuncionarioController fc = new FuncionarioController();

        recyclerView = (RecyclerView) findViewById(R.id.limpeza_recycler_view);

        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        apr = new Apartamento_Request(this);
        List<Apartamento> apartamentoList = new ArrayList<>();

        limpezaAdapter = new LimpezaAdapter(this, apartamentoList);

        apr.buscar_por_estado("Sujo", "estado", limpezaAdapter, quantidade_ap_sujo);
        recyclerView.setAdapter(limpezaAdapter);

        limpezaController = new LimpezaController(this);



    }

}


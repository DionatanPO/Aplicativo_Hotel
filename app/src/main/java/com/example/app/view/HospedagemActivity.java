package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.CheckinController;
import com.example.app.model.Hospedagem;
import com.example.app.request.Hospedagem_Request;
import com.example.app.view.adapter.HospedagemAdapter;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class HospedagemActivity extends Activity {
    private HospedagemAdapter hospedagemAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    CheckinController checkinController;
    Hospedagem_Request hospedagem_request;
    Button btn_add;
    String json;
    Spinner spinner;
    ArrayAdapter spinnerAdapter;
    View view;
    String cargo;
    String cpf;
    private AlertDialog alerta;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    private List<Hospedagem> fumList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospedagem);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        hospedagem_request = new Hospedagem_Request(this);

        hospedagemAdapter = new HospedagemAdapter(this, fumList);

        hospedagem_request.bsucarTodosAtivos(hospedagemAdapter);


        btn_add = findViewById(R.id.fab_add);


        recyclerView = (RecyclerView) findViewById(R.id.hospedagem_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(hospedagemAdapter);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HospedagemActivity.this, CheckinActivity.class);
                startActivity(i);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new HospedagemActivity.ItemTouchHandler(0,
                        ItemTouchHelper.LEFT)
        );
        helper.attachToRecyclerView(recyclerView);

    }


    public class ItemTouchHandler extends ItemTouchHelper.SimpleCallback {

        public ItemTouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("WrongConstant")
        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            checkinController = new CheckinController(HospedagemActivity.this);
            hospedagemAdapter.getHospedagemsList().get(position).setEstado("Desabilitado");

            json = checkinController.converter_hospedagem_json(hospedagemAdapter.getHospedagemsList().get(position));

            hospedagem_request.alterar_hospedagem(json, hospedagemAdapter.getHospedagemsList().get(position).getId());


            hospedagemAdapter.getHospedagemsList().remove(position);
            hospedagemAdapter.notifyItemRemoved(position);

            LayoutInflater inflater2 = LayoutInflater.from(HospedagemActivity.this);
            View layout2 = inflater2.inflate(R.layout.custom_toast, null);

            TextView text = (TextView) layout2.findViewById(R.id.text);
            text.setText("Hospedagem apagada!");

            Toast toast = new Toast(HospedagemActivity.this);
            toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
            toast.setDuration(8000);
            toast.setView(layout2);
            toast.show();


        }

        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(HospedagemActivity.this, R.color.red))
                    .addActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    }

}



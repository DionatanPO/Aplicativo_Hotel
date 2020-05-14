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
import com.example.app.controller.ReservaController;
import com.example.app.model.Reserva;
import com.example.app.request.Reserva_Request;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class ReservaActivity extends Activity {
    private ReservaAdapter reservaAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ReservaController reservaController;
    Reserva_Request reserva_request;
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
    private List<Reserva> reservaList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        reserva_request = new Reserva_Request(this);

        reservaAdapter = new ReservaAdapter(this, reservaList);

        reserva_request.bsucarTodosAtivos(reservaAdapter);


        btn_add = findViewById(R.id.fab_add);


        recyclerView = (RecyclerView) findViewById(R.id.reserva_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(reservaAdapter);


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReservaActivity.this, ReservaAddActivity.class);
                startActivity(i);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ReservaActivity.ItemTouchHandler(0,
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

            reservaController = new ReservaController(ReservaActivity.this);
            reservaAdapter.getReservasList().get(position).setEstado("Desabilitado");

            json = reservaController.converter_reserva_json(reservaAdapter.getReservasList().get(position));

            reserva_request.alterar_reserva(json, reservaAdapter.getReservasList().get(position).getId());


            reservaAdapter.getReservasList().remove(position);
            reservaAdapter.notifyItemRemoved(position);

            LayoutInflater inflater2 = LayoutInflater.from(ReservaActivity.this);
            View layout2 = inflater2.inflate(R.layout.custom_toast, null);

            TextView text = (TextView) layout2.findViewById(R.id.text);
            text.setText("Reserva apagada!");

            Toast toast = new Toast(ReservaActivity.this);
            toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
            toast.setDuration(8000);
            toast.setView(layout2);
            toast.show();


        }

        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(ReservaActivity.this, R.color.red))
                    .addActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    }

}



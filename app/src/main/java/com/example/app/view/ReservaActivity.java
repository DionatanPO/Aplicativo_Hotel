package com.example.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.controller.ReservaController;
import com.example.app.model.Funcionario;
import com.example.app.model.Reserva;
import com.example.app.request.Reserva_Request;
import com.example.app.view.adapter.ReservaAdapter;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static com.example.app.view.CustonToast.viewToast;


public class ReservaActivity extends Activity {
    private ReservaAdapter reservaAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ReservaController reservaController;
    private Reserva_Request reserva_request;
    private String json;
    private Button btn_add;
    private ProgressBar progressBar;
    private List<Reserva> reservaList = new ArrayList<>();
    private Funcionario funcionario;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        funcionario = (Funcionario) getIntent().getSerializableExtra("funcionario");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        context = this;

        reserva_request = new Reserva_Request(this);

        reservaAdapter = new ReservaAdapter(this, reservaList, funcionario);

        reserva_request.bsucarTodosAtivos(reservaAdapter, funcionario.getAdministrador_id());

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
                i.putExtra("funcionario", funcionario);
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

            viewToast(context, "Reserva apagada!");


        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(ReservaActivity.this, R.color.red))
                    .addActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    }

    public void add_adpter(Reserva reserva) {
        reservaAdapter.getReservasList().add(reserva);
        reservaAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        reservaAdapter.notifyDataSetChanged();

    }


}



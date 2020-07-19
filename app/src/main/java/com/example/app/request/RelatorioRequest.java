package com.example.app.request;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import android.view.LayoutInflater;
import android.view.View;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;


import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;

import com.example.app.R;
import com.example.app.model.Url;


public class RelatorioRequest {
    private RequestQueue mRequestQueue;
    private Context mCtx;
    private Url url = new Url();
    private String ip = url.getUrl();
    private AlertDialog alerta;

    public RelatorioRequest(Context ctx) {
        this.mCtx = ctx;
        mRequestQueue = Volley.newRequestQueue(mCtx);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void export(Long id) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View layout = inflater.inflate(R.layout.crregamento, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setView(layout);
        alerta = builder.create();
        alerta.show();

        String mUrl = ip + "/exporter/download/" + 1;
        if (ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mCtx, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mCtx, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            DownloadManager downloadManager = (DownloadManager) mCtx.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(mUrl);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Dados exportados.xlsx");
            downloadManager.enqueue(request);
            alerta.cancel();
        }


    }


}

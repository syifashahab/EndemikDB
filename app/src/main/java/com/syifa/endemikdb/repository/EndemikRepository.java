package com.syifa.endemikdb.repository;

import android.content.Context;

import com.syifa.endemikdb.api.ApiClient;
import com.syifa.endemikdb.api.ApiService;
import com.syifa.endemikdb.database.EndemikDatabase;
import com.syifa.endemikdb.entities.Endemik;
import com.syifa.endemikdb.models.EndemikResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndemikRepository {

    private final EndemikDatabase database;
    private final ApiService apiService;

    public interface SyncListener {
        void onSuccess();
        void onFailed(String message);
    }

    public EndemikRepository(Context context) {
        database = EndemikDatabase.getInstance(context);

        apiService = ApiClient
                .getClient()
                .create(ApiService.class);
    }

    public void syncData(SyncListener listener) {

        if (database.endemikDao().countData() > 0) {
            listener.onSuccess();
            return;
        }

        apiService.getEndemik().enqueue(new Callback<List<EndemikResponse>>() {

            @Override
            public void onResponse(Call<List<EndemikResponse>> call,
                                   Response<List<EndemikResponse>> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    listener.onFailed("Response kosong");
                    return;
                }

                new Thread(() -> {

                    List<Endemik> list = new ArrayList<>();

                    for (EndemikResponse item : response.body()) {

                        Endemik e = new Endemik();

                        e.setId(item.getId());
                        e.setTipe(item.getTipe());
                        e.setNama(item.getNama());
                        e.setNama_latin(item.getNama_latin());
                        e.setFamili(item.getFamili());
                        e.setGenus(item.getGenus());
                        e.setDeskripsi(item.getDeskripsi());
                        e.setAsal(item.getAsal());
                        e.setSebaran(item.getSebaran());
                        e.setFoto(item.getFoto());
                        e.setSumber_foto(item.getSumber_foto());
                        e.setVidio(item.getVidio());
                        e.setSumber_vidio(item.getSumber_vidio());
                        e.setStatus(item.getStatus());

                        list.add(e);
                    }

                    database.endemikDao().insertAll(list);

                    android.os.Handler handler =
                            new android.os.Handler(android.os.Looper.getMainLooper());

                    handler.post(listener::onSuccess);

                }).start();

            }

            @Override
            public void onFailure(Call<List<EndemikResponse>> call,
                                  Throwable t) {

                android.os.Handler handler =
                        new android.os.Handler(android.os.Looper.getMainLooper());

                handler.post(() -> listener.onFailed(t.getMessage()));

            }
        });
    }
}
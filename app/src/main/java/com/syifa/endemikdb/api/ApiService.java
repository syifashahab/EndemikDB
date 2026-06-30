package com.syifa.endemikdb.api;

import com.syifa.endemikdb.models.EndemikResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("endemik.json")
    Call<List<EndemikResponse>> getEndemik();
}

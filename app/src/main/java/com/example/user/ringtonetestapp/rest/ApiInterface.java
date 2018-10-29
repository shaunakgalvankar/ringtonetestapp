package com.example.user.ringtonetestapp.rest;

import com.example.user.ringtonetestapp.BBRing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("v1/bb_ki_vines/post_list?")
    Call<List<BBRing>> getceleb(@Query( "category_id" )String category_id);
}

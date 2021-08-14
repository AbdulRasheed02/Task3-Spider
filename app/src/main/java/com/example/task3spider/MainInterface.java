package com.example.task3spider;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainInterface {

    @GET("all.json")
    Call<ArrayList<SuperHero>> getSuperHero();

    @GET("id/{HEROID}.json")
    Call<SuperHero> getSuperHeroById(@Path("HEROID") String id);

}

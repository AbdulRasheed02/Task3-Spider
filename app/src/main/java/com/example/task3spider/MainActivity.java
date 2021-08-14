package com.example.task3spider;

import static android.nfc.NfcAdapter.EXTRA_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {
    private static final String TAG = "ALL Heroes";
    public static final String EXTRA_ID = "SuperHeroId";
    public static final String EXTRA_IMAGEURL = "ImageUrl";
    public static final String EXTRA_SUPERHERONAME = "SuperHeronAME";

    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;
    private ArrayList<SuperHero> mSuperHeroes;
    MainInterface mainInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setTitle("ALL SUPERHEROES");

        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSuperHeroes=new ArrayList<>();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://akabab.github.io/superhero-api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mainInterface=retrofit.create(MainInterface.class);

        if(CheckNetwork.isInternetAvailable(MainActivity.this))
        {
            getSuperHeroes();
        }
        else
        {
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }

    }

    private void getSuperHeroes(){

        Call<ArrayList<SuperHero>> call= mainInterface.getSuperHero();

        call.enqueue(new Callback<ArrayList<SuperHero>>() {
            @Override
            public void onResponse(Call<ArrayList<SuperHero>> call, Response<ArrayList<SuperHero>> response) {
                if(response.isSuccessful()){
                    mSuperHeroes=response.body();
                    Log.d(TAG, "onResponse:"+"All Heroes Success");
                }
                mMainAdapter = new MainAdapter(MainActivity.this, mSuperHeroes);
                mRecyclerView.setAdapter(mMainAdapter);
                mMainAdapter.setOnItemClickListener(MainActivity.this);
            }

            @Override
            public void onFailure(Call<ArrayList<SuperHero>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent=new Intent(this,DetailActivity.class);
        SuperHero clickedItem=mSuperHeroes.get(position);

        detailIntent.putExtra(EXTRA_ID, String.valueOf(clickedItem.getId()));
        detailIntent.putExtra(EXTRA_IMAGEURL, clickedItem.getImages().getLg());
        detailIntent.putExtra(EXTRA_SUPERHERONAME, clickedItem.getName());

        startActivity(detailIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}
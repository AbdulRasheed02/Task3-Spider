package com.example.task3spider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
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
    public static final String EXTRA_SUPERHERO_IMAGE_TRANSITION_NAME = "SuperHeroImageTransition";

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

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

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
    public void onItemClick(int position, ImageView sharedImageView) {
        Intent detailIntent=new Intent(this,DetailActivity.class);
        SuperHero clickedItem=mSuperHeroes.get(position);

        detailIntent.putExtra(EXTRA_ID, String.valueOf(clickedItem.getId()));
        detailIntent.putExtra(EXTRA_IMAGEURL, clickedItem.getImages().getLg());
        detailIntent.putExtra(EXTRA_SUPERHERONAME, clickedItem.getName());
        detailIntent.putExtra(EXTRA_SUPERHERO_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImageView,
                ViewCompat.getTransitionName(sharedImageView));

        startActivity(detailIntent, options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.superhero_menu,menu);

        MenuItem searchItem=menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView=(androidx.appcompat.widget.SearchView)searchItem.getActionView();
        searchView.setQueryHint("Search SuperHero by Name/Id");

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mMainAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}
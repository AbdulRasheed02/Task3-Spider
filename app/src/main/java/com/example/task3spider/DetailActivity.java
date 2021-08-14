package com.example.task3spider;

import static com.example.task3spider.MainActivity.EXTRA_ID;
import static com.example.task3spider.MainActivity.EXTRA_IMAGEURL;
import static com.example.task3spider.MainActivity.EXTRA_SUPERHERONAME;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG2 ="SuperHeroByID";
    ImageView iv_SuperHeroDetail;
    TextView tv_NameDetail, tv_PowerStats, tv_Appearance, tv_Biography,tv_Work, tv_Connections,tv_PowerStatsContent, tv_AppearanceContent, tv_BiographyContent,tv_WorkContent, tv_ConnectionsContent;
    String superHeroId,superHeroImageUrl,superHeroName;

    SuperHero superHero;
    MainInterface mainInterface;
    Retrofit retrofit2;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        iv_SuperHeroDetail=findViewById(R.id.iv_SuperHeroDetail);
        tv_NameDetail=findViewById(R.id.tv_NameDetail);
        tv_PowerStats=findViewById(R.id.tv_PowerStats);
        tv_Appearance=findViewById(R.id.tv_Appearance);
        tv_Biography=findViewById(R.id.tv_Biography);
        tv_Work=findViewById(R.id.tv_Work);
        tv_Connections=findViewById(R.id.tv_Connections);

        tv_PowerStatsContent=findViewById(R.id.tv_PowerStatsContent);
        tv_AppearanceContent=findViewById(R.id.tv_ApperanceContent);
        tv_BiographyContent=findViewById(R.id.tv_BiographyContent);
        tv_WorkContent=findViewById(R.id.tv_WorkContent);
        tv_ConnectionsContent=findViewById(R.id.tv_ConnectionsContent);

        tv_PowerStatsContent.setVisibility(View.INVISIBLE);
        tv_AppearanceContent.setVisibility(View.INVISIBLE);
        tv_BiographyContent.setVisibility(View.INVISIBLE);
        tv_WorkContent.setVisibility(View.INVISIBLE);
        tv_ConnectionsContent.setVisibility(View.INVISIBLE);

        superHeroId=intent.getStringExtra(EXTRA_ID);

        superHeroImageUrl=intent.getStringExtra(EXTRA_IMAGEURL);
        Picasso.with(this).load(superHeroImageUrl).fit().centerInside().into(iv_SuperHeroDetail);

        superHeroName=intent.getStringExtra(EXTRA_SUPERHERONAME);
        tv_NameDetail.setText(superHeroName);

        tv_PowerStats.setText("POWER STATS");
        tv_Appearance.setText("APPEARANCE");
        tv_Biography.setText("BIOGRAPHY");
        tv_Work.setText("WORK");
        tv_Connections.setText("CONNECTIONS");

        retrofit2=new Retrofit.Builder()
                .baseUrl("https://akabab.github.io/superhero-api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mainInterface=retrofit2.create(MainInterface.class);

        if(CheckNetwork.isInternetAvailable(DetailActivity.this))
        {
            getSuperHeroesByID();
        }
        else
        {
            Toast.makeText(DetailActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }

        this.setTitle(superHeroName);
    }

    private void getSuperHeroesByID() {

        Call<SuperHero> call= mainInterface.getSuperHeroById(superHeroId);

        call.enqueue(new Callback<SuperHero>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<SuperHero> call, Response<SuperHero> response) {
                if(response.isSuccessful()){
                    superHero=response.body();
                    Log.d(TAG2, "onResponse:"+"Super Hero by ID Success");
                    setViews();
                }
            }

            @Override
            public void onFailure(Call<SuperHero> call, Throwable t) {
                Log.d(TAG2, "onFailure: "+t.getMessage());
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setViews() {

        String powerStatContent="";
        powerStatContent+="Intelligence : " + superHero.getPowerstats().getIntelligence()+"\n";
        powerStatContent+="Strength : " + superHero.getPowerstats().getStrength()+"\n";
        powerStatContent+="Durability : "+ superHero.getPowerstats().getPower()+"\n";
        powerStatContent+="Power : "+ superHero.getPowerstats().getPower()+"\n";
        powerStatContent+="Combat : "+ superHero.getPowerstats().getCombat();

        tv_PowerStatsContent.setText(powerStatContent);

        String appearanceContent="";
        appearanceContent+="Gender : "+superHero.getAppearance().getGender()+"\n";
        appearanceContent+="Race : "+superHero.getAppearance().getRace()+"\n";
        appearanceContent+="Height : "+superHero.getAppearance().getHeight().get(0)+" / " +superHero.getAppearance().getHeight().get(1) +"\n";
        appearanceContent+="Weight : "+superHero.getAppearance().getWeight().get(0)+" / " +superHero.getAppearance().getWeight().get(1) +"\n";
        appearanceContent+="Eye Colour : "+superHero.getAppearance().getEyeColor()+"\n";
        appearanceContent+="Hair Colour : "+superHero.getAppearance().getHairColor();

        tv_AppearanceContent.setText(appearanceContent);

        String biographyContent="";
        biographyContent+="Full Name : "+superHero.getBiography().getFullName()+"\n";
        biographyContent+="Alter Egos : "+superHero.getBiography().getAlterEgos();

        biographyContent += "\n"+"Aliases : ";
        for(int i=0;i<superHero.getBiography().getAliases().size();i++) {
             biographyContent+=superHero.getBiography().getAliases().get(0) + ",";
        }

        biographyContent+="\n"+"Place of Birth : "+superHero.getBiography().getPlaceOfBirth()+"\n";
        biographyContent+="First Appearance : "+superHero.getBiography().getFirstAppearance()+"\n";
        biographyContent+="Publisher : "+superHero.getBiography().getPublisher()+"\n";
        biographyContent+="Alignment : "+superHero.getBiography().getAlignment();

        tv_BiographyContent.setText(biographyContent);

        String workContent="";
        workContent+="Occupation : "+superHero.getWork().getOccupation()+"\n";
        workContent+="Base : "+superHero.getWork().getBase();

        tv_WorkContent.setText(workContent);

        String connectionContent="";
        connectionContent+="Group Affiliations : "+superHero.getConnections().getGroupAffiliation()+"\n";
        connectionContent+="Relatives : "+superHero.getConnections().getRelatives();

        tv_ConnectionsContent.setText(connectionContent);

        tv_PowerStatsContent.setVisibility(View.VISIBLE);
        tv_AppearanceContent.setVisibility(View.VISIBLE);
        tv_BiographyContent.setVisibility(View.VISIBLE);
        tv_WorkContent.setVisibility(View.VISIBLE);
        tv_ConnectionsContent.setVisibility(View.VISIBLE);
    }
}


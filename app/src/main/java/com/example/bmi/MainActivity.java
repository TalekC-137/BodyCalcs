package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
ImageView bmi,bmr,graf, repmax;
TextView tv_bmi, tv_dateBmi, tv_bmr,tv_DateBmr, tv_needed, tv_latest;
AdView Ad_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bmi = findViewById(R.id.imageView_bmi);
        bmr = findViewById(R.id.imageView_bmr);
        graf = findViewById(R.id.imageView_graf);
        repmax = findViewById(R.id.repmax);
        tv_bmi = findViewById(R.id.tvbmi);
        tv_bmr = findViewById(R.id.tv_bmr);
        tv_dateBmi =findViewById(R.id.tv_DateBmi);
        tv_DateBmr = findViewById(R.id.tv_DateBmr);
        tv_needed = findViewById(R.id.tv_needed);
        tv_latest = findViewById(R.id.tv_latest);
        Ad_banner = findViewById(R.id.ad_banner);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        Ad_banner.loadAd(adRequest);


            DataBaseHelperBmi dataBaseHelperBmi = new DataBaseHelperBmi(MainActivity.this, 1);
            DataBaseHelperBmr dataBaseHelperBmr = new DataBaseHelperBmr(MainActivity.this, 1);

            if(dataBaseHelperBmi.getBmiCount()!=0){

               Long LiczbaBmi  = dataBaseHelperBmi.getBmiCount();
                   BmiModel NewestRecordBmi = dataBaseHelperBmi.getOne(LiczbaBmi);
                   String NewestBmi = NewestRecordBmi.getBmi();
                 String NewestBmiDate = NewestRecordBmi.getData();
                tv_bmi.setText(NewestBmi);
                    tv_dateBmi.setText(NewestBmiDate);
           } else if(dataBaseHelperBmi.getBmiCount()==0 && dataBaseHelperBmr.getBmrCount() ==0) {
                    tv_bmi.setText("your results will appear here once you save one");

           }

            if(dataBaseHelperBmr.getBmrCount() != 0){
                Long LiczbaBmr  = dataBaseHelperBmr.getBmrCount();
                BmrModel NewestRecordBmr = dataBaseHelperBmr.getOne(LiczbaBmr);
                String NewestBmr = NewestRecordBmr.getBmr();
                String NewestNeeded = NewestRecordBmr.getNeeded();
                String NewestBmrDate = NewestRecordBmr.getData();
                tv_bmr.setText(NewestBmr);
               tv_DateBmr.setText(NewestBmrDate);
                tv_needed.setText(NewestNeeded);

            }


       //     Long LiczbaBmi  = dataBaseHelperBmi.getBmiCount();
       //    BmiModel NewestRecordBmi = dataBaseHelperBmi.getOne(LiczbaBmi);
      //    String NewestBmi = NewestRecordBmi.getBmi();
          //  String NewestBmiDate = NewestRecordBmi.getData();
       //  tv_bmi.setText(NewestBmi);
      //     tv_dateBmi.setText(NewestBmiDate);





        bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, bmi.class);
                startActivity(i);
            }
        });

        repmax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OneRep.class);
                startActivity(i);
            }
        });

        bmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, bmr.class);
                startActivity(i);
            }
        });


    graf.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent(MainActivity.this, testdb.class);
            startActivity(i);

        }
    });

    }
    @Override
    public void onResume()
    {
        super.onResume();

        DataBaseHelperBmi dataBaseHelperBmi = new DataBaseHelperBmi(MainActivity.this, 1);
        DataBaseHelperBmr dataBaseHelperBmr = new DataBaseHelperBmr(MainActivity.this, 1);
        if(dataBaseHelperBmi.getBmiCount()!=0){

            Long LiczbaBmi  = dataBaseHelperBmi.getBmiCount();
            BmiModel NewestRecordBmi = dataBaseHelperBmi.getOne(LiczbaBmi);
            String NewestBmi = NewestRecordBmi.getBmi();
            String NewestBmiDate = NewestRecordBmi.getData();
            tv_bmi.setText(NewestBmi);
            tv_dateBmi.setText(NewestBmiDate);
        }

        if(dataBaseHelperBmr.getBmrCount() != 0){
            Long LiczbaBmr  = dataBaseHelperBmr.getBmrCount();
            BmrModel NewestRecordBmr = dataBaseHelperBmr.getOne(LiczbaBmr);
            String NewestBmr = NewestRecordBmr.getBmr();
            String NewestNeeded = NewestRecordBmr.getNeeded();
            String NewestBmrDate = NewestRecordBmr.getData();
            tv_bmr.setText(NewestBmr);
            tv_DateBmr.setText(NewestBmrDate);
           tv_needed.setText(NewestNeeded);
        }
    }


}
package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OneShotPreDrawListener;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OneRep extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Switch sw_Imperial;
    EditText ciezar;
    EditText reps;
    TextView rmx;
    Button btn_calculate;
    FrameLayout kalkulator;
    AdView ad_banner_rep;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_rep);
        ciezar = findViewById(R.id.ciezar);
        reps = findViewById(R.id.reps);
        rmx = findViewById(R.id.rpm);
        btn_calculate = findViewById(R.id.calculate);
        kalkulator = findViewById(R.id.kalkulator);
        sw_Imperial = findViewById(R.id.switch_Imperial);
        ad_banner_rep = findViewById(R.id.ad_banner_rep);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        ad_banner_rep.loadAd(adRequest);


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Exercises,R.layout.white);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        final SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        sw_Imperial.setChecked(sharedPreferences.getBoolean("value",true));


        if (sw_Imperial.isChecked()){
            sw_Imperial.setChecked(true);
            sw_Imperial.setText("Switch to Metric   ");
            ciezar.setHint("weight in lbs");

        }else{
            ciezar.setHint("weight in kg");
        }

        sw_Imperial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //jesli jest w imperialnym
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    sw_Imperial.setChecked(true);
                    sw_Imperial.setText("Switch to Metric   ");
                    ciezar.setHint("weight in lbs");



                }else{
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    sw_Imperial.setChecked(false);
                    sw_Imperial.setText("Switch to Imperial");
                    ciezar.setHint("weight in kg");

                }
            }
        });







        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              try {
                if(!sw_Imperial.isChecked()) {
                    if(reps.getText().toString().equals("1")){
                        rmx.setText("Your 1 rep max: " + ciezar.getText().toString() + "kg");
                    }else {
                        Double rep_max;
                        Double ciezar_Value = Double.parseDouble(ciezar.getText().toString());
                        Double reps_Value = Double.parseDouble(reps.getText().toString());
                        rep_max = ciezar_Value * reps_Value * 0.0333 + ciezar_Value;
                        Double rep_maxs = new BigDecimal(rep_max).setScale(1, RoundingMode.HALF_UP).doubleValue();
                        rmx.setText("Your 1 rep max: " + rep_maxs.toString() + "kg");
                    }
                }else{
                    if(reps.getText().toString().equals("1")){
                        rmx.setText("Your 1 rep max: " + ciezar.getText().toString() + "lbs");
                    }else {

                        Double rep_max;
                        Double ciezar_Value = Double.parseDouble(ciezar.getText().toString());
                        Double ciezar_lbs = ciezar_Value * 0.4535;
                        Double reps_Value = Double.parseDouble(reps.getText().toString());
                        rep_max = (ciezar_lbs * reps_Value * 0.0333 + ciezar_lbs) * 2.2046;
                        Double rep_maxs = new BigDecimal(rep_max).setScale(1, RoundingMode.HALF_UP).doubleValue();
                        rmx.setText("Your 1 rep max: " + rep_maxs.toString() + "lbs");
                    }
                }



                 }catch (Exception e){
                  Toast.makeText(OneRep.this, "Fill all of the measurements", Toast.LENGTH_SHORT).show();
                        }
            }
        });


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();


        if(text.equals("dead lift")){
            Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
            kalkulator.setVisibility(View.GONE);

        }else if(text.equals("bench press")){
            Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
            kalkulator.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }


}
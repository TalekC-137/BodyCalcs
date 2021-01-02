package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.BufferUnderflowException;
import java.util.Calendar;


public class bmi extends AppCompatActivity {
    EditText weight, height,height_ft, height_inch;
    Calendar c;
    TextView BMI_Value, test;
    Button btn_bmi_show,btn_bmi_save;
    Switch ToImperial;
    String Date;
    AdView ad_banner_bmi;
    ImageView szczala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        BMI_Value = findViewById(R.id.TextView_bmi);
        btn_bmi_show = findViewById(R.id.btn_bmi_show);
        weight = findViewById(R.id.EditText_weight);
        height = findViewById(R.id.EditText_height);
        ToImperial = findViewById(R.id.sw_ToImperial);
        height_ft = findViewById(R.id.et_ft);
        height_inch = findViewById(R.id.et_inch);
        btn_bmi_save = findViewById(R.id.btn_bmi_save);
        test = findViewById(R.id.test);
        btn_bmi_save.setVisibility(View.GONE);
        ad_banner_bmi = findViewById(R.id.ad_banner_bmi);
        szczala = findViewById(R.id.szczala);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        ad_banner_bmi.loadAd(adRequest);


        final SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        ToImperial.setChecked(sharedPreferences.getBoolean("value",true));

        if(weight ==null || height == null){
            Toast.makeText(bmi.this,"error",Toast.LENGTH_LONG).show();
        }
//jest w imperialnym
        if (ToImperial.isChecked()){
            ToImperial.setChecked(true);
            ToImperial.setText("Switch to Metric   ");
            weight.setHint("weight in lbs");
            height.setVisibility(View.GONE);
            height_ft.setVisibility(View.VISIBLE);
            height_inch.setVisibility(View.VISIBLE);
            height_ft.setHint("ft");
            height_inch.setHint("inches");


        }

        ToImperial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                weight.setText("");
                height.setText("");
                if(isChecked){
                    //jesli jest w imperialnym
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    ToImperial.setChecked(true);
                    ToImperial.setText("Switch to Metric   ");
                    weight.setHint("weight in lbs");
                    height.setVisibility(View.GONE);
                    height_ft.setVisibility(View.VISIBLE);
                    height_inch.setVisibility(View.VISIBLE);
                    height_ft.setHint("ft");
                    height_inch.setHint("inches");






                }else{
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    ToImperial.setChecked(false);
                    ToImperial.setText("Switch to Imperial");
                    weight.setHint("Weight in kg");
                    height.setVisibility(View.VISIBLE);
                    height_ft.setVisibility(View.GONE);
                    height_inch.setVisibility(View.GONE);

                }
            }
        });


        btn_bmi_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try {


                    try {


                        if (ToImperial.isChecked()) {
                            //funty


                            Double weightValue = Double.parseDouble(weight.getText().toString());
                            Double heightValue = Double.parseDouble(height_ft.getText().toString());
                            Double heightValueInch = Double.parseDouble(height_inch.getText().toString());
                            Double totalInches = heightValue * 12 + heightValueInch;

                            Double Bmi_Index = weightValue / (totalInches * totalInches) * 703;
                            Double rounded = new BigDecimal(Bmi_Index).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            BMI_Value.setText("BMI: " + rounded.toString());
                            btn_bmi_save.setVisibility(View.VISIBLE);
                            float anim = rounded.floatValue();

                            if(anim <= 18.5){
                                szczala.animate().rotation(anim * 2.24f);
                            }else if(anim <=25 && anim > 18.5){
                                szczala.animate().rotation(anim * 3.6f);
                            }else if( anim > 25 && anim <= 30){
                                szczala.animate().rotation(anim * 4.5f);
                            }else if(anim > 30  && anim <= 36.4) {
                                szczala.animate().rotation(anim * 5f);
                            }else if( anim > 36.4) {
                                szczala.animate().rotation(180f);
                            }


                            //120 cali

                        } else {
                            //kg


                            Double weightValue = Double.parseDouble(weight.getText().toString());
                            Double heightValue = Double.parseDouble(height.getText().toString());

                            Double Bmi_Index = weightValue / (heightValue * heightValue) * 10000;
                            Double rounded = new BigDecimal(Bmi_Index).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            BMI_Value.setText("BMI: " + rounded.toString());
                            btn_bmi_save.setVisibility(View.VISIBLE);
                            float anim = rounded.floatValue();

                            if(anim <= 18.5){
                                szczala.animate().rotation(anim * 2.24f);
                            }else if(anim <=25 && anim > 18.5){
                                szczala.animate().rotation(anim * 3.6f);
                            }else if( anim > 25 && anim <= 30){
                                szczala.animate().rotation(anim * 4.5f);
                            }else if(anim > 30  && anim <= 36.4) {
                                szczala.animate().rotation(anim * 5f);
                            }else if( anim > 36.4) {
                                szczala.animate().rotation(180f);
                            }





                        }
                    } catch (Exception e) {

                        if (height_inch.getText().toString().length() == 0) {
                            //kod który jest zbyt skomplikowany niż powonien być ale jestem nie wypany i jest taki bo tak.


                            Double weightValue = Double.parseDouble(weight.getText().toString());
                            Double heightValue = Double.parseDouble(height_ft.getText().toString());
                            Double heightValueInch = 0.0;
                            Double totalInches = heightValue * 12 + heightValueInch;

                            Double Bmi_Index = weightValue / (totalInches * totalInches) * 703;
                            Double rounded = new BigDecimal(Bmi_Index).setScale(2, RoundingMode.HALF_UP).doubleValue();
                            BMI_Value.setText("BMI: " + rounded.toString());
                            btn_bmi_save.setVisibility(View.VISIBLE);
                            float anim = rounded.floatValue();

                            if(anim <= 18.5){
                                szczala.animate().rotation(anim * 2.24f);
                            }else if(anim <=25 && anim > 18.5){
                                szczala.animate().rotation(anim * 3.6f);
                            }else if( anim > 25 && anim <= 30){
                                szczala.animate().rotation(anim * 4.5f);
                            }else if(anim > 30  && anim <= 36.4) {
                                szczala.animate().rotation(anim * 5f);
                            }else if( anim > 36.4) {
                                szczala.animate().rotation(180f);
                            }

                        }
                    }

                }catch(Exception a){
                    Toast.makeText(bmi.this, "put in your measurements", Toast.LENGTH_LONG).show();
                }


            }
        });


        btn_bmi_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmiModel bmiModel;

                try{
                    c = Calendar.getInstance();
                    Date = c.get(Calendar.YEAR)+"/"+pad(c.get(Calendar.MONTH)+1)+"/"+pad(c.get(Calendar.DAY_OF_MONTH));
                    test.setText(Date);

                    bmiModel  = new BmiModel(-1, Date, (String) BMI_Value.getText());

                    DataBaseHelperBmi dataBaseHelperBmi = new DataBaseHelperBmi(bmi.this, 1);
                    boolean succes = dataBaseHelperBmi.addOne(bmiModel);

                    Toast.makeText(bmi.this, "saving successful", Toast.LENGTH_SHORT).show();

                    btn_bmi_save.setVisibility(View.GONE);


                }catch(Exception e){
                    Toast.makeText(bmi.this, "error ", Toast.LENGTH_LONG).show();
                    bmiModel  = new BmiModel( -1, "error", null);


                }



            }
        });


    }

    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }



}
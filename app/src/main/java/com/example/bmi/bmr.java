package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

public class bmr extends AppCompatActivity {
    EditText weight, height,height_ft, height_inch, et_age;
    Spinner spinner;
    TextView BMR_Value, textView_bmrActiv, textViewInfoBmr,textViewInfoNeeds;
    Button btn_bmr_show,btn_save_bmr;
    Switch ToImperial;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ArrayAdapter arrayAdapter;
    Calendar c;
    String Date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bmr);
        btn_save_bmr = findViewById(R.id.btn_save_bmr);
        spinner = findViewById(R.id.spinner_activity);
        BMR_Value = findViewById(R.id.TextView_bmr);
        btn_bmr_show = findViewById(R.id.btn_bmr_show);
        weight = findViewById(R.id.EditText_weight);
        height = findViewById(R.id.EditText_height);
        ToImperial = findViewById(R.id.sw_ToImperial);
        height_ft = findViewById(R.id.et_ft);
        height_inch = findViewById(R.id.et_inch);
        et_age = findViewById(R.id.et_age);
        radioGroup = findViewById(R.id.radiogroup);
        textView_bmrActiv = findViewById(R.id.textView_bmrActiv);
        textViewInfoBmr = findViewById(R.id.textViewInfoBmr);
        textViewInfoNeeds = findViewById(R.id.textViewInfoNeeds);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        textViewInfoBmr.setVisibility(View.GONE);
        textViewInfoNeeds.setVisibility(View.GONE);
        btn_save_bmr.setVisibility(View.GONE);
        final ArrayList<String> activity = new ArrayList<String>();
        activity.add("Sedentary: little or no exercise");
        activity.add("Exercise 1-3 times/week");
        activity.add("Exercise 4-5 times/week");
        activity.add("Intense exercise 6-7 times/week");
        activity.add("Very intense exercise daily, or physical job");


        arrayAdapter = new ArrayAdapter<>(bmr.this, R.layout.white, activity);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(null);

        final SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        ToImperial.setChecked(sharedPreferences.getBoolean("value",true));

        if(weight ==null || height == null){
            Toast.makeText(bmr.this,"error",Toast.LENGTH_LONG).show();
        }



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


        btn_bmr_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewInfoBmr.setVisibility(View.VISIBLE);
                textViewInfoNeeds.setVisibility(View.VISIBLE);
                try {


                    Integer wartoscPlci;
                    int radioID = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioID);
                    if (radioButton.getText().toString().equals("male")) {
                        wartoscPlci = 5;
                    } else {
                        wartoscPlci = -161;
                    }
                    Double wartoscAktyw;
                    if (spinner.getSelectedItem().toString().equals("Sedentary: little or no exercise")) {
                        wartoscAktyw = 1.2;
                    } else if (spinner.getSelectedItem().toString().equals("Exercise 1-3 times/week")) {
                        wartoscAktyw = 1.375;
                    } else if (spinner.getSelectedItem().toString().equals("Exercise 4-5 times/week")) {
                        wartoscAktyw = 1.55;
                    } else if (spinner.getSelectedItem().toString().equals("Intense exercise 6-7 times/week")) {
                        wartoscAktyw = 1.725;
                    } else {
                        wartoscAktyw = 1.9;
                    }

                    try {


                        if (ToImperial.isChecked()) {
                            //funty

                            Double weightValue = Double.parseDouble(weight.getText().toString());
                            Double heightValue = Double.parseDouble(height_ft.getText().toString());
                            Double heightValueInch = Double.parseDouble(height_inch.getText().toString());
                            Double totalInches = heightValue * 12 + heightValueInch;
                            Double age = Double.parseDouble(et_age.getText().toString());

                            Double weightKG = weightValue * 0.453592;
                            Double heightCM = totalInches * 2.54;

                            Double bmr = (10 * weightKG) + (6.25 * heightCM) - (5 * age) + 5 + wartoscPlci;
                            //   Double rounded = new BigDecimal(bmr).setScale(0, RoundingMode.HALF_UP).doubleValue();

                            Integer intVAlbmr = bmr.intValue();
                            String bmrReal = intVAlbmr.toString();

                            Double CalNeeds = bmr * wartoscAktyw;

                            Integer intValNeeds = CalNeeds.intValue();
                            String NeedsReal = intValNeeds.toString();

                            BMR_Value.setText("BMR: " + bmrReal);

                            textView_bmrActiv.setText("Needed: "  + NeedsReal);

                            btn_save_bmr.setVisibility(View.VISIBLE);
                        } else {
                            //kg

                            Double weightValue = Double.parseDouble(weight.getText().toString());
                            Double heightValue = Double.parseDouble(height.getText().toString());
                            Double age = Double.parseDouble(et_age.getText().toString());
                            Double bmr = (10 * weightValue) + (6.25 * heightValue) - (5 * age) + wartoscPlci;

                            Integer intVAlbmr = bmr.intValue();
                            String bmrReal = intVAlbmr.toString();

                            Double CalNeeds = bmr * wartoscAktyw;

                            Integer intValNeeds = CalNeeds.intValue();
                            String NeedsReal = intValNeeds.toString();

                            BMR_Value.setText("BMR: " + bmrReal);

                            textView_bmrActiv.setText("Needed: " + NeedsReal);

                            btn_save_bmr.setVisibility(View.VISIBLE);

                        }
                    } catch (Exception e) {
                        if (height_inch.getText().length() == 0) {
                            Double weightValue = Double.parseDouble(weight.getText().toString());
                            Double heightValue = Double.parseDouble(height_ft.getText().toString());
                            Double heightValueInch = 0.0;
                            Double totalInches = heightValue * 12 + heightValueInch;
                            Double age = Double.parseDouble(et_age.getText().toString());

                            Double weightKG = weightValue * 0.453592;
                            Double heightCM = totalInches * 2.54;

                            Double bmr = (10 * weightKG) + (6.25 * heightCM) - (5 * age) + 5 + wartoscPlci;
                            //   Double rounded = new BigDecimal(bmr).setScale(0, RoundingMode.HALF_UP).doubleValue();

                            Integer intVAlbmr = bmr.intValue();
                            String bmrReal = intVAlbmr.toString();

                            Double CalNeeds = bmr * wartoscAktyw;

                            Integer intValNeeds = CalNeeds.intValue();
                            String NeedsReal = intValNeeds.toString();

                            BMR_Value.setText("BMR: " + bmrReal);

                            textView_bmrActiv.setText("Needed: " + NeedsReal);

                            btn_save_bmr.setVisibility(View.VISIBLE);
                        }
                    }

                } catch(Exception a){
                    Toast.makeText(bmr.this, "put in your measurements", Toast.LENGTH_SHORT).show();

                }

                }



        });


        btn_save_bmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BmrModel bmrModel;

                try{
                    c = Calendar.getInstance();
                    Date = c.get(Calendar.YEAR)+"/"+pad(c.get(Calendar.MONTH)+1) + "/"+pad(c.get(Calendar.DAY_OF_MONTH));


                    bmrModel  = new BmrModel(-1, Date, (String) BMR_Value.getText(), (String) textView_bmrActiv.getText());

                    DataBaseHelperBmr dataBaseHelperBmr = new DataBaseHelperBmr(bmr.this, 1);
                    boolean succes = dataBaseHelperBmr.addOne(bmrModel);

                    Toast.makeText(bmr.this, "saving successful", Toast.LENGTH_SHORT).show();


                    btn_save_bmr.setVisibility(View.GONE);



                }catch(Exception e){
                    Toast.makeText(bmr.this, "error ", Toast.LENGTH_LONG).show();
                    bmrModel  = new BmrModel( -1, "error", "error", "error");


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
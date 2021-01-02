package com.example.bmi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class testdb extends AppCompatActivity {
        Button btn_show, btn_bmrShow;
        ListView view;
        String pokaz;
        InterstitialAd interstitialAd;


        private LineChart mChart;

         private static final String TAG = "testdb";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdb);
        btn_show = findViewById(R.id.btn_show);
        view = findViewById(R.id.view);
        btn_bmrShow = findViewById(R.id.btn_bmrShow);
        mChart = findViewById(R.id.Chart);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());





        DataBaseHelperBmi dataBaseHelperBmi = new DataBaseHelperBmi(testdb.this, 1);
        List<BmiModel> everyone = dataBaseHelperBmi.getEveryone();


        ArrayAdapter customerArrayAdapter = new ArrayAdapter<BmiModel>(testdb.this, R.layout.white, everyone);
        Collections.reverse(everyone);
        view.setAdapter(customerArrayAdapter);

        pokaz = "bmi";



        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelperBmi dataBaseHelperBmi = new DataBaseHelperBmi(testdb.this, 1);
                List<BmiModel> everyone = dataBaseHelperBmi.getEveryone();


                ArrayAdapter customerArrayAdapter = new ArrayAdapter<BmiModel>(testdb.this, R.layout.white, everyone);
                Collections.reverse(everyone);
                view.setAdapter(customerArrayAdapter);

                pokaz = "bmi";

                Integer randomInt = (int)(4.0 * Math.random());

                String randomowa = randomInt.toString();

                if( randomInt.equals(1)) {

                    interstitialAd.show();


                }else {}



            }
        });


        btn_bmrShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DataBaseHelperBmr dataBaseHelperBmr = new DataBaseHelperBmr(testdb.this, 1);
                List<BmrModel> everyone = dataBaseHelperBmr.getEveryone();

                ArrayAdapter customerArrayAdapter = new ArrayAdapter<BmrModel>(testdb.this, R.layout.white, everyone);
                Collections.reverse(everyone);
                view.setAdapter(customerArrayAdapter);

                pokaz = "bmr";



                    Integer randomInt = (int)(4.0 * Math.random());

                    String randomowa = randomInt.toString();

                    if( randomInt.equals(1)) {
                        interstitialAd.show();
                    }else {}





            }
        });
 /*
        view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View V, int position, long id) {
try {
            if(pokaz == "bmi") {


                DataBaseHelperBmi dataBaseHelperBmi = new DataBaseHelperBmi(testdb.this, 1);
                List<BmiModel> everyone = dataBaseHelperBmi.getEveryone();
                BmiModel clickedExer = (BmiModel) parent.getItemAtPosition(position);
                dataBaseHelperBmi.deleteOne(clickedExer);
                ArrayAdapter exerciseArrayAdapter = new ArrayAdapter<>(testdb.this, R.layout.white, everyone);
                Collections.reverse(everyone);
                view.setAdapter(exerciseArrayAdapter);
            }else if(pokaz == "bmr"){

              DataBaseHelperBmr dataBaseHelperBmr = new DataBaseHelperBmr(testdb.this, 1);
                List<BmrModel> everyone = dataBaseHelperBmr.getEveryone();
                BmrModel clickedExer = (BmrModel) parent.getItemAtPosition(position);
                dataBaseHelperBmr.deleteOne(clickedExer);
                ArrayAdapter exerciseArrayAdapter = new ArrayAdapter<>(testdb.this, R.layout.white, everyone);
                Collections.reverse(everyone);
                view.setAdapter(exerciseArrayAdapter);

            }



                }catch (Exception e){
    Toast.makeText(testdb.this, "wyjebao sie", Toast.LENGTH_SHORT).show();
}

                return false;
            }
        });
 */

/*
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);


        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry( 0, 60f));
        yValues.add(new Entry( 1, 65f));
        yValues.add(new Entry( 2, 70f));
        yValues.add(new Entry( 3, 45f));
        yValues.add(new Entry( 4, 80f));
        yValues.add(new Entry( 5, 60f));

        LineDataSet set1 = new LineDataSet(yValues, "BMI value");
        set1.setFillAlpha(110);
        set1.setColor(Color.CYAN);
        set1.setLineWidth(3f);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);
            mChart.setData(data);
*/
    }
}
package com.example.bb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import API.flightsModel;
import API.flightsRestRepository;

public class MainActivity extends AppCompatActivity {

    public static String deptCountry;
    public static String deptDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.findTrip);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //gets text of departure country and departure and arrival dates
                EditText editText_editDeptCountry = findViewById(R.id.editDeptCountry);
                deptCountry = editText_editDeptCountry.getText().toString();

                EditText editText_editDeptDate = findViewById(R.id.editDeptDate);
                deptDate = editText_editDeptDate.getText().toString();

                EditText editText_editArrivalDate = findViewById(R.id.editArrivalDate);
                String arrivalDate = editText_editArrivalDate.getText().toString();

                loadFlights();


            }

        });

    }

    private void loadFlights(){
        flightsRestRepository.getInstance().fetchFlights().observe(this,this::populateFlightsContainer);
    }

    private void populateFlightsContainer(List<flightsModel> flightsFound){

        if(flightsFound!=null){
            for(flightsModel flights:flightsFound){

            }
        }
        else{

        }

    }


}
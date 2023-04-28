package com.example.bb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import API.flightsModel;
import API.flightsRestRepository;

import API.hotelsModel;
import API.hotelsRestRepository;

public class MainActivity extends AppCompatActivity {

    public static String deptCountry;
    public static String deptDate;
    public static Date thereArrivalDate;
    public static String arrivalDate;
    public static Date thereDeptDate;
    public static String arrivalCountry;
    /*  At Index:
        0: store flight there
        1: store hotel
        2: store flight back
    */
    public static Object[][] trips = new Object[10][3];

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /*  since each asynch process is called in the populateFlightsThereContainer we first check
        if it is finished. while doing this for every increment we add to totalProcesses. in check
        total processes is multiplied by two since we have loadFlightsBack and loadHotels
    */
    private boolean loadFlightsThereFinished = false;
    private int processesFinished = 0;
    private int totalProcesses=0;


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
                arrivalDate = editText_editArrivalDate.getText().toString();

                loadTripsLoadingLayout();

            }

        });

    }

    private void loadTripsLoadingLayout() {
        setContentView(R.layout.trips_loading);

        //load and sort flights
        loadFlightsThere();
    }

    /*   fetch Flights is able to get flights from origin to destination and vice versa.
    flightDirection = 0 is from origin to destination
    flightDirection= 1 is the reverse
    */
    private void loadFlightsThere(){
        LiveData<List<flightsModel>> flightLiveData = flightsRestRepository.getInstance().fetchFlights(0);
        flightLiveData.observe(this, new Observer<List<flightsModel>>() {
            @Override
            public void onChanged(List<flightsModel> flightList) {
                populateFlightsThereContainer(flightList);
            }
        });
    }

    private void loadFlightsBack(int tripsArrayIndex){
        LiveData<List<flightsModel>> flightLiveData = flightsRestRepository.getInstance().fetchFlights(1);
        flightLiveData.observe(this, new Observer<List<flightsModel>>() {
            @Override
            public void onChanged(List<flightsModel> flightListBack) {
                populateFlightsBackContainer(flightListBack,tripsArrayIndex);
            }
        });
    }

    private void loadHotels(int tripsArrayIndex){
        LiveData<List<hotelsModel>> hotelLiveData = hotelsRestRepository.getInstance().fetchHotels();
        hotelLiveData.observe(this, new Observer<List<hotelsModel>>() {
            @Override
            public void onChanged(List<hotelsModel> hotelList) {
                populateHotelsContainer(hotelList,tripsArrayIndex);
            }
        });
    }

    private void populateFlightsThereContainer(List<flightsModel> flightsThereFound){

        if(flightsThereFound!=null){
            // Sort the flightsThere by price, in ascending order
            Collections.sort(flightsThereFound, new Comparator<flightsModel>() {
                @Override
                public int compare(flightsModel flight1, flightsModel flight2) {
                    // Parse the flight prices to double values
                    double price1 = flight1.getPrice();
                    double price2 = flight2.getPrice();
                    // Compare the prices and return the result
                    return Double.compare(price1, price2);
                }
            });

            //reset trips array and noOfTrips
            int tripsArrayIndex = 0;
            int noOfTrips = 0;
            // Loop through the sorted flights
            if(flightsThereFound!=null) {
                for (flightsModel flight : flightsThereFound) {
                    if (noOfTrips < 10) {
                        arrivalCountry = flight.getDestination();
                        try {
                            thereArrivalDate = dateFormat.parse(flight.getArrivalDate());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                        trips[tripsArrayIndex][0] = flightsThereFound.get(noOfTrips);
                        loadFlightsBack(tripsArrayIndex);
                        loadHotels(tripsArrayIndex);
                        tripsArrayIndex++;
                        noOfTrips++;
                        totalProcesses++;
                    } else {

                    }
                }

                loadFlightsThereFinished=true;
                allProcessesFinished();


            }

        }
        else{

        }
    }

    private void populateFlightsBackContainer(List<flightsModel> flightsBackFound,int tripsArrayIndex) {

        if(flightsBackFound!=null) {
            // Sort the flightsThere by price, in ascending order
            Collections.sort(flightsBackFound, new Comparator<flightsModel>() {
                @Override
                public int compare(flightsModel flight1, flightsModel flight2) {
                    // Parse the flight prices to double values
                    double price1 = flight1.getPrice();
                    double price2 = flight2.getPrice();
                    // Compare the prices and return the result
                    return Double.compare(price1, price2);
                }
            });

            trips[tripsArrayIndex][2] = flightsBackFound.get(0);
            try {
                thereDeptDate = dateFormat.parse(flightsBackFound.get(0).getDepartureDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            processesFinished++;
            allProcessesFinished();

        }
        else{

        }

    }

    private void populateHotelsContainer(List<hotelsModel> hotelsModels, int tripsArrayIndex) {

        if(hotelsModels!=null) {
            // Sort the hotels by price, in ascending order
            Collections.sort(hotelsModels, new Comparator<hotelsModel>() {
                @Override
                public int compare(hotelsModel hotel1, hotelsModel hotel2) {
                    // Parse the hotel prices to double values
                    double price1 = hotel1.getPrice();
                    double price2 = hotel2.getPrice();
                    // Compare the prices and return the result
                    return Double.compare(price1, price2);
                }
            });

            trips[tripsArrayIndex][1] = hotelsModels.get(0);
            for(int i=0; i<1000;i++){
                i++;
                i--;
            }

            processesFinished++;
            allProcessesFinished();
        }
        else{

        }

    }

    private void allProcessesFinished(){
        if(processesFinished==totalProcesses*2 && loadFlightsThereFinished) {

            loadFlightsThereFinished=false;
            processesFinished=0;
            totalProcesses = 0;

            setContentView(R.layout.activity_main);
        }
    }

    private void orderFlights(){
        for(int i=0;i< trips.length;i++){
            if(trips[i]!=null && trips[i+1]!=null) {
                flightsModel flight1 = (flightsModel) trips[i][0];
                flightsModel flight2 = (flightsModel) trips[i][2];
                hotelsModel hotel = (hotelsModel) trips[i][1];
                double totalPrice1 = flight1.getPrice()+flight2.getPrice()+hotel.getPrice();
            }
        }
    }



}
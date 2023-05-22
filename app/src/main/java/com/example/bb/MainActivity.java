package com.example.bb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import API.FlightsCallback;
import API.HotelsCallback;
import API.flightsModel;
import API.flightsRestRepository;

import API.hotelsModel;
import API.hotelsRestRepository;
import DB.DBHelper;

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

    private AtomicInteger completedRequests = new AtomicInteger(0);
    private AtomicInteger completedHotelRequests = new AtomicInteger(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setHeader(0);

        //gets all inputted values on button click and queries API
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

    private void setHeader(int page){
        //sets header according to page
        final Context context = this;
        Header header;

        if(page==0) {
            header = new Header(findViewById(R.id.header_layout), new Header.HeaderClickListener() {

                @Override
                public void onImageLeftClick() {

                }

                @Override
                public void onImageRightClick() {
                    Intent renderFavsIntent = new Intent(context, renderFavourites.class);
                    startActivity(renderFavsIntent);
                }


            });
        }
        else{
            header = new Header(findViewById(R.id.header_layout), new Header.HeaderClickListener() {

                @Override
                public void onImageLeftClick() {
                    Intent renderFavsIntent = new Intent(context, MainActivity.class);
                    startActivity(renderFavsIntent);
                }

                @Override
                public void onImageRightClick() {
                    Intent renderFavsIntent = new Intent(context, renderFavourites.class);
                    startActivity(renderFavsIntent);
                }


            });
        }

        if(page==0) {
            header.setImageLeft(getResources().getIdentifier("logo", "drawable", getPackageName()));
        }
        else{
            header.setImageLeft(getResources().getIdentifier("arrow", "drawable", getPackageName()));
        }
        header.setImageRight(getResources().getIdentifier("heart","drawable",getPackageName()));
        header.setTitle("Find Trip");
    }

    //loads loading page while users wait
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
        flightsRestRepository repository = flightsRestRepository.getInstance();
        repository.fetchFlights(0, new FlightsCallback(){
            @Override
            public void onSuccess(List<flightsModel> flights) {
                populateFlightsThereContainer(flights);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }
    private void populateFlightsThereContainer(List<flightsModel> flightsThereFound){

        if(flightsThereFound.size()>0){
            // Sort the flightsThere by price, in ascending order
            Collections.sort(flightsThereFound, new Comparator<flightsModel>() {
                @Override
                public int compare(flightsModel flight1, flightsModel flight2) {
                    double price1 = flight1.getPrice();
                    double price2 = flight2.getPrice();
                    return Double.compare(price1, price2);
                }
            });

            // Loop through the sorted flights and add to the trips array at index [0]
            if(flightsThereFound!=null) {
                for (int i=0;i<trips.length;i++){
                        trips[i][0] = flightsThereFound.get(i);
                }

                setFlightsBack();


            }

        }
        else{
            setContentView(R.layout.no_trips);
            setHeader(1);
        }
    }
    private void setFlightsBack(){
        /*loops through the trips array which has sorted flights to various destinations and calls
        the method to find and sort the flights back
        */
        for(int i=0;i<trips.length;i++){
            flightsModel flight = (flightsModel) trips[i][0];
            if(flight!=null) {
                arrivalCountry = flight.getDestination();
                arrivalDate = flight.getArrivalDate();
                try {
                    loadFlightsBack(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    //calls API to query for flights back
    private void loadFlightsBack(int i) throws InterruptedException{
        flightsRestRepository repository = flightsRestRepository.getInstance();
        repository.fetchFlights(1, new FlightsCallback() {
            @Override
            public void onSuccess(List<flightsModel> flights) {
                populateFlightsBackContainer(flights, i);

                int completed = completedRequests.incrementAndGet();
                if (completed == trips.length) {
                    setHotels();
                }
            }


            //need to create method to delete trip if flight back not found
            @Override
            public void onFailure(String errorMessage) {
                Log.e("loadFlightsBack", "Error: " + errorMessage);

                int completed = completedRequests.incrementAndGet();
                if (completed == trips.length) {
                    setHotels();
                }
            }

        });
    }

    private void populateFlightsBackContainer(List<flightsModel> flightsBackFound,int i) {

        if(flightsBackFound!=null) {
            // Sort the flightsThere by price, in ascending order
            Collections.sort(flightsBackFound, new Comparator<flightsModel>() {
                @Override
                public int compare(flightsModel flight1, flightsModel flight2) {
                    double price1 = flight1.getPrice();
                    double price2 = flight2.getPrice();
                    return Double.compare(price1, price2);
                }
            });

            // add the cheapest flight (the one at index 0) to the appropriate index
            trips[i][2] = flightsBackFound.get(0);

        }
        else{
            //need to add method to delete trip if flight back not found
        }

    }

    //loops through trips and searches for hotels
    private void setHotels(){
        for(int i=0;i<trips.length;i++){
            flightsModel flight1 = (flightsModel) trips[i][0];
            flightsModel flight2 = (flightsModel) trips[i][2];
            if(flight2!=null) {
                arrivalCountry = flight1.getDestination();
                try {
                    thereArrivalDate = dateFormat.parse(flight1.getArrivalDate());
                    thereDeptDate = dateFormat.parse(flight2.getDepartureDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                loadHotels(i);
            }
        }

    }


    //loads hotels from API
    private void loadHotels(int i){
        hotelsRestRepository repository = hotelsRestRepository.getInstance();
        repository.fetchHotels(new HotelsCallback() {
            @Override
            public void onSuccess(List<hotelsModel> hotels) {
                populateHotelsContainer(hotels, i);

                int completed = completedHotelRequests.incrementAndGet();
                if (completed == trips.length) {
                    orderTrips();
                }
            }


            @Override
            public void onFailure(String errorMessage) {
                Log.e("loadHotels", "Error: " + errorMessage);

                int completed = completedHotelRequests.incrementAndGet();
                if (completed == trips.length) {
                    orderTrips();
                }
            }

        });

    }
    private void populateHotelsContainer(List<hotelsModel> hotelsModels, int i) {

        if(hotelsModels!=null && !hotelsModels.isEmpty()) {
            // Sort the hotels by price, in ascending order
            Collections.sort(hotelsModels, new Comparator<hotelsModel>() {
                @Override
                public int compare(hotelsModel hotel1, hotelsModel hotel2) {
                    double price1 = hotel1.getPrice();
                    double price2 = hotel2.getPrice();
                    return Double.compare(price1, price2);
                }
            });

            //add cheapest hotel to appropriate trip
            trips[i][1] = hotelsModels.get(0);
        }
        else{
        }

    }

    //iterates through trips and sorts by cheapest overall trip. (algorithm can be way faster)
    private void orderTrips(){
        for(int j=0;j< trips.length;j++) {
            for (int i = 0; i < trips.length-1; i++) {
                if (trips[i] != null && trips[i + 1][0]!= null) {

                    flightsModel flight1 = (flightsModel) trips[i][0];
                    flightsModel flight2 = (flightsModel) trips[i][2];
                    hotelsModel hotel = (hotelsModel) trips[i][1];

                    if (flight1 != null && flight2 != null && hotel != null) {
                        double totalPrice1 = flight1.getPrice() + flight2.getPrice() + hotel.getPrice();


                        flight1 = (flightsModel) trips[i + 1][0];
                        flight2 = (flightsModel) trips[i + 1][2];
                        hotel = (hotelsModel) trips[i + 1][1];

                        if (flight1 != null && flight2 != null && hotel != null) {
                            double totalPrice2 = flight1.getPrice() + flight2.getPrice() + hotel.getPrice();

                            if (totalPrice1 > totalPrice2) {

                                Object[] temp = trips[i];
                                trips[i] = trips[i + 1];
                                trips[i + 1] = temp;

                            }
                        }

                    }

                }
            }
        }

        //goes to renderTrips to display the trips found. for this reason the trips array is passed
        Intent renderTripsIntent = new Intent(this,renderTrips.class);
        renderTripsIntent.putExtra("trips",trips);

        startActivity(renderTripsIntent);

    }



}
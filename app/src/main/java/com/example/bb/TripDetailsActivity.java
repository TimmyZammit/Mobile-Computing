package com.example.bb;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import API.flightsModel;
import API.hotelsModel;
import DB.DBHelper;
import DB.Trip;

public class TripDetailsActivity extends AppCompatActivity {

    private boolean isFav=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Get the tripData from the intent
        Object[] tripData = (Object[]) getIntent().getSerializableExtra("tripData");
        setContentView(R.layout.trip_details);

        final Context context = this;

        Header header = new Header(findViewById(R.id.header_layout), new Header.HeaderClickListener() {

            @Override
            public void onImageLeftClick() {
                Intent backIntent = new Intent(context,renderTrips.class);
                startActivity(backIntent);
            }

            @Override
            public void onImageRightClick() {
                Intent renderFavsIntent = new Intent(context,renderFavourites.class);
                startActivity(renderFavsIntent);
            }


        });

        header.setImageLeft(getResources().getIdentifier("arrow","drawable",getPackageName()));
        header.setImageRight(getResources().getIdentifier("heart","drawable",getPackageName()));
        header.setTitle("Trip Details");

        //setModels
        flightsModel flightThere = (flightsModel) tripData[0];
        hotelsModel hotel = (hotelsModel) tripData[1];
        flightsModel flightBack = (flightsModel) tripData[2];

        DBHelper dbHelper = new DBHelper(TripDetailsActivity.this);

        /*checks of the current trip is in favourites and if it is it fills the heart and changes
        isFav to enable a toggle to add or remove the trips to favourites
        */
        String[] tripDetails = dbHelper.getTripDetails(flightThere.getOrigin(), flightThere.getDestination());
        if(tripDetails != null) {
            TextView favs = findViewById(R.id.add_favs);
            favs.setText("Remove From Favourites");
            ImageView heart = findViewById(R.id.heart);
            heart.setImageResource(R.drawable.heart_filled);
            isFav =true;
        }

        // sets the top card which contains the location and overall price
        TextView textView = findViewById(R.id.location);
        textView.setText(flightThere.getDestination());

        textView = findViewById(R.id.details_price);
        double totalPrice = flightThere.getPrice() + hotel.getPrice() + flightBack.getPrice();
        String totalPriceString = String.format("%.2f", totalPrice);
        textView.setText("£" + totalPriceString);


        //sets flights there details
        textView = findViewById(R.id.flight_there_company);
        textView.setText(flightThere.getAirline());

        textView = findViewById(R.id.flight_there_code);
        textView.setText(flightThere.getFlightNumber());

        textView = findViewById(R.id.flight_there_price);
        totalPriceString = String.format("%.2f", flightThere.getPrice());
        textView.setText("£" + totalPriceString);

        textView = findViewById(R.id.flight_there_dept_airport_time);
        String airportTime = String.format(flightThere.getOrigin()+"-"+flightThere.getDepartureTime());
        textView.setText(airportTime);

        textView = findViewById(R.id.flight_there_arrival_airport_time);
        airportTime = String.format(flightThere.getDestination()+" - "+flightThere.getArrivalTime());
        textView.setText(airportTime);

        //sets hotel details
        textView = findViewById(R.id.hotel_name);
        textView.setText(hotel.getHotelName());

        textView = findViewById(R.id.hotel_price);
        totalPriceString = String.format("%.2f", hotel.getPrice());
        textView.setText("£" + totalPriceString);

        textView = findViewById(R.id.hotel_address);
        textView.setText(hotel.getAddress());

        //sets flights back details
        textView = findViewById(R.id.flight_back_company);
        textView.setText(flightBack.getAirline());

        textView = findViewById(R.id.flight_back_code);
        textView.setText(flightBack.getFlightNumber());

        textView = findViewById(R.id.flight_back_price);
        totalPrice = flightBack.getPrice();
        totalPriceString = String.format("%.2f", totalPrice);
        textView.setText("£" + totalPriceString);

        textView = findViewById(R.id.flight_back_dept_airport_time);
        airportTime = String.format(flightThere.getDestination()+" - "+flightBack.getDepartureTime());
        textView.setText(airportTime);

        textView = findViewById(R.id.flight_back_arrival_airport_time);
        airportTime = String.format(flightThere.getOrigin()+" - "+flightBack.getArrivalTime());
        textView.setText(airportTime);


        ConstraintLayout addFavsCard = findViewById(R.id.add_favs_card);

         /*checks of the current trip is in favourites and if it is it fills the heart and changes
        isFav to enable a toggle to add or remove the trips to favourites
        */
        addFavsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isFav) {
                    Object[] values = {
                            flightThere.getDestination(),
                            flightThere.getAirline(),
                            flightThere.getFlightNumber(),
                            flightThere.getPrice(),
                            flightThere.getDepartureDate(),
                            flightThere.getDepartureTime(),
                            flightThere.getArrivalDate(),
                            flightThere.getArrivalTime(),
                            hotel.getHotelName(),
                            hotel.getPrice(),
                            hotel.getAddress(),
                            flightBack.getPrice(),
                            flightBack.getDepartureDate(),
                            flightBack.getDepartureTime(),
                            flightBack.getArrivalDate(),
                            flightBack.getArrivalTime(),
                            flightBack.getAirline(),
                            flightBack.getFlightNumber(),
                            flightThere.getOrigin()
                    };

                    Trip trip = new Trip(values);
                    ImageView heart = findViewById(R.id.heart);
                    heart.setImageResource(R.drawable.heart_filled);
                    TextView favs = findViewById(R.id.add_favs);
                    favs.setText("Remove From Favourites");
                    isFav=true;
                    dbHelper.insertTrip(trip);

                    Toast.makeText(TripDetailsActivity.this, "add_favs_card clicked", Toast.LENGTH_SHORT).show();
                }
                else{
                    ImageView heart = findViewById(R.id.heart);
                    heart.setImageResource(R.drawable.heart);
                    TextView favs = findViewById(R.id.add_favs);
                    favs.setText("Add To Favourites");
                    isFav=false;
                    dbHelper.deleteTripDetails(flightThere.getOrigin(),flightThere.getDestination());
                }

            }

        });

    }


}


package com.example.bb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import API.flightsModel;
import API.hotelsModel;
import DB.DBHelper;

public class renderFavourites extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TripsAdapter adapter;
    private DBHelper dbHelper;

    /*calls all values in the local DB. If no values are found then the no favs page is displayed,
    otherwise the values found are displayed
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trips_found);


        recyclerView = findViewById(R.id.trips_list);
        dbHelper = new DBHelper(this);

        List<String[]> trips = dbHelper.getAllTrips();

        if(trips.size()==0){
            setContentView(R.layout.no_favs);
        }

        //uses recycler view and sets favourites, displaying visiting country and total price
        //the same template used in renderTrips is used
        adapter = new TripsAdapter(trips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //sets header depending on the page
        final Context context = this;

        Header header = new Header(findViewById(R.id.header_layout), new Header.HeaderClickListener() {

            @Override
            public void onImageLeftClick() {
                Intent backIntent = new Intent(context,MainActivity.class);
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
        header.setTitle("Favourites");

    }

    private class TripsViewHolder extends RecyclerView.ViewHolder {
        private TextView destinationTextView;
        private TextView priceTextView;

        TripsViewHolder(View view) {
            super(view);
            destinationTextView = view.findViewById(R.id.destination);
            priceTextView = view.findViewById(R.id.price);
        }
    }

    private class TripsAdapter extends RecyclerView.Adapter<TripsViewHolder> {
        private List<String[]> trips;

        TripsAdapter(List<String[]> trips) {
            this.trips = trips;
        }

        @Override
        public TripsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trips_found_card, parent, false);
            return new TripsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TripsViewHolder holder, int position) {
            String[] trip = trips.get(position);
            holder.destinationTextView.setText(trip[1] + " - " + trip[0]);
            holder.priceTextView.setText(trip[2]);

            /* When a favourite trip is clicked all the information about that trip is obtained
            and all appropriate values assigned to and instance of their model which can then be
            in TripDetailsActivity
            This prevents duplication of code.
            */
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the trip details based on the clicked item's origin and destination
                    String[] tripDetails = dbHelper.getTripDetails(trip[1], trip[0]);

                    flightsModel flightThere = new flightsModel();

                    flightThere.setDestination(tripDetails[0]);
                    flightThere.setAirline(tripDetails[1]);
                    flightThere.setFlightNumber(tripDetails[2]);
                    flightThere.setPrice(Double.parseDouble(tripDetails[3]));
                    flightThere.setDepartureDate(tripDetails[4]);
                    flightThere.setDepartureTime(tripDetails[5]);
                    flightThere.setArrivalDate(tripDetails[6]);
                    flightThere.setArrivalTime(tripDetails[7]);
                    flightThere.setOrigin(tripDetails[18]);

                    hotelsModel hotel = new hotelsModel();

                    hotel.setHotelName(tripDetails[8]);
                    hotel.setPrice(Double.parseDouble(tripDetails[9]));
                    hotel.setAddress(tripDetails[10]);

                    flightsModel flightBack = new flightsModel();

                    flightBack.setPrice(Double.parseDouble(tripDetails[11]));
                    flightBack.setDepartureDate(tripDetails[12]);
                    flightBack.setDepartureTime(tripDetails[13]);
                    flightBack.setArrivalDate(tripDetails[14]);
                    flightBack.setArrivalTime(tripDetails[15]);
                    flightBack.setAirline(tripDetails[16]);
                    flightBack.setFlightNumber(tripDetails[17]);


                    Object[] tripData = new Object[] {flightThere, hotel, flightBack};

                    Intent intent = new Intent(v.getContext(), TripDetailsActivity.class);
                    intent.putExtra("tripData", tripData);
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return trips.size();
        }
    }
}



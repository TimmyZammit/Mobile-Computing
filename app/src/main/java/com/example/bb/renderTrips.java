package com.example.bb;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import API.flightsModel;
import API.hotelsModel;

public class renderTrips extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trips_found);

        // Get the trips array from the intent
        Object[][] trips = (Object[][]) intent.getSerializableExtra("trips");

        // Initialize the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.trips_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter with data from the trips array
        TripAdapter tripAdapter = new TripAdapter(trips);
        recyclerView.setAdapter(tripAdapter);
    }


    private class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

        private Object[][] trips;

        public TripAdapter(Object[][] trips) {
            this.trips = trips;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trips_found_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (trips[position][0] != null) {
                flightsModel flight1 = (flightsModel) trips[position][0];
                hotelsModel hotel = (hotelsModel) trips[position][1];
                flightsModel flight2 = (flightsModel) trips[position][2];

                String destination = flight1.getDestination();
                double price = flight1.getPrice();

                // Check if hotel object is not null before calling its methods
                if (hotel != null) {
                    price += hotel.getPrice();
                } else {
                    // Handle the case where hotel data is not available
                }

                if (flight2 != null) {
                    price += flight2.getPrice();
                } else {
                    // Handle the case where flight2 data is not available
                }

                holder.destination.setText(destination);
                holder.price.setText(String.format("£%.2f", price));
            }
        }


        @Override
        public int getItemCount() {
            return trips.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView destination;
            private TextView price;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                destination = itemView.findViewById(R.id.destination);
                price = itemView.findViewById(R.id.price);
            }
        }
    }
}


package API;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class flightsModel {

        @SerializedName("origin")
        private String origin;

        public String getOrigin(){
            return origin;
        }

        @SerializedName("destination")
        private String destination;

        public String getDestination(){
            return destination;
        }

        @SerializedName("departureDate")
        private String departureDate;

        public String getDepartureDate(){
            return departureDate;
        }

        @SerializedName("departureTime")
        private String departureTime;

        public String getDepartureTime(){
            return departureTime;
        }

        @SerializedName("arrivalDate")
        private String arrivalDate;

        public String getArrivalDate(){
            return arrivalDate;
        }

        @SerializedName("arrivalTime")
        private String arrivalTime;

        public String getArrivalTime(){
            return arrivalTime;
        }

        @SerializedName("flightDuration")
        private String flightDuration;

        public String getFlightDuration(){
            return flightDuration;
        }

        @SerializedName("airline")
        private String airline;

        public String getAirline(){
            return airline;
        }

        @SerializedName("flightNumber")
        private String flightNumber;

        public String getFlightNumber(){
            return flightNumber;
        }

        @SerializedName("price")
        private double price;

        public double getPrice(){
            return price;
        }

    }



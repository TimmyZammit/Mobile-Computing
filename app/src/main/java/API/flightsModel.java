package API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

//used to get set and store all value of flights
public class flightsModel implements Serializable {

        @SerializedName("origin")
        private String origin;
        public String getOrigin(){
            return origin;
        }
        public void setOrigin(String origin) { this.origin = origin; }

        @SerializedName("destination")
        private String destination;

        public String getDestination(){
            return destination;
        }
        public void setDestination(String destination) { this.destination = destination; }
        @SerializedName("departureDate")
        private String departureDate;

        public String getDepartureDate(){
            return departureDate;
        }
        public void setDepartureDate(String departureDate) { this.departureDate = departureDate; }
        @SerializedName("departureTime")
        private String departureTime;

        public String getDepartureTime(){
            return departureTime;
        }
        public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
        @SerializedName("arrivalDate")
        private String arrivalDate;

        public String getArrivalDate(){
            return arrivalDate;
        }
        public void setArrivalDate(String arrivalDate) { this.arrivalDate = arrivalDate; }
        @SerializedName("arrivalTime")
        private String arrivalTime;

        public String getArrivalTime(){
            return arrivalTime;
        }
        public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
        @SerializedName("flightDuration")
        private String flightDuration;

        public String getFlightDuration(){
            return flightDuration;
        }
        public void setFlightDuration(String flightDuration) { this.flightDuration = flightDuration; }
        @SerializedName("airline")
        private String airline;

        public String getAirline(){
            return airline;
        }
         public void setAirline(String airline) { this.airline = airline; }

        @SerializedName("flightNumber")
        private String flightNumber;
        public String getFlightNumber(){
            return flightNumber;
        }
        public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

        @SerializedName("price")
        private double price;
        public double getPrice(){
            return price;
        }
        public void setPrice(double price) { this.price = price; }
    }



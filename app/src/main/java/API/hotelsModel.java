package API;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

//used to get set and store all values of hotel
public class hotelsModel implements Serializable {

    @SerializedName("destination")
    private String destination;

    public String getDestination(){
        return destination;
    }
    public void setDestination(String destination) { this.destination = destination; }

    @SerializedName("hotel_name")
    private String hotel_name;

    public String getHotelName(){
        return hotel_name;
    }
    public void setHotelName(String hotel_name) { this.hotel_name = hotel_name; }

    @SerializedName("price_per_night")
    private double price_per_night;

    public double getPrice(){return price_per_night;}
    public void setPrice(double price_per_night) { this.price_per_night = price_per_night; }

    @SerializedName("address")
    private String address;

    public String getAddress(){
        return address;
    }
    public void setAddress(String address) { this.address = address; }

    @SerializedName("available_dates")
    private List<Object> available_dates;

    public List<Object> getAvailableDates(){return available_dates;}


}

package API;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class hotelsModel {

    @SerializedName("destination")
    private String destination;

    public String getDestination(){
        return destination;
    }


    @SerializedName("hotel_name")
    private String hotel_name;

    public String getHotelName(){
        return hotel_name;
    }


    @SerializedName("price_per_night")
    private double price_per_night;

    public double getPrice(){return price_per_night;}


    @SerializedName("address")
    private String address;

    public String getAddress(){
        return address;
    }


    @SerializedName("available_dates")
    private List<Object> available_dates;

    public List<Object> getAvailableDates(){return available_dates;}


}

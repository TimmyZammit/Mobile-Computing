package API;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//used to query API for flights
public interface flights {

    /*link to api. uses current ip address.
    Get ip by opening clicking the windows button and typing cmd.
    Inside the command prompt type "ipconfig"
    find the number for IPV4 and replace it as follows:
    "http://IP address here:3000/"
    */
    String BASE_URL = "http://192.168.4.44:3000/";

    @GET("flights")
    Call<List<flightsModel>> getFlights(
            @Query("origin") String origin,
            @Query("departureDate") String deptDate
    );

    @GET("flights")
    Call<List<flightsModel>> getFlightsBack(
            @Query("origin") String origin,
            @Query("arrivalDate") String arrivalDate,
            @Query("destination") String destination
    );


}

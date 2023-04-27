package API;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface flights {

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

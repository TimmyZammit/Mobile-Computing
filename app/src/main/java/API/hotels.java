package API;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface hotels {

    String BASE_URL = "http://192.168.4.44:3000/";

    @GET("hotels")
    Call<List<hotelsModel>> getHotels(
            @Query("destination") String destination,
            @Query("arrivalDate") Date arrivalDate,
            @Query("departureDate") Date deptDate
    );

}

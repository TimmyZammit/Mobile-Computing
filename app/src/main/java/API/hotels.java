package API;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//used to query API for hotels
public interface hotels {

    /*link to api. uses current ip address.
    Get ip by opening clicking the windows button and typing cmd.
    Inside the command prompt type "ipconfig"
    find the number for IPV4 and replace it as follows:
    "http://IP address here:3000/"
    */
    String BASE_URL = "http://192.168.4.44:3000/";

    @GET("hotels")
    Call<List<hotelsModel>> getHotels(
            @Query("destination") String destination,
            @Query("arrivalDate") Date arrivalDate,
            @Query("departureDate") Date deptDate
    );

}

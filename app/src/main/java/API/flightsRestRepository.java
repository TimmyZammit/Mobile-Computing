package API;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bb.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//used to call from the API
public class flightsRestRepository {
    public static flightsRestRepository instance=null;
    private flights api;
    //uses flights interface to instantiate the api call
    private flightsRestRepository(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(flights.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        api = retrofit.create(flights.class);
    }

    public static synchronized flightsRestRepository getInstance(){
        if(instance==null){
            instance = new flightsRestRepository();
        }
        return instance;
    }

    //call the appropriate method from flights interface to query the API
    public List<flightsModel> fetchFlights(int flightDirection, FlightsCallback callback) {
        Call<List<flightsModel>> flightsCall = null;

        /*flight direction == 0 refers to the departure flight and finds all flights that have the
        same departure country and departure date

        flight direction == 1 refers to the arrival flight and finds all flights that have the same
        arrival country(i.e. country the holiday is in) and the same departure country
        (i.e. home country) and the correct arrival date in the home country.
        */
        if (flightDirection == 0) {
            flightsCall = api.getFlights(MainActivity.deptCountry, MainActivity.deptDate);
        } else if (flightDirection == 1) {
            flightsCall = api.getFlightsBack(MainActivity.arrivalCountry, MainActivity.arrivalDate, MainActivity.deptCountry);
        }
        flightsCall.enqueue(new Callback<List<flightsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<flightsModel>> call, @NonNull Response<List<flightsModel>> response) {
                if (!response.isSuccessful()) {
                    callback.onFailure("Unsuccessful response");
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<flightsModel>> call, @NonNull Throwable t) {
                Log.i("fetchFlights", call.request().toString());
                Log.e("fetchFlights", t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
        return null;
    }

}


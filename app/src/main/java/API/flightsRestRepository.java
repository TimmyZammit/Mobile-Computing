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

public class flightsRestRepository {
    public static flightsRestRepository instance=null;
    private flights api;
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
    public List<flightsModel> fetchFlights(int flightDirection, FlightsCallback callback) {
        Call<List<flightsModel>> flightsCall = null;

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


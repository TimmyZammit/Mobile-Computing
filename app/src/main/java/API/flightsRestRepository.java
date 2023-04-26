package API;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    public LiveData<List<flightsModel>> fetchFlights (){
        final MutableLiveData<List<flightsModel>> flights = new MutableLiveData<>();

        api.getFlights(MainActivity.deptCountry,MainActivity.deptDate).enqueue(new Callback<List<flightsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<flightsModel>> call, @NonNull Response<List<flightsModel>> response) {

                if(!response.isSuccessful()){
                    return;
                }
                flights.setValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<List<flightsModel>> call, @NonNull Throwable t) {
                Log.i("fetchFlights",call.request().toString());
                Log.e("fetchFlights",t.getMessage());
                flights.setValue(null);
            }
        });
        return flights;
    }
}

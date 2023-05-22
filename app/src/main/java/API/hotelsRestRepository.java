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
public class hotelsRestRepository {
    public static hotelsRestRepository instance=null;
    private static hotels api;

    //uses flights interface to instantiate the api call
    private hotelsRestRepository(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(hotels.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(hotels.class);
    }

    public static synchronized hotelsRestRepository getInstance(){
        if(instance==null){
            instance = new hotelsRestRepository();
        }
        return instance;
    }

    //call the appropriate method from hotel interface to query the API
    public static List<hotelsModel> fetchHotels(HotelsCallback callback) {
        Call<List<hotelsModel>> hotelsCall = api.getHotels(MainActivity.arrivalCountry, MainActivity.thereArrivalDate, MainActivity.thereDeptDate);

        hotelsCall.enqueue(new Callback<List<hotelsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<hotelsModel>> call, @NonNull Response<List<hotelsModel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("fail", "Fetched hotels: " + response.toString());
                    callback.onFailure("Unsuccessful response");
                    return;
                }
                Log.d("fetchHotels", "Fetched hotels: " + response.toString());
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<hotelsModel>> call, @NonNull Throwable t) {
                Log.i("fail", call.request().toString());
                Log.e("fail", t.getMessage());
                callback.onFailure(t.getMessage());

                Log.d("fail", "Fetched hotels: ");
            }
        });

        return null;
    }



}



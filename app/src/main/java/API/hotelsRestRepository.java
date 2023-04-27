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

public class hotelsRestRepository {
    public static hotelsRestRepository instance=null;
    private hotels api;

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

    public LiveData<List<hotelsModel>> fetchHotels() {
        final MutableLiveData<List<hotelsModel>> hotels = new MutableLiveData<>();
        Call<List<hotelsModel>> hotelsCall = api.getHotels(MainActivity.arrivalCountry, MainActivity.thereArrivalDate, MainActivity.thereDeptDate);

        hotelsCall.enqueue(new Callback<List<hotelsModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<hotelsModel>> call, @NonNull Response<List<hotelsModel>> response) {
                if(!response.isSuccessful()){
                    return;
                }
                hotels.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<hotelsModel>> call, @NonNull Throwable t) {
                Log.i("fetchHotels", call.request().toString());
                Log.e("fetchHotels", t.getMessage());
                hotels.setValue(null);
            }
        });
        return hotels;
    }


}


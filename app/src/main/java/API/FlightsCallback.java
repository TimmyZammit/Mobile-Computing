package API;

import java.util.List;

//used on result
public interface FlightsCallback {
    void onSuccess(List<flightsModel> flights);

    void onFailure(String errorMessage);
}

package API;

import java.util.List;

public interface FlightsCallback {
    void onSuccess(List<flightsModel> flights);

    void onFailure(String errorMessage);
}

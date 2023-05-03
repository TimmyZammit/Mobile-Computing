package API;

import java.util.List;

public interface HotelsCallback {
    void onSuccess(List<hotelsModel> hotels);

    void onFailure(String errorMessage);
}

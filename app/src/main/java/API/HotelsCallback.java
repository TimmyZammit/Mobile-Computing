package API;

import java.util.List;

//used on result
public interface HotelsCallback {
    void onSuccess(List<hotelsModel> hotels);

    void onFailure(String errorMessage);
}

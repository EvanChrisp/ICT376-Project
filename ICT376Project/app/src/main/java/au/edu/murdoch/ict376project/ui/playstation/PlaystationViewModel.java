package au.edu.murdoch.ict376project.ui.playstation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaystationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlaystationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the Playstation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
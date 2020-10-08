package au.edu.murdoch.ict376project.ui.nintendo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NintendoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NintendoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the Nintendo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}


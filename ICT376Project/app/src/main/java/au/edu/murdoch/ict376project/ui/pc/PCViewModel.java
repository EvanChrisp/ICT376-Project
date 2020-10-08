package au.edu.murdoch.ict376project.ui.pc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PCViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PCViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the PC fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

package au.edu.murdoch.ict376project.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the news fragment");
    }
    // test
    public LiveData<String> getText() {
        return mText;
    }
}
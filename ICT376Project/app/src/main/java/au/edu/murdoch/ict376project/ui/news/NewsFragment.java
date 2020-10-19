package au.edu.murdoch.ict376project.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import au.edu.murdoch.ict376project.R;

public class NewsFragment extends Fragment {

    public static NewsFragment newInstance(){

        NewsFragment newsf = new NewsFragment();

        return newsf;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // int the View
        View mLayoutView = inflater.inflate(R.layout.fragment_news, container, false);

        return mLayoutView;
    }


}
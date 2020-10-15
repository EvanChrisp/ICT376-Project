package au.edu.murdoch.ict376project.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import au.edu.murdoch.ict376project.R;
import au.edu.murdoch.ict376project.ui.contact.ContactViewModel;
import au.edu.murdoch.ict376project.ui.playstation.PlaystationFragment;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;

    public static NewsFragment newInstance(){

        NewsFragment newsf = new NewsFragment();

        return newsf;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        View mLayoutView = inflater.inflate(R.layout.fragment_news, container, false);

        final TextView textView = mLayoutView.findViewById(R.id.text_news);

        newsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return mLayoutView;
    }
}
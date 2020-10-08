package au.edu.murdoch.ict376project.ui.playstation;

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

public class PlaystationFragment extends Fragment {

    private PlaystationViewModel playstationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        playstationViewModel =
                ViewModelProviders.of(this).get(PlaystationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_playstation, container, false);
        final TextView textView = root.findViewById(R.id.text_playstation);
        playstationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}

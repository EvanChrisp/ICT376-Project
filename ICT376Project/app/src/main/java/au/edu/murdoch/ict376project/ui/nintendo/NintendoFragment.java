package au.edu.murdoch.ict376project.ui.nintendo;

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

public class NintendoFragment extends Fragment {

    private NintendoViewModel nintendoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        nintendoViewModel =
                ViewModelProviders.of(this).get(NintendoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nintendo, container, false);
        final TextView textView = root.findViewById(R.id.text_nintendo);
        nintendoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}



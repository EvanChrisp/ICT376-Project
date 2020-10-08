package au.edu.murdoch.ict376project.ui.xbox;

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


public class XboxFragment extends Fragment {

    private XboxViewModel xboxViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        xboxViewModel =
                ViewModelProviders.of(this).get(XboxViewModel.class);
        View root = inflater.inflate(R.layout.fragment_xbox, container, false);
        final TextView textView = root.findViewById(R.id.text_xbox);
        xboxViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}


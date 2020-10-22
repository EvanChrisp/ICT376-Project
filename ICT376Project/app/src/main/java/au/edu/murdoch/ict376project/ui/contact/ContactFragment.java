package au.edu.murdoch.ict376project.ui.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import au.edu.murdoch.ict376project.DirectionsActivity;
import au.edu.murdoch.ict376project.R;

public class ContactFragment extends Fragment
{
    View mLayoutView;
    Button gMaps;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mLayoutView = inflater.inflate(R.layout.fragment_contact, container, false);
        gMaps = (Button)mLayoutView.findViewById(R.id.gMaps);

        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        gMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DirectionsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displaySomething() {
        // todo
    }
}
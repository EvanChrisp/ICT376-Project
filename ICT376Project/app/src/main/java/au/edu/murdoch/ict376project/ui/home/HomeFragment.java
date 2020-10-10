package au.edu.murdoch.ict376project.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Random;

import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.MainActivity;
import au.edu.murdoch.ict376project.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Random rand = new Random();
        Database db = new Database(getActivity());

        for(int i = 1; i < 6; i++)
        {
            ImageView view = root.findViewById(getResources().getIdentifier("deal" + i, "id", getActivity().getPackageName()));

            int id = rand.nextInt((20 - 1) + 1) + 1;
            Cursor res = db.getProductById(id);

            view.setImageResource(getResources().getIdentifier(res.getString(res.getColumnIndex("file")), "drawable", getActivity().getPackageName()));
        }

        return root;
    }
}
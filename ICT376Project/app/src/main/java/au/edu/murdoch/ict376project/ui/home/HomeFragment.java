package au.edu.murdoch.ict376project.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Random;

import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        int i = 1;
        int[] ids = new int[5];
        Random rand = new Random();
        // get db
        Database db = new Database(getActivity());

        // loop through 5 deals
        while(i <= 5)
        {
            int id = rand.nextInt(20 - 1 + 1) + 1;
            if(duplicateDeal(id, ids))
            {
               continue;
            }
            ids[i - 1] = id;

            // get cursor returned from db
            Cursor res = db.getProductById(id);

            // widget = view.findViewById(getResources().getIdentifier(String name, String type, String package);
            // String name is the id in the XML - type is "id" for i.d. , package = getActivity().getPackageName()
            ImageView image = root.findViewById(getResources().getIdentifier("deal" + i, "id", getActivity().getPackageName()));
            TextView name = root.findViewById(getResources().getIdentifier("dealtext" + i, "id", getActivity().getPackageName()));

            // name of the entry in column no. (the one called "file") - as a String
            String mFile = res.getString(res.getColumnIndex("file"));
            String mName = res.getString(res.getColumnIndex("name"));
            // type is drawable
            String mDefType = "drawable";
            // package = getActivity().getPackageName();
            String mDefPackage = getActivity().getPackageName();
            // resource id = getResources().getIdentifier(String name, String defType, String defPackage);
            int resId = getResources().getIdentifier(mFile, mDefType, mDefPackage);
            // set the view to the relevant id
            image.setImageResource(resId);
            name.setText(mName);

            i++;
        }

        return root;
    }

    //check if game already exists in deals of the week
    private boolean duplicateDeal(int id, int[] ids)
    {
        for(int num : ids)
        {
            if(num == id)
                return true;
        }
        return false;
    }
}
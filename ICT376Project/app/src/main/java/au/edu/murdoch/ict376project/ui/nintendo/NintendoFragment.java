package au.edu.murdoch.ict376project.ui.nintendo;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.R;

public class NintendoFragment extends Fragment {

    private NintendoViewModel nintendoViewModel;
    private ListView obj;
    View mLayoutView;

    // Database
    Database mydb = null;
    ArrayList mArrayList;  // the list of all contacts

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // data comes from the nintendoViewModel class
        nintendoViewModel = ViewModelProviders.of(this).get(NintendoViewModel.class);

        // declare view -> inflate (this fragment, into this container, false);
        mLayoutView = inflater.inflate(R.layout.fragment_nintendo, container, false);

        // textView = resource
        final TextView textView = mLayoutView.findViewById(R.id.text_nintendo);

        // use the nintendoViewModel to set the text to String s
        nintendoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        // now return the view
        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        displayProducts();
        Toast.makeText(getActivity(), "Called displayProducts", Toast.LENGTH_SHORT).show();
    }

    private void displayProducts() {

        if(mydb == null){
            mydb = new Database(getActivity());
            /*mydb.addProduct("Mario 3d allstars", 69, "3d adventures of Mario", "all ages", "Nintendo", "0");
            mydb.addProduct("Mario 3d allstars", 69, "3d adventures of Mario", "all ages", "Nintendo", "0");*/

            mArrayList = mydb.getProductList();

            ArrayList<String> array_list = new  ArrayList<String>();

            //String something = "";

            for (int i=0; i<mArrayList.size(); i++){
                Pair<Integer, String> p = (Pair<Integer, String>)mArrayList.get(i);
                array_list.add(p.second);
                //something = p.second;
            }
            // Put all the contacts in an array
            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array_list);

            // Display the products in the ListView object
            obj = (ListView)mLayoutView.findViewById(R.id.productListView);
            obj.setAdapter(arrayAdapter);

            //Toast.makeText(getActivity(), something, Toast.LENGTH_SHORT).show();

        }

        /*if (mydb == null)
            mydb = new Database(getActivity());*//*

        mydb.insertMyShopItems();
        // Get all the contacts from the database
        Cursor cursor = mydb.getAllProducts("0");

        String[] columns = new String[] {
                // comes from StoreDatabase.KEY_NAME
                Database.PRODUCT_NAME
        };

        int[] to = new int[] {
                android.R.layout.simple_list_item_1,
        };

        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(getActivity(), R.layout.fragment_nintendo, cursor, columns, to, 0);

        ListView listView = (ListView)mLayoutView.findViewById(R.id.productListView);
        assert listView != null;
        listView.setAdapter(dataAdapter);*/


       /* ArrayList<String> array_list = new  ArrayList<String>();

        for (int i=0; i<mArrayList.size(); i++){
            Pair<Integer, String> p = (Pair<Integer, String>)mArrayList.get(i);
            array_list.add(p.second);
        }
        // Put all the contacts in an array
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array_list);

        // Display the contacts in the ListView object
        obj = (ListView)mLayoutView.findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);

        // Check the orientation of the display
        //mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        View detailsFrame = getActivity().findViewById(R.id.contactdetails_fragment_container);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;  */
    }
}



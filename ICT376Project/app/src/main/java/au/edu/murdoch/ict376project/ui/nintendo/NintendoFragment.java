package au.edu.murdoch.ict376project.ui.nintendo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import au.edu.murdoch.ict376project.DetailsActivity;
import au.edu.murdoch.ict376project.R;
import au.edu.murdoch.ict376project.ui.pc.PCFragment;

public class NintendoFragment extends Fragment {

    // model class
    private NintendoViewModel nintendoViewModel;
    // listview variable
    private ListView obj;

    private ListView listView;
    // layout view for onCreateView
    View mLayoutView;

    private Database dbHelper;

    // Database
    Database mydb = null;
    ArrayList mArrayList;  // the list of all products

    public static NintendoFragment newInstance(){

        NintendoFragment ninf = new NintendoFragment();
        return ninf;
    }

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

        //displayProducts();
        displayListView();
        //Toast.makeText(getActivity(), "returning Nintendo products", Toast.LENGTH_SHORT).show();
    }

    private void displayListView(){

        // get database
        dbHelper = new Database(getActivity());

        // cursor = return from db function
        Cursor cursor = dbHelper.getCursorProducts("Nintendo");

        // columns to return
        final String[] columns = new String[]{Database.PRODUCT_ID, Database.PRODUCT_NAME, Database.PRODUCT_PLATFORM, Database.PRODUCT_FILE};

        // column data goes to this layout (in item_layout.xml) per item
        int[] lvResourceIds = new int[]{R.id.pIdTextView, R.id.pNameTextView, R.id.pPlatformTextView, R.id.pImageHolder};

        // cursor adapter requires the id to be _id in the database. Please do not change
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(getActivity(),R.layout.item_layout, cursor, columns, lvResourceIds,0);

        // the setViewBinder of the data adapter takes every view called R.id.pImageHolder and replaces it with the resId
        // which as per the db changes for each different game
        dataAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                if (view.getId() == R.id.pImageHolder) {
                    ImageView simpleImageView = (ImageView) view;
                    int resId = getResources().getIdentifier(
                            cursor.getString(cursor.getColumnIndex("file")),
                            "drawable",
                            getActivity().getPackageName());
                    simpleImageView.setImageResource(resId);
                    return true;
                } else {
                    return false;
                }
            }
        });

        // listview - uses the mLayoutView as it is a fragment and not an activity -> listview is displayed in nintendoProductListview container
        listView = (ListView)mLayoutView.findViewById(R.id.nintendoProductListView);

        // listview cannot be null as the db is pre-filled
        assert listView != null;

        // set the adapter and display on screen
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Cursor cursor = (Cursor)listView.getItemAtPosition(position);

                // Get the item attributes to be sent to details activity from this row in the database.
                String name =  cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description =  cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String file =  cursor.getString(cursor.getColumnIndexOrThrow("file"));
                String status =  cursor.getString(cursor.getColumnIndexOrThrow("status"));
                String rating =  cursor.getString(cursor.getColumnIndexOrThrow("rating"));
                String platform =  cursor.getString(cursor.getColumnIndexOrThrow("platform"));
                String price =  cursor.getString(cursor.getColumnIndexOrThrow("price"));
                int itemId =  cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("file", file);
                intent.putExtra("status", status);
                intent.putExtra("rating", rating);
                intent.putExtra("platform", platform);
                intent.putExtra("price", price);
                intent.putExtra("_id", itemId);
                startActivity(intent);
            }
        });
    }

    private void displayProducts() {

        if(mydb == null){
            // get db
            mydb = new Database(getActivity());

            // mydb.getNintendoProductList(); returns an arraylist
            mArrayList = mydb.getNintendoProductList();

            // used to store just the names - NOT the id....
            ArrayList<String> array_list = new  ArrayList<String>();

            // loop through the returned arraylist from the db
            for (int i=0; i<mArrayList.size(); i++){
                // there are 2 elements for each row being returned - place each row into Pair p
                Pair<Integer, String> p = (Pair<Integer, String>)mArrayList.get(i);
                // array_list can add a string only -> so add in the second of the pair entries - ie, (Pair<Integer, String>)
                array_list.add(p.second);
            }
            // need an adapter to populate the list view = new ArrayAdapter(if inside fragment call getActivity() otherwise "this", layout file for listview (built-in simple_list_item_1), from this "data")
            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array_list);

            // Display the products in the ListView object
            obj = (ListView)mLayoutView.findViewById(R.id.nintendoProductListView);
            obj.setAdapter(arrayAdapter);

            //Toast.makeText(getActivity(), something, Toast.LENGTH_SHORT).show();

        }
    }
}
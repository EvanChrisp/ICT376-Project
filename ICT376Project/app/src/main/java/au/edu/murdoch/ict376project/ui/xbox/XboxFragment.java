package au.edu.murdoch.ict376project.ui.xbox;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.DetailsActivity;
import au.edu.murdoch.ict376project.R;


public class XboxFragment extends Fragment {

    private XboxViewModel xboxViewModel;
    private ListView obj;
    View mLayoutView;
    private Database dbHelper;
    private ListView listView;


    // Database
    Database mydb = null;
    ArrayList mArrayList;  // the list of all products

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        xboxViewModel = ViewModelProviders.of(this).get(XboxViewModel.class);

        mLayoutView = inflater.inflate(R.layout.fragment_xbox, container, false);

        final TextView textView = mLayoutView.findViewById(R.id.text_xbox);

        xboxViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        //displayProducts();
        displayListView();
        //Toast.makeText(getActivity(), "Called displayProducts", Toast.LENGTH_SHORT).show();
    }

    private void displayListView(){

        // get database
        dbHelper = new Database(getActivity());

        // cursor = return from db function
        Cursor cursor = dbHelper.getCursorProducts("Xbox");

        // columns to return
        String[] columns = new String[]{Database.PRODUCT_ID, Database.PRODUCT_NAME, Database.PRODUCT_PLATFORM,};

        // column data goes to this layout (in item_layout.xml) per item
        int[] lvResourceIds = new int[]{R.id.pIdTextView, R.id.pNameTextView, R.id.pPlatformTextView};

        // cursor adapter requires the id to be _id in the database. Please do not change
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(getActivity(),R.layout.item_layout, cursor, columns, lvResourceIds,0);

        // listview - uses the mLayoutView as it is a fragment and not an activity -> listview is displayed in nintendoProductListview container
        listView = (ListView)mLayoutView.findViewById(R.id.xboxProductListView);

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

        if (mydb == null) {
            mydb = new Database(getActivity());

            //mArrayList = mydb.getProductList();
            mArrayList = mydb.getXboxProductList();

            ArrayList<String> array_list = new ArrayList<String>();

            //String something = "";

            for (int i = 0; i < mArrayList.size(); i++) {
                Pair<Integer, String> p = (Pair<Integer, String>) mArrayList.get(i);
                array_list.add(p.second);
                //something = p.second;
            }
            // Put all the contacts in an array
            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array_list);

            // Display the products in the ListView object
            obj = (ListView) mLayoutView.findViewById(R.id.xboxProductListView);
            obj.setAdapter(arrayAdapter);

            //Toast.makeText(getActivity(), something, Toast.LENGTH_SHORT).show();

        }
    }
}

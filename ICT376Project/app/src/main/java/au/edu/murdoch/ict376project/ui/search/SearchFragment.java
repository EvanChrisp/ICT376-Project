package au.edu.murdoch.ict376project.ui.search;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import au.edu.murdoch.ict376project.R;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private ListView obj;
    View mLayoutView;
    private Database dbHelper;
    private ListView listView;
    String searchTerms = "FIFA";

    // Database
    Database mydb = null;
    ArrayList mArrayList;  // the list of all products

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        mLayoutView = inflater.inflate(R.layout.fragment_search, container, false);

        final TextView textView = mLayoutView.findViewById(R.id.text_search);

        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        displayListView();

    }

    private void displayListView(){

        // get database
        dbHelper = new Database(getActivity());

        // cursor = return from db function - hard coded searchTerms ***** - searches for FIFA
        Cursor cursor = dbHelper.getCursorSearchProducts(searchTerms);

        // columns to return
        String[] columns = new String[]{Database.PRODUCT_NAME, Database.PRODUCT_PLATFORM, Database.PRODUCT_DESCRIPTION};

        // column data goes to this layout (in item_layout.xml) per item
        int[] lvResourceIds = new int[]{R.id.pNameTextView, R.id.pPlatformTextView, R.id.pDescriptionTextView};

        // cursor adapter requires the id to be _id in the database. Please do not change
        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(getActivity(),R.layout.item_layout, cursor, columns, lvResourceIds,0);

        // listview - uses the mLayoutView as it is a fragment and not an activity -> listview is displayed in nintendoProductListview container
        listView = (ListView)mLayoutView.findViewById(R.id.searchProductListView);

        // listview cannot be null as the db is pre-filled
        assert listView != null;

        // set the adapter and display on screen
        listView.setAdapter(dataAdapter);
    }
}
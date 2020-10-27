package au.edu.murdoch.ict376project.ui.search;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.DetailsActivity;
import au.edu.murdoch.ict376project.R;

public class SearchFragment extends Fragment
{
    View mLayoutView;
    EditText searchBox;
    Button searchButton;
    public static String str = "Fifa";
    RadioGroup radioGroup;
    int selectedId;
    String platform = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mLayoutView = inflater.inflate(R.layout.fragment_search, container, false);
        searchBox = mLayoutView.findViewById(R.id.searchFragmentEditText);
        radioGroup = mLayoutView.findViewById(R.id.radioGroup);
        searchButton = mLayoutView.findViewById(R.id.searchFragmentButton);
        defaultListView();


        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // listen to clicks on the radiogroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            // do something when the radiobutton is checked
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int position = group.indexOfChild(radioButton);
                selectedId = position;

                switch (position){
                    case 0:
                        platform = "Nintendo";
                        break;
                    case 1:
                        platform = "Playstation";
                        break;
                    case 2:
                        platform = "Xbox";
                        break;
                    case 3:
                        platform = "PC";
                        break;
                }
            }

        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!platform.equals("")){
                    str = searchBox.getText().toString();
                    displayListView(str, platform);
                } else {
                    assert getActivity() != null;
                    Toast.makeText(getActivity().getApplicationContext(), "Please select a platform to search", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void defaultListView()
    {
        // get database
        Database dbHelper = new Database(getActivity());

        // cursor = return from db function - hard coded searchTerms ***** - searches for FIFA
        Cursor cursor = dbHelper.getCursorSearchProductsHome(str);

        // columns to return
        String[] columns = new String[]{Database.PRODUCT_ID, Database.PRODUCT_NAME, Database.PRODUCT_PLATFORM, Database.PRODUCT_FILE};

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
                    assert getActivity() != null;
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
        ListView listView = mLayoutView.findViewById(R.id.searchProductListView);

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

    private void displayListView(String gameTitle, String gamePlatform)
    {
        // get database
        Database dbHelper = new Database(getActivity());

        // cursor = return from db function - hard coded searchTerms ***** - searches for FIFA
        Cursor cursor = dbHelper.getCursorSearchProducts(gameTitle, gamePlatform);

        // columns to return
        String[] columns = new String[]{Database.PRODUCT_ID, Database.PRODUCT_NAME, Database.PRODUCT_PLATFORM, Database.PRODUCT_FILE};

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
                    assert getActivity() != null;
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
        ListView listView = mLayoutView.findViewById(R.id.searchProductListView);

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
}
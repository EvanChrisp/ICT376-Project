package au.edu.murdoch.ict376project.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Random;

import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.DetailsActivity;
import au.edu.murdoch.ict376project.R;
import au.edu.murdoch.ict376project.ui.pc.PCFragment;

public class HomeFragment extends Fragment
{

    EditText searchBox;
    ImageView PSIcon;
    ImageView XboxIcon;
    ImageView SwitchIcon;
    ImageView PCIcon;
    Button searchButton;
    String str;
    //private SearchViewModel homeViewModel;
    private Database db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //homeViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        searchItems();
        loopDeals();
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

    private void setDealImage(ImageView view, Cursor res)
    {
        // name of the entry in column no. (the one called "file") - as a String
        String mFile = res.getString(res.getColumnIndexOrThrow("file"));
        String mDefType = "drawable";
        String mDefPackage = getActivity().getPackageName();

        // resource id = getResources().getIdentifier(String name, String defType, String defPackage);
        int resId = getResources().getIdentifier(mFile, mDefType, mDefPackage);
        // set the view to the relevant id
        view.setImageResource(resId);
    }

    private void setDealName(TextView view, Cursor res)
    {
        String mName = res.getString(res.getColumnIndexOrThrow("name"));
        view.setText(mName);
    }

    private void setDealOldPrice(TextView view, Cursor res)
    {
        String mPrice = res.getString(res.getColumnIndexOrThrow("price"));
        view.setText("$" + mPrice);
        view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void setDealNewPrice(TextView view, Cursor res)
    {
        String mPrice = res.getString(res.getColumnIndexOrThrow("price"));
        double price = Integer.parseInt(mPrice) * 0.8;
        view.setText("$" + (int)price);
    }

    private void loopDeals()
    {
        int i = 1;
        int[] ids = new int[5];
        Random rand = new Random();
        db = new Database(getActivity()); //get db
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
            final Cursor res = db.getProductById(id);

            // String name is the id in the XML - type is "id" for i.d. , package = getActivity().getPackageName()
            ImageView image = getActivity().findViewById(getResources().getIdentifier("deal" + i, "id", getActivity().getPackageName()));
            setDealImage(image, res);

            TextView name = getActivity().findViewById(getResources().getIdentifier("dealtext" + i, "id", getActivity().getPackageName()));
            setDealName(name, res);

            TextView priceOld = getActivity().findViewById(getResources().getIdentifier("dealoldprice" + i, "id", getActivity().getPackageName()));
            setDealOldPrice(priceOld, res);

            TextView priceNew = getActivity().findViewById(getResources().getIdentifier("dealnewprice" + i, "id", getActivity().getPackageName()));
            setDealNewPrice(priceNew, res);

            image.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    String mPrice = res.getString(res.getColumnIndexOrThrow("price"));
                    double mPriceDouble = Integer.parseInt(mPrice) * 0.8;
                    int mPriceInt = (int)mPriceDouble;

                    String name =  res.getString(res.getColumnIndexOrThrow("name"));
                    String description =  res.getString(res.getColumnIndexOrThrow("description"));
                    String file =  res.getString(res.getColumnIndexOrThrow("file"));
                    String status =  res.getString(res.getColumnIndexOrThrow("status"));
                    String rating =  res.getString(res.getColumnIndexOrThrow("rating"));
                    String platform =  res.getString(res.getColumnIndexOrThrow("platform"));
                    String price =  Integer.toString(mPriceInt);
                    int itemId =  res.getInt(res.getColumnIndexOrThrow("_id"));
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

            i++;
        }

        ImageView test = getActivity().findViewById(R.id.imageViewPC);
        test.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Fragment fragment = new PCFragment();
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.contentFragment, fragment);
                transaction.commit();
            }
        });
    }

    private void searchItems() {
        // int view
        searchBox = getActivity().findViewById(R.id.editTextTextPersonName2);
        // set the listener for the searchBox
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void afterTextChanged(Editable editable) {
                str = editable.toString();
            }
        });

        searchButton = getActivity().findViewById(R.id.HomeSearchButton);

        // use str from afterTextChanged()
        str = searchBox.getText().toString();

        // set listener for search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //displayListView();
                Toast.makeText(getActivity(),"You searched for: " +str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void testToast(){
        Toast.makeText(getActivity().getApplicationContext(),"Test toast in onActivity", Toast.LENGTH_SHORT).show();
    }


}

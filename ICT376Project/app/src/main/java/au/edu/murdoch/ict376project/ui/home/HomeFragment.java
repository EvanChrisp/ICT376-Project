package au.edu.murdoch.ict376project.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Random;

import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.DetailsActivity;
import au.edu.murdoch.ict376project.PlatformActivity;
import au.edu.murdoch.ict376project.R;
import au.edu.murdoch.ict376project.ui.search.SearchFragment;

public class HomeFragment extends Fragment
{
    EditText searchBox;
    Button searchButton;
    String str;
    View root;
    private Database db;
    ImageView image;
    TextView name, priceOld, priceNew;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_home, container, false);

        loopDeals();
        loopLatest();
        searchItems();

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        searchItems();
        loopDeals();
        loopLatest();

        ImageView pc = root.findViewById(R.id.imageViewPC);
        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("platform", 1);
                Intent intent = new Intent(requireActivity().getApplicationContext(), PlatformActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        ImageView xbox = root.findViewById(R.id.imageViewXbox);
        xbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("platform", 2);
                Intent intent = new Intent(requireActivity().getApplicationContext(), PlatformActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        ImageView ps = root.findViewById(R.id.imageViewPS);
        ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("platform", 3);
                Intent intent = new Intent(requireActivity().getApplicationContext(), PlatformActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        ImageView nintendo = root.findViewById(R.id.imageViewSwitch);
        nintendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("platform", 4);
                Intent intent = new Intent(requireActivity().getApplicationContext(), PlatformActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
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
        assert getActivity() != null;
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
        view.setText(getString(R.string.priceOld, mPrice));
        view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void setDealNewPrice(TextView view, Cursor res)
    {
        String mPrice = res.getString(res.getColumnIndexOrThrow("price"));
        double price = Integer.parseInt(mPrice) * 0.8;
        view.setText(getString(R.string.priceNew, (int)price));
    }

    private void loopDeals() {
        int i = 1;
        int[] ids = new int[5];
        Random rand = new Random();
        db = new Database(getActivity()); //get db
        // loop through 5 deals
        while(i <= 5)
        {
            int id = rand.nextInt(40 - 1 + 1) + 1;
            if(duplicateDeal(id, ids))
            {
                continue;
            }
            ids[i - 1] = id;

            // get cursor returned from db
            final Cursor res = db.getProductById(id);

            // String name is the id in the XML - type is "id" for i.d. , package = getActivity().getPackageName()
            assert getActivity() != null;
            image = root.findViewById(getResources().getIdentifier("deal" + i, "id", getActivity().getPackageName()));
            setDealImage(image, res);

            name = root.findViewById(getResources().getIdentifier("dealtext" + i, "id", getActivity().getPackageName()));
            setDealName(name, res);

            priceOld = root.findViewById(getResources().getIdentifier("dealoldprice" + i, "id", getActivity().getPackageName()));
            setDealOldPrice(priceOld, res);

            priceNew = root.findViewById(getResources().getIdentifier("dealnewprice" + i, "id", getActivity().getPackageName()));
            setDealNewPrice(priceNew, res);

            image.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    String mPrice = res.getString(res.getColumnIndexOrThrow("price"));
                    double mPriceDouble = Integer.parseInt(mPrice) * 0.8;
                    int mPriceInt = (int)mPriceDouble;

                    String name = res.getString(res.getColumnIndexOrThrow("name"));
                    String description = res.getString(res.getColumnIndexOrThrow("description"));
                    String file = res.getString(res.getColumnIndexOrThrow("file"));
                    String status = Integer.toString(2);
                    String rating = res.getString(res.getColumnIndexOrThrow("rating"));
                    String platform = res.getString(res.getColumnIndexOrThrow("platform"));
                    String price = Integer.toString(mPriceInt);
                    int itemId = res.getInt(res.getColumnIndexOrThrow("_id"));
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

        db.close();
    }

    private void loopLatest()
    {
        int i = 1;
        //Database db = new Database(getActivity()); //get db
        int j = 10;

        while(i <= 4)
        {
            // get cursor returned from db
            final Cursor res = db.getProductById(j);

            // String name is the id in the XML - type is "id" for i.d. , package = getActivity().getPackageName()
            assert getActivity() != null;
            ImageView image2 = root.findViewById(getResources().getIdentifier("latest" + i, "id", getActivity().getPackageName()));
            setDealImage(image2, res);

            TextView name = root.findViewById(getResources().getIdentifier("latesttext" + i, "id", getActivity().getPackageName()));
            setDealName(name, res);

            image2.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    String name =  res.getString(res.getColumnIndexOrThrow("name"));
                    String description =  res.getString(res.getColumnIndexOrThrow("description"));
                    String file =  res.getString(res.getColumnIndexOrThrow("file"));
                    String status =  res.getString(res.getColumnIndexOrThrow("status"));
                    String rating =  res.getString(res.getColumnIndexOrThrow("rating"));
                    String platform =  res.getString(res.getColumnIndexOrThrow("platform"));
                    String price = res.getString(res.getColumnIndexOrThrow("price"));
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
            j+=10;
        }
        db.close();
    }

    private void searchItems() {
        // int view
        searchBox = root.findViewById(R.id.search);
        searchButton = root.findViewById(R.id.button11);

        // use str from afterTextChanged()
        str = searchBox.getText().toString();

        // set listener for search button
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Fragment mSearch = new SearchFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                SearchFragment.str = searchBox.getText().toString();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, mSearch).addToBackStack(null).commit();
            }
        });
    }
}

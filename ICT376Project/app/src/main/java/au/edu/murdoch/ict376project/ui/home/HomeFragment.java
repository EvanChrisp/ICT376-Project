package au.edu.murdoch.ict376project.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Random;

import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.DetailsActivity;
import au.edu.murdoch.ict376project.R;
import au.edu.murdoch.ict376project.ui.pc.PCFragment;

public class HomeFragment extends Fragment
{
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        int i = 1;
        int[] ids = new int[5];
        Random rand = new Random();
        Database db = new Database(getActivity()); //get db
         Button search = (Button) root.findViewById(R.id.HomeSearchButton);
        ImageView PSIcon=(ImageView) root.findViewById(R.id.imageViewPS);
        ImageView XboxIcon=(ImageView) root.findViewById(R.id.imageViewXbox);
        ImageView SwitchIcon=(ImageView) root.findViewById(R.id.imageViewSwitch);
        ImageView PCIcon=(ImageView) root.findViewById(R.id.imageViewPC);

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
            ImageView image = root.findViewById(getResources().getIdentifier("deal" + i, "id", getActivity().getPackageName()));
            setDealImage(image, res);

            TextView name = root.findViewById(getResources().getIdentifier("dealtext" + i, "id", getActivity().getPackageName()));
            setDealName(name, res);

            TextView priceOld = root.findViewById(getResources().getIdentifier("dealoldprice" + i, "id", getActivity().getPackageName()));
            setDealOldPrice(priceOld, res);

            TextView priceNew = root.findViewById(getResources().getIdentifier("dealnewprice" + i, "id", getActivity().getPackageName()));
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
}

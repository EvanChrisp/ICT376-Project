package au.edu.murdoch.ict376project.ui.home;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Random;

import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.DetailsActivity;
import au.edu.murdoch.ict376project.R;

public class HomeFragment extends Fragment
{
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        int i = 1;
        int[] ids = new int[5];
        Random rand = new Random();
        Database db = new Database(getActivity()); //get db

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

            // String name is the id in the XML - type is "id" for i.d. , package = getActivity().getPackageName()
            ImageView image = root.findViewById(getResources().getIdentifier("deal" + i, "id", getActivity().getPackageName()));
            setDealImage(image, res);

            TextView name = root.findViewById(getResources().getIdentifier("dealtext" + i, "id", getActivity().getPackageName()));
            setDealName(name, res);

            TextView priceOld = root.findViewById(getResources().getIdentifier("dealoldprice" + i, "id", getActivity().getPackageName()));
            setDealOldPrice(priceOld, res);

            TextView priceNew = root.findViewById(getResources().getIdentifier("dealnewprice" + i, "id", getActivity().getPackageName()));
            setDealNewPrice(priceNew, res);

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
        String mFile = res.getString(res.getColumnIndex("file"));
        String mDefType = "drawable";
        String mDefPackage = getActivity().getPackageName();

        // resource id = getResources().getIdentifier(String name, String defType, String defPackage);
        int resId = getResources().getIdentifier(mFile, mDefType, mDefPackage);
        // set the view to the relevant id
        view.setImageResource(resId);
    }

    private void setDealName(TextView view, Cursor res)
    {
        String mName = res.getString(res.getColumnIndex("name"));
        view.setText(mName);
    }

    private void setDealOldPrice(TextView view, Cursor res)
    {
        String mPrice = res.getString(res.getColumnIndex("price"));
        view.setText("$" + mPrice);
        view.setPaintFlags(view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void setDealNewPrice(TextView view, Cursor res)
    {
        String mPrice = res.getString(res.getColumnIndex("price"));
        double price = Integer.parseInt(mPrice) * 0.8;
        view.setText("$" + (int)price);
    }
}
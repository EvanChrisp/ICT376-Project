package au.edu.murdoch.ict376project.ui.pc;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import au.edu.murdoch.ict376project.Database;
import au.edu.murdoch.ict376project.R;

public class PCFragment extends Fragment {

    private PCViewModel pcViewModel;
    private ListView obj;
    View mLayoutView;

    // Database
    Database mydb = null;
    ArrayList mArrayList;  // the list of all products

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pcViewModel = ViewModelProviders.of(this).get(PCViewModel.class);

        mLayoutView = inflater.inflate(R.layout.fragment_pc, container, false);

        final TextView textView = mLayoutView.findViewById(R.id.text_pc);
        pcViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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

        displayProducts();
        //Toast.makeText(getActivity(), "Called displayProducts", Toast.LENGTH_SHORT).show();
    }

    private void displayProducts() {

        if (mydb == null) {
            mydb = new Database(getActivity());

            //mArrayList = mydb.getProductList();
            mArrayList = mydb.getPCProductList();

            ArrayList<String> array_list = new ArrayList<String>();

            //String something = " ";

            for (int i = 0; i < mArrayList.size(); i++) {
                Pair<Integer, String> p = (Pair<Integer, String>) mArrayList.get(i);
                array_list.add(p.second);
                //something = p.second;
            }
            // Put all the contacts in an array
            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array_list);

            // Display the products in the ListView object
            obj = (ListView) mLayoutView.findViewById(R.id.pcProductListView);
            obj.setAdapter(arrayAdapter);

            //Toast.makeText(getActivity(), something, Toast.LENGTH_SHORT).show();

        }
    }
}


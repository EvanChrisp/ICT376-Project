package au.edu.murdoch.ict376project.ui.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import au.edu.murdoch.ict376project.DirectionsActivity;
import au.edu.murdoch.ict376project.R;

public class ContactFragment extends Fragment
{
    View mLayoutView;
    Button gMaps;
    TextView emailUs, number, address1, address2;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mLayoutView = inflater.inflate(R.layout.fragment_contact, container, false);
        gMaps = (Button)mLayoutView.findViewById(R.id.gMaps);
        emailUs = (TextView)mLayoutView.findViewById(R.id.Email);
        number = (TextView)mLayoutView.findViewById(R.id.Number);
        address1 = (TextView)mLayoutView.findViewById(R.id.address1);
        address2 = (TextView)mLayoutView.findViewById(R.id.address2);

        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        emailUs.setText(Html.fromHtml("<a href=\"mailto:erecustomerhelp@eregames.com.au\">erecustomerhelp@eregames.com.au </a>"));
        emailUs.setMovementMethod(LinkMovementMethod.getInstance());

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: +61892587049"));
                startActivity(callIntent);

            }
        });

        gMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DirectionsActivity.class);
                startActivity(intent);
            }
        });

        address1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DirectionsActivity.class);
                startActivity(intent);
            }
        });

        address2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DirectionsActivity.class);
                startActivity(intent);
            }
        });
    }

}
package au.edu.murdoch.ict376project.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import au.edu.murdoch.ict376project.R;
import au.edu.murdoch.ict376project.ui.contact.ContactViewModel;
import au.edu.murdoch.ict376project.ui.playstation.PlaystationFragment;

public class NewsFragment extends Fragment {

    public static NewsFragment newInstance(){

        NewsFragment newsf = new NewsFragment();

        return newsf;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // int the View
        View mLayoutView = inflater.inflate(R.layout.fragment_news, container, false);

        // int view using View NB. you need permissions to access internet - go to manifest file
        WebView webView = (WebView)mLayoutView.findViewById(R.id.webView1);
        // do something
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(getActivity());
        webView.setWebViewClient(webViewClient);

        webView.loadUrl("https://www.ign.com");

        return mLayoutView;
    }


}
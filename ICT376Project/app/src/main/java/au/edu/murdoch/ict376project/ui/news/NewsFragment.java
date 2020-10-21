package au.edu.murdoch.ict376project.ui.news;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import au.edu.murdoch.ict376project.R;

public class NewsFragment extends Fragment
{
    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // int the View
        View mLayoutView = inflater.inflate(R.layout.fragment_news, container, false);

        // int view using View NB. you need permissions to access internet - go to manifest file
        WebView webView = mLayoutView.findViewById(R.id.webView1);
        // do something
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(getActivity());
        webView.setWebViewClient(webViewClient);

        webView.loadUrl("https://www.ign.com");

        return mLayoutView;

    }


}
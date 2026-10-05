package com.nilampublication.pulaukata;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String HOME_URL = "https://pulau-kata-year-one-adventure.teaservideo1.chatgpt.site/";
    private WebView webView;
    private ProgressBar loadingIndicator;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.web_view);
        loadingIndicator = findViewById(R.id.loading_indicator);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new AppWebViewClient());
        webView.loadUrl(HOME_URL);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView.canGoBack()) webView.goBack(); else finish();
            }
        });
    }

    private class AppWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            loadingIndicator.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            loadingIndicator.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, android.webkit.WebResourceError error) {
            if (request.isForMainFrame()) {
                loadingIndicator.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Could not load Pulau Kata. Check your internet connection and try again.", Toast.LENGTH_LONG).show();
            }
        }
    }
}

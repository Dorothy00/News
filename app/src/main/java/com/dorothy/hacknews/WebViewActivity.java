package com.dorothy.hacknews;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;
    private String url;
    private HackProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mDialog = new HackProgressDialog(this);
showDialog();
        url = getIntent().getStringExtra("url");
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("--------", "pagestart----");
                showDialog();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                dismissDialog();
                Log.d("--------","page end------");
                super.onPageFinished(view, url);
            }
        });
        mWebView.loadUrl(url);
    }

    protected void showDialog() {
        if (mDialog != null ) {
            mDialog.show();
        }
    }


    protected void dismissDialog() {
        if (mDialog != null ) {
            mDialog.dismiss();
        }
    }
}

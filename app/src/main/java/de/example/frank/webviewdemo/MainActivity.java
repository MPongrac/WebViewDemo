package de.example.frank.webviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button prev, next;
    private EditText editText;
    private WebView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prev = (Button) findViewById(R.id.prev);
        next = (Button) findViewById(R.id.next);
        editText = (EditText) findViewById(R.id.editText) ;
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.goBack();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.goForward();
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                view.loadUrl(v.getText().toString());
                return true;
            }
        });
        view = (WebView)findViewById(R.id.webview);
        view.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                updateNavBar();
            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
               updateNavBar();
            }
        });
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().setUseWideViewPort(true);
        if(savedInstanceState!=null){
            view.restoreState(savedInstanceState);

        }else{
            view.loadUrl("https://google.de");
        }
    }
    protected void onSaveInstanceState(Bundle state){
        view.saveState(state);
    }

    private void updateNavBar() {
        prev.setEnabled(view.canGoBack());
        next.setEnabled(view.canGoForward());
        editText.setText(view.getUrl());
    }

}

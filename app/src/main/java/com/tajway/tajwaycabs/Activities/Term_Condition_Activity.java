package com.tajway.tajwaycabs.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.tajway.tajwaycabs.R;

public class Term_Condition_Activity extends AppCompatActivity {
    View view;
    WebView webView;


   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_term__condition_, container, false);*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_term__condition_);
        getSupportActionBar().setTitle("Terms and Condition");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    webView=findViewById(R.id.webView);
        webView.loadUrl("http://tajtripcars.appoffice.xyz/api/terms-condition");


       // return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
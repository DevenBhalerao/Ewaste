package com.example.dev_n.ewaste.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidBarcodeView view = new AndroidBarcodeView(this);
        setContentView(view);
    }

}

package com.example.dev_n.ewaste;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Canvas;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.AndroidFont;
import com.onbarcode.barcode.android.Code39;
import com.onbarcode.barcode.android.GeneratedBarcodeInfo;
import com.onbarcode.barcode.android.IBarcode;

public class BarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidBarcodeView view = new AndroidBarcodeView(this);
        setContentView(view);
    }

}

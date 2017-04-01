package com.example.dev_n.ewaste.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev_n.ewaste.R;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView addressView;
    private TextView contactNoView;
    private TextView itemsCountView;
    private TextView approxWeightView;
    private Button confirmButton;
    private Button rejectButton;

    private static int CAMERA_PERMISSION;

    private Toolbar mToolbar;

    private DrawerLayout drawerLayout;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        addressView = (TextView) findViewById(R.id.order_address_field);
        contactNoView = (TextView) findViewById(R.id.order_contact_field);
        itemsCountView = (TextView) findViewById(R.id.order_item_count_field);
        approxWeightView = (TextView) findViewById(R.id.order_approx_weight_field);

        confirmButton = (Button) findViewById(R.id.confirm_order_bt);
        rejectButton = (Button) findViewById(R.id.order_reject_bt);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Order Details");


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                } else {
                    Intent intent = new Intent(context, OrderActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(this, OrderActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }


}
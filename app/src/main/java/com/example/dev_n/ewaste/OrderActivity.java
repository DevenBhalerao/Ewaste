package com.example.dev_n.ewaste;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.support.annotation.IntegerRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.dev_n.ewaste.R.id.toolbar;

public class OrderActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private ArrayList<OrderData> al;
    private HashMap<Integer, String> IdMap;
    private static final String TAG = "OrderActivity";

    private Toolbar mToolbar;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar = (Toolbar) findViewById(toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Order Details");


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        OrderData o1 = new OrderData("1", "2", "frifge", "fridge");
        OrderData o2 = new OrderData("1", "2", "frifge", "fridge");
        OrderData o3 = new OrderData("1", "2", "frifge", "fridge");
        OrderData o4 = new OrderData("1", "2", "frifge", "fridge");

        al = new ArrayList<>();
        al.add(o1);
        al.add(o2);
        al.add(o3);
        al.add(o4);


        Bundle IdData = getIntent().getExtras();
        if (IdData != null) {
            IdMap = new HashMap<>();
            int selectedOrderPosition = 0;
            for (int i = 0; i < al.size(); i++) {
                Log.d(TAG, "id data for " + i + " is " + IdData.getString("scannedID" + i));
                if (IdData.getString("scannedID" + i) != null) {
                    selectedOrderPosition = i;
                }
            }

            al.get(selectedOrderPosition).setOrderId(IdData.getString("scannedID" + selectedOrderPosition));

        } else {
            Log.d(TAG, "data is nul");
        }

        // specify an adapter (see also next example)
        mAdapter = new OrderAdapter(al, this);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == OrderAdapter.CAMERA_REQUEST) {

            if (resultCode == RESULT_OK) {
                al.get(Integer.parseInt(data.getExtras().getString("orderPosition"))).setOrderId(data.getExtras().getString("result"));
                mAdapter = new OrderAdapter(al, getApplication().getBaseContext());
                mRecyclerView.setAdapter(mAdapter);


            }
        }
    }


}
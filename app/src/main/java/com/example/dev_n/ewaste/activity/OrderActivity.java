package com.example.dev_n.ewaste.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dev_n.ewaste.R;
import com.example.dev_n.ewaste.Volley.LoginRequest;
import com.example.dev_n.ewaste.Volley.OrderSend;
import com.example.dev_n.ewaste.app.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private Button confirmBt;

    private SharedPreferences pref;

    private ArrayList<OrderData> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        pref = getApplicationContext().getSharedPreferences(RequestActivity.MyPREFERENCES, 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        int pos = getIntent().getIntExtra("position", 0);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar = (Toolbar) findViewById(toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Order Details");


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        confirmBt = (Button) findViewById(R.id.confirm_bt);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        Gson gson = new GsonBuilder().create();
        ArrayList<RequestData> list = gson.fromJson(pref.getString("request_data", null), new TypeToken<ArrayList<RequestData>>() {}.getType());
        RequestData data = list.get(pos);
        items = data.getOrderItems();

        Bundle IdData = getIntent().getExtras();
        if (IdData != null) {
            IdMap = new HashMap<>();
            int selectedOrderPosition = 0;
            for (int i = 0; i < items.size(); i++) {
                Log.d(TAG, "id data for " + i + " is " + IdData.getString("scannedID" + i));
                if (IdData.getString("scannedID" + i) != null) {
                    selectedOrderPosition = i;
                }
            }

            items.get(selectedOrderPosition).setOrderId(IdData.getString("scannedID" + selectedOrderPosition));
            data.setOrderItems(items);
            list.set(pos, data);
            Type type = new TypeToken<ArrayList<RequestData>>() {
            }.getType();
            String json = gson.toJson(list, type);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("request_data", json);
            editor.apply();




        } else {
            Log.d(TAG, "data is nul");
        }

        // specify an adapter (see also next example)
        mAdapter = new OrderAdapter(items, pos,this);
        mRecyclerView.setAdapter(mAdapter);


        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getApplicationContext().getSharedPreferences(RequestActivity.MyPREFERENCES, 0);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.e(TAG, response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                String collectorID = jsonResponse.getString("collector_id");
                                SharedPreferences pref = getApplicationContext().getSharedPreferences(RequestActivity.MyPREFERENCES, 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("collector_id", collectorID);
                                editor.apply();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                String userid = "12345";

                String collectorID = pref.getString("collector_id", null);


                OrderSend loginRequest = new OrderSend(userid, collectorID, items, responseListener);

                RequestQueue queue = Volley.newRequestQueue(OrderActivity.this);
                queue.add(loginRequest);
            }
        });
    }




}
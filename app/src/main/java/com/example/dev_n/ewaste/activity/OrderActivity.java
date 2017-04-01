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

import org.json.JSONException;
import org.json.JSONObject;

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

        confirmBt = (Button) findViewById(R.id.confirm_bt);

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


                OrderSend loginRequest = new OrderSend(userid, collectorID, al, responseListener);

                RequestQueue queue = Volley.newRequestQueue(OrderActivity.this);
                queue.add(loginRequest);
            }
        });
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
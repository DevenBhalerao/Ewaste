package com.example.dev_n.ewaste.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dev_n.ewaste.R;
import com.example.dev_n.ewaste.Volley.RequestFetch;
import com.example.dev_n.ewaste.app.Config;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context = this;

    private static final String TAG = RequestActivity.class.getSimpleName();

    ArrayList<RequestData> requestDatas;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences pref = getApplicationContext().getSharedPreferences(RequestActivity.MyPREFERENCES, 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pending Requests");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                }
            }
        };
        displayFirebaseRegId();


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    int count = 0;
                    JSONObject jsonResponse = new JSONObject(response);
                    requestDatas = new ArrayList<>();
                    if (((String) jsonResponse.getString("success")).equals("true")) {
                        while (jsonResponse.getJSONObject(Integer.toString(count)) != null && !(jsonResponse.getJSONObject(Integer.toString(count)).getString("success")).equals(true)) {
                            JSONObject jobject = jsonResponse.getJSONObject(Integer.toString(count));

                            Log.e(TAG, "hmm " + jobject.toString());

                            RequestData requestObject = new RequestData();
                            requestObject.setAddress(jobject.getString("address"));
                            requestObject.setContactNo(jobject.getString("contact_no"));
                            requestObject.setItemCOunt(jobject.getString("count"));
                            JSONArray arra = new JSONArray(jobject.getString("products"));
                            ArrayList<OrderData> productList = new ArrayList<>();
                            for (int i = 0; i < arra.length(); i++) {
                                OrderData o = new OrderData();
                                o.setOrderId("NOT SET");
                                o.setOrderType(arra.getJSONObject(i).getString("product_type"));
                                o.setOrderCount(arra.getJSONObject(i).getString("quantity"));
                                productList.add(o);
                                Log.e(TAG, " " + i);
                            }
                            requestObject.setOrderItems(productList);
                            requestDatas.add(requestObject);
                            Log.e(TAG, " lol" + requestDatas.size());


//                        mRecyclerView = (RecyclerView) findViewById(R.id.request_recycler_view);

                            // use this setting to improve performance if you know that changes
                            // in content do not change the layout size of the RecyclerView
//                        mRecyclerView.setHasFixedSize(true);
//
//                        mAdapter = new RequestAdapter(requestDatas, context);
//                        mRecyclerView.setAdapter(mAdapter);
//
//                        // use a linear layout manager
//                        mLayoutManager = new LinearLayoutManager(context);
//                        mRecyclerView.setLayoutManager(mLayoutManager);
//                        mRecyclerView.setHasFixedSize(true);
//                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            count++;
                            Log.e(TAG, "count" + count);
                            Gson gson = new Gson();
                            Type type = new TypeToken<ArrayList<RequestData>>() {
                            }.getType();
                            String json = gson.toJson(requestDatas, type);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("request_data", json);
                            editor.apply();
                            Log.e(TAG, json);

                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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


        String collectorId = pref.getString("collector_id", null);
        //RequestFetch productRequest = new RequestFetch(collectorId, responseListener);
        RequestFetch productRequest = new RequestFetch("31", responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(productRequest);


        RequestData rd = new RequestData("1", "sion", "20kg");
        RequestData rd1 = new RequestData("2", "sion", "20kg");
        RequestData rd3 = new RequestData("3", "sion", "20kg");
        RequestData rd4 = new RequestData("4", "sion", "20kg");
        RequestData rd5 = new RequestData("5", "sion", "20kg");

        ArrayList<RequestData> al = new ArrayList<>();
        al.add(rd);
        al.add(rd1);
        al.add(rd3);
        al.add(rd4);
        al.add(rd5);


        mRecyclerView = (RecyclerView) findViewById(R.id.request_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RequestAdapter(al, context);
        mRecyclerView.setAdapter(mAdapter);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pending_request) {
            // Handle the camera action
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_completed_requests) {
            Intent i1 = new Intent(context, CompletedRequests.class);
            startActivity(i1);
        } else if (id == R.id.nav_rejected_requests) {
            Intent i = new Intent(context, RejectedRequests.class);
            startActivity(i);
        } else if (id == R.id.nav_log_in) {
            Intent i = new Intent(context, LoginActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            //TODO sharing intent
        } else if (id == R.id.nav_send) {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.example.dev_n.ewaste.Volley;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.dev_n.ewaste.activity.OrderData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestFetch extends StringRequest {

    private String TAG = RequestFetch.class.getSimpleName();
    private static final String ORDER_REQUEST_URL = "http://smartewaste-com.stackstaging.com/Foobar404Gov/api/forwardrequest.php";
    private Map<String, String> params;

    public RequestFetch(String collectorID, Response.Listener<String> listener) {
        super(Method.POST, ORDER_REQUEST_URL, listener, null);
        Log.e(TAG, collectorID);
        params = new HashMap<>();
        params.put("collector_id", collectorID);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }

}



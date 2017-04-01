package com.example.dev_n.ewaste.Volley;

/**
 * Created by Dev_N on 01-04-2017.
 */

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.dev_n.ewaste.activity.OrderData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderSend extends StringRequest {

    private static final String ORDER_REQUEST_URL = "http://smartewaste-com.stackstaging.com/Foobar404Gov/api/recieve-product-details.php";
    private Map<String, String> params;
    private String TAG = OrderSend.class.getSimpleName();

    public OrderSend(String userid, String collectorID, ArrayList<OrderData> orderList,Response.Listener<String> listener) {
        super(Method.POST, ORDER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("collector_id", collectorID);
        params.put("count", Integer.toString(orderList.size()));


        for (int i = 0; i < orderList.size(); i++) {

            params.put( Integer.toString(i) , orderList.get(i).getOrderCount() + "," + orderList.get(i).getOrderType()+ ", " +orderList.get(i).getOrderId());
        }


        for (String key : params.keySet()) {
            Log.e(TAG, key + " " + params.get(key));
        }
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }

}



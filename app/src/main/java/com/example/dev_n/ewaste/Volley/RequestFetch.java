package com.example.dev_n.ewaste.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestFetch extends StringRequest {

    private static final String ORDER_REQUEST_URL = "http://dietnlss.000webhostapp.com/Login.php";
    private Map<String, String> params;

    public RequestFetch(String collectorID, Response.Listener<String> listener) {
        super(Method.POST, ORDER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("collectorID", collectorID);
    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }

}



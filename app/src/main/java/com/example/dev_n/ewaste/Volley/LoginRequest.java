package com.example.dev_n.ewaste.Volley;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://smartewaste-com.stackstaging.com/Foobar404Gov/api/collector-login.php";
    private Map<String, String> params;
    private String TAG = LoginRequest.class.getSimpleName();

    public LoginRequest(String username,  String password, String deviceID, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);
        params.put("deviceID", deviceID);

    }

    @Override
    public Map<String, String> getParams() {

        return params;
    }

}

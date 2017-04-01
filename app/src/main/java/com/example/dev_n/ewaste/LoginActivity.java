package com.example.dev_n.ewaste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.dev_n.ewaste.Volley.LoginRequest;
import com.example.dev_n.ewaste.app.Config;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText emailTextView;
    private EditText passwordTextView;
    private Button submitButton;
    private String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailTextView = (EditText) findViewById(R.id.your_email_address);
        passwordTextView = (EditText) findViewById(R.id.create_new_password);

        submitButton = (Button) findViewById(R.id.submit_login_bt);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = emailTextView.getText().toString();
                final String password = passwordTextView.getText().toString();
                SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                String regId = pref.getString("regId", null);
                final String deviceID = regId;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                String collectorID = jsonResponse.getString("collector_id");
                                SharedPreferences pref = getApplicationContext().getSharedPreferences(RequestActivity.MyPREFERENCES, 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("collector_id", collectorID);
                                editor.apply();
                                Log.e(TAG, response);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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


                LoginRequest loginRequest = new LoginRequest(username, password, deviceID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}

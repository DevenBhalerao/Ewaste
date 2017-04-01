package com.example.dev_n.ewaste.activity;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dev_n.ewaste.R;

import static com.example.dev_n.ewaste.R.id.toolbar;

public class RegisterActivity extends AppCompatActivity {


    private EditText nameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText contactField;
    private EditText areaField;
    private Button submitButton;
    private Context context = this;


    private Toolbar mToolbar;

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = (Toolbar) findViewById(toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Pending Requests");

        nameField = (EditText) findViewById(R.id.register_name_field);
        emailField = (EditText) findViewById(R.id.register_email_field);
        passwordField = (EditText) findViewById(R.id.register_password_field);
        confirmPasswordField = (EditText) findViewById(R.id.register_password_confirm_field);
        contactField = (EditText) findViewById(R.id.register_contact_no_field);
        areaField = (EditText) findViewById(R.id.register_area_field);

        submitButton = (Button) findViewById(R.id.register_submit_bt);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordField.getText().equals(confirmPasswordField.getText())){

                }
                else{
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


}

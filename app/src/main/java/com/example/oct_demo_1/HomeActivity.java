package com.example.oct_demo_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    public void Company(View view) {
        startActivity(new Intent(getApplicationContext(), Company_view.class));
        Toast.makeText(this, "All company details", Toast.LENGTH_SHORT).show();

    }
}
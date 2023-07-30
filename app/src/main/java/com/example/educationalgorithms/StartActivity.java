package com.example.educationalgorithms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void ifStart(View view) {
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
}

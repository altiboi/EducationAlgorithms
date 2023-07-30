package com.example.educationalgorithms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Retrieve the APS scores from the Intent
        int apsWits = getIntent().getIntExtra("apsWits", 0);
        int apsUKZN = getIntent().getIntExtra("apsUKZN", 0);
        int apsAPS = getIntent().getIntExtra("apsAPS", 0);
        int apsUWC = getIntent().getIntExtra("apsUWC", 0);

        // Display the APS scores on TextViews or use them as needed
        /*TextView textViewApsWits = findViewById(R.id.textViewApsWits);
        TextView textViewApsUKZN = findViewById(R.id.textViewApsUKZN);
        TextView textViewApsAPS = findViewById(R.id.textViewApsAPS);
        TextView textViewApsUWC = findViewById(R.id.textViewApsUWC);*

        textViewApsWits.setText("APS at Wits: " + apsWits);
        textViewApsUKZN.setText("APS at UKZN: " + apsUKZN);
        textViewApsAPS.setText("APS at APS: " + apsAPS);
        textViewApsUWC.setText("APS at UWC: " + apsUWC);*/
    }

    public void ifAps(View view) {
        Intent intent= new Intent(this, MarksActivity.class);
        startActivity(intent);
    }



}
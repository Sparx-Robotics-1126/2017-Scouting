package com.sparx1126.steamworks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Amanda on 1/17/17.
 */

public class BenchmarkAutoScreen extends AppCompatActivity {

    Button teleopSwitcher;
    ImageButton home_auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarkauto);

        home_auto = (ImageButton)findViewById(R.id.home_auto);
        home_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_autoButtonClicked(v);
            }
        });

        teleopSwitcher = (Button)findViewById(R.id.teleopSwitcher);
        teleopSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teleopSwitcherButtonClicked(v);
            }
        });
    }
    private void teleopSwitcherButtonClicked(View v) {
        Context context = BenchmarkAutoScreen.this;
        Class destination = BenchmarkTeleopScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }
    private void home_autoButtonClicked(View v) {
        Context context = BenchmarkAutoScreen.this;
        Class destination = MainScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }

}
package com.sparx1126.steamworks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class BenchmarkTeleopScreen extends AppCompatActivity {

    Button benchmarkAutoSwitcher;
    ImageButton home_teleop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarkteleop);

        home_teleop = (ImageButton)findViewById(R.id.home_teleop);
        home_teleop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_teleopImageButtonClicked(v);
            }
        });

        benchmarkAutoSwitcher = (Button)findViewById(R.id.benchmarkAutoSwitcher);
        benchmarkAutoSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                benchmarkAutoSwitcherButtonClicked(v);
            }
        });
    }

    private void home_teleopImageButtonClicked(View v) {
        Context context = BenchmarkTeleopScreen.this;
        Class destination = MainScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }

    private void benchmarkAutoSwitcherButtonClicked(View v) {
        Context context = BenchmarkTeleopScreen.this;
        Class destination = BenchmarkAutoScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }

}
package com.sparx1126.steamworks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class BenchmarkTeleopSwitcher extends AppCompatActivity {

    Button benchmarkAutoSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarkteleop);

        benchmarkAutoSwitcher = (Button)findViewById(R.id.benchmarkAutoSwitcher);
        benchmarkAutoSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                benchmarkAutoSwitcherButtonClicked(v);
            }
        });
    }

    private void benchmarkAutoSwitcherButtonClicked(View v) {
        Context context = BenchmarkTeleopSwitcher.this;
        Class destination = BenchmarkAuto.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }

}

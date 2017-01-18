package com.sparx1126.steamworks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Amanda on 1/17/17.
 */

public class BenchmarkAuto extends AppCompatActivity {

    Button teleopSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.benchmarkauto);

        teleopSwitcher = (Button)findViewById(R.id.teleopSwitcher);
        teleopSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teleopSwitcherButtonClicked(v);
            }
        });
    }
    private void teleopSwitcherButtonClicked(View v) {
        Context context = BenchmarkAuto.this;
        Class destination = BenchmarkTeleopSwitcher.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }

}

package com.sparx1126.steamworks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

import static com.sparx1126.steamworks.R.layout.scouting;

public class ScoutingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(scouting);
        ImageButton home_scouting = (ImageButton) findViewById(R.id.home_scouting);
        home_scouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
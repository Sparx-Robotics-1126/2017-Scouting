package com.sparx1126.steamworks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import static com.sparx1126.steamworks.R.layout.scouting;

/**
 * Created by Amanda on 1/21/17.
 */

public class ScoutingScreen extends AppCompatActivity {
    ImageButton home_scouting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(scouting);
        home_scouting = (ImageButton)findViewById(R.id.home_scouting);
        home_scouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_scoutingButtonClicked(v);
            }
        });
    }

    private void home_scoutingButtonClicked(View v) {
        Context context = ScoutingScreen.this;
        Class destination = MainScreen.class;
        Intent intent = new Intent(context, destination);
        startActivity(intent);
    }

}
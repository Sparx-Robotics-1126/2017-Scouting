package com.sparx1126.steamworks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ToggleButton;

public class AllianceScreen extends AppCompatActivity {
    private Button selectStuff;
    private SharedPreferences settings;
    private ToggleButton blueSelectedToggle;
    private RadioButton team1;
    private RadioButton team2;
    private RadioButton team3;
    private int teamSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alliance_selection_screen);

        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);

        if(settings.getInt("team selected", 0) != 0){
            Intent intent = new Intent(AllianceScreen.this, MainScreen.class);
            startActivity(intent);
        }
        else {
            selectStuff = (Button) findViewById(R.id.selectStuff);
            selectStuff.setOnClickListener(selectStuffClicked);
            selectStuff.setVisibility(View.INVISIBLE);
            blueSelectedToggle = (ToggleButton) findViewById(R.id.blueSelectedToggle);
            team1 = (RadioButton) findViewById(R.id.team1);
            team1.setOnClickListener(teamListener);
            team2 = (RadioButton) findViewById(R.id.team2);
            team2.setOnClickListener(teamListener);
            team3 = (RadioButton) findViewById(R.id.team3);
            team3.setOnClickListener(teamListener);
        }
    }

    private final View.OnClickListener teamListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            teamSelected = 0;
            if (team1.isChecked()) {
                teamSelected = 1;
            } else if (team2.isChecked()) {
                teamSelected = 2;
            } else if (team3.isChecked()) {
                teamSelected = 3;
            }

            if (teamSelected != 0) {
                selectStuff.setVisibility(View.VISIBLE);
            }
            else {
                selectStuff.setVisibility(View.INVISIBLE);
            }
        }
    };

    private final View.OnClickListener selectStuffClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("red alliance", !blueSelectedToggle.isChecked());
            editor.putInt("team selected", teamSelected);
            editor.apply();

            Intent intent = new Intent(AllianceScreen.this, MainScreen.class);
            startActivity(intent);
        }
    };
}

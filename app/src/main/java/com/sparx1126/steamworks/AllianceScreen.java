package com.sparx1126.steamworks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ToggleButton;

/**
 * Created by KBates on 3/20/2017.
 */

public class AllianceScreen extends AppCompatActivity {
    private Button selectStuff;
    private SharedPreferences settings;
    private ToggleButton allianceColor;
    private boolean redAlliance = true;
    private RadioButton team1;
    private RadioButton team2;
    private RadioButton team3;
    private int teamSelected;
    private boolean isTeamSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alliance_selection_screen);
        selectStuff = (Button) findViewById(R.id.button2);
        selectStuff.setOnClickListener(buttonClicked);
        selectStuff.setVisibility(View.INVISIBLE);
        allianceColor = (ToggleButton) findViewById(R.id.toggleButton);
        team1 = (RadioButton) findViewById(R.id.team1);
        team1.setOnClickListener(teamListener);
        team2 = (RadioButton) findViewById(R.id.team2);
        team2.setOnClickListener(teamListener);
        team3 = (RadioButton) findViewById(R.id.team3);
        team3.setOnClickListener(teamListener);
        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);


    }
    private final View.OnClickListener teamListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (team1.isChecked() == (true)) {
                teamSelected = 1;
                isTeamSelected = true;
            } else if (team2.isChecked() == (true)) {
                teamSelected = 2;
                isTeamSelected = true;
            } else if (team3.isChecked() == (true)) {
                teamSelected = 3;
                isTeamSelected = true;
            } else {
                isTeamSelected = false;
            }

            if (isTeamSelected) {
                selectStuff.setVisibility(View.VISIBLE);
            }
            else if(!isTeamSelected){
                selectStuff.setVisibility(View.INVISIBLE);
            }
        }

        };

    private final View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(allianceColor.isChecked()==(true)){
                redAlliance = false;
                System.out.println("Is blue alliance");
            }
            else{
                redAlliance = true;
                System.out.println("Is red alliance");
            }




            //code here
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("red alliance", redAlliance);
            editor.putInt("team selected", teamSelected);

            if(isTeamSelected) {
                Intent intent = new Intent(AllianceScreen.this, MainScreen.class);
                startActivity(intent);
            }
        }
    };
}

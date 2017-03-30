package com.sparx1126.steamworks;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sparx1126.steamworks.components.CustomExpandableListAdapter;
import com.sparx1126.steamworks.components.Utility;
import com.sparx1126.steamworks.components.ViewScreenListDataPump;

import org.gosparx.scouting.aerialassist.BenchmarkingData;
import org.gosparx.scouting.aerialassist.TeamData;
import org.gosparx.scouting.aerialassist.ScoutingData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewScreen extends AppCompatActivity {
    private Utility utility;
    private SharedPreferences settings;
    private TextView scalingWarning;
    private EditText teamNumber;
    private  LinearLayout theAllKnower;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_screen);

        utility = Utility.getInstance();
        settings = getSharedPreferences(getResources().getString(R.string.pref_name), 0);

        teamNumber = (EditText) findViewById(R.id.teamNumber);
        teamNumber.addTextChangedListener(teamTextEntered);

        ImageButton home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        theAllKnower = (LinearLayout) findViewById(R.id.theAllKnower);
        theAllKnower.setVisibility(View.INVISIBLE);

        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.setTitle(getString(R.string.view_title));
        }
    }

    private int getTeamNumber() {
        int value = 0;
        String textEntered = teamNumber.getText().toString();
        if (!textEntered.isEmpty()) {
            value = Integer.parseInt(textEntered);
        }
        return value;
    }

    private final TextWatcher teamTextEntered = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if(utility.getTeamList().contains(teamNumber.getText().toString())){
                theAllKnower.setVisibility(View.VISIBLE);
                TeamData.setTeamData(getTeamNumber(), settings.getString(getString(R.string.pref_event), ""));
                TeamData currentInfo = TeamData.getCurrentTeam();
                BenchmarkingData currentData = currentInfo.getBenchmarkingData();
                currentData.setStudent(settings.getString(getString(R.string.pref_scouter), ""));
                List<ScoutingData> scoutingDatas = currentInfo.getScoutingDatas();
                boolean scaledEver = false;
                for(ScoutingData sd: scoutingDatas) {
                    if(sd.isDidScale()) {
                        scaledEver = true;
                        break;
                    }
                }
                scalingWarning = (TextView) findViewById(R.id.scalingWarning);
                if(scaledEver) {
                    scalingWarning.setVisibility(View.INVISIBLE);
                    scalingWarning.setHeight(0);
                } else {
                    scalingWarning.setVisibility(View.VISIBLE);
                }

                ActionBar bar = getSupportActionBar();
                if(bar != null) {
                    bar.setTitle(getString(R.string.view_title) + " - " + String.valueOf(currentInfo.getTeamNumber()));
                }

                if (currentData.isBenchmarkingWasDoneButton()) {
                    ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
                    HashMap<String, List<String>> expandableListDetail = ViewScreenListDataPump.getData();
                    List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                    ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(ViewScreen.this, expandableListTitle, expandableListDetail);
                    expandableListView.setAdapter(expandableListAdapter);
                    LinearLayout layout = (LinearLayout) findViewById(R.id.linear);

                    File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    if (storageDir == null)
                        throw new AssertionError("Cannot read " + Environment.DIRECTORY_PICTURES);
                    String path = storageDir.getAbsolutePath();

                    File directory = new File(path);
                    File[] files = directory.listFiles();
                    boolean noneFound = true;
                    for (int i = 0; i < files.length; i++) {
                        String fileName = files[i].getName();
                        if (fileName.contains(currentData.getTeamNumber() + "Robot")) {
                            noneFound = false;
                            ImageView imageView = new ImageView(ViewScreen.this);
                            imageView.setId(i);
                            imageView.setPadding(2, 2, 2, 2);
                            imageView.setImageBitmap(BitmapFactory.decodeFile(path + "/" + fileName));
                            imageView.setScaleType(ImageView.ScaleType.MATRIX);
                            imageView.setAdjustViewBounds(true);
                            imageView.setMaxWidth(500);
                            imageView.setMaxHeight(450);
                            layout.addView(imageView);
                        }
                    }

                    if (noneFound) {
                        ImageView imageView = new ImageView(ViewScreen.this);
                        imageView.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
                        layout.addView(imageView);
                    }
                }
                else {
                    utility.alertUser(ViewScreen.this, getString(R.string.team_not_benchmark), getString(R.string.data_missing)).show();
                }
            }
            else{
                theAllKnower.setVisibility(View.INVISIBLE);
            }
        }
    };
}
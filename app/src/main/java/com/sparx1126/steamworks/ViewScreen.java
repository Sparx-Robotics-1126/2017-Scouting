package com.sparx1126.steamworks;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sparx1126.steamworks.components.CustomExpandableListAdapter;
import com.sparx1126.steamworks.components.Utility;
import com.sparx1126.steamworks.components.ViewScreenListDataPump;

import org.gosparx.scouting.aerialassist.BenchmarkingData;
import org.gosparx.scouting.aerialassist.TeamData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewScreen extends AppCompatActivity {
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_screen);

        TeamData currentInfo = TeamData.getCurrentTeam();
        BenchmarkingData currentData = currentInfo.getBenchmarkingData();
        Utility utility = Utility.getInstance();

        ImageButton home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.setTitle(getString(R.string.view_title) + String.valueOf(currentInfo.getTeamNumber()));
        }

        if (currentData.isBenchmarkingWasDoneButton()) {

            ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
            HashMap<String, List<String>> expandableListDetail = ViewScreenListDataPump.getData();
            List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
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
                    ImageView imageView = new ImageView(this);
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
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
                layout.addView(imageView);
            }
        }
        else {
            utility.alertUser(this, getString(R.string.team_not_benchmark), getString(R.string.data_missing)).show();
        }
    }
}
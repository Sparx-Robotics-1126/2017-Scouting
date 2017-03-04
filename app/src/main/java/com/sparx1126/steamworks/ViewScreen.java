package com.sparx1126.steamworks;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.gosparx.scouting.aerialassist.dto.BenchmarkingData;
import org.gosparx.scouting.aerialassist.dto.ScoutingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sparx1126.steamworks.R.layout.view_screen;

/**
 * Created by Amanda on 1/21/17.
 */

public class ViewScreen extends AppCompatActivity {
    private ScoutingInfo currentInfo;
    private BenchmarkingData currentData;
    private ImageButton home;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view_screen);

        currentInfo = ScoutingInfo.getCurrentInfo();
        currentData = currentInfo.getBenchmarkingData();
        home = (ImageButton) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ViewScreenListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linear);

        if (!currentData.getPicturePaths().isEmpty()) {
            for (int i = 0; i < currentData.getPicturePaths().size(); i++) {
                ImageView imageView = new ImageView(this);
                imageView.setId(i);
                imageView.setPadding(2, 2, 2, 2);
                imageView.setImageBitmap(BitmapFactory.decodeFile(currentData.getPicturePaths().get(i)));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                layout.addView(imageView);
            }
        }
        else {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
            layout.addView(imageView);
        }
    }
}
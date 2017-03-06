package com.sparx1126.steamworks;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.gosparx.scouting.aerialassist.dto.BenchmarkingData;
import org.gosparx.scouting.aerialassist.dto.TeamData;
import org.gosparx.scouting.aerialassist.networking.BlueAlliance;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Amanda on 1/21/17.
 */

public class ViewScreen extends AppCompatActivity {
    private TeamData currentInfo;
    private BenchmarkingData currentData;
    private ImageButton home;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;



    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_screen);

        currentInfo = TeamData.getCurrentTeam();
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

        ///String imageFileName = String.valueOf(currentData.getTeamNumber());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String path = storageDir.getAbsolutePath();

        File directory = new File(path);
        File[] files = directory.listFiles();
        boolean noneFound = true;
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            if(fileName.contains(currentData.getTeamNumber() + "Robot")) {
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

        if(noneFound) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.ic_add_a_photo_black_24dp);
            layout.addView(imageView);
        }

        boolean benchmarked = currentData.isBenchmarkingWasDoneButton();
        if (benchmarked == false) {
            alertUser("This Team Has Yet To Be Benchmarked", "Some data may not be shown").show();
        }


    }

    private android.app.AlertDialog createUploadDialog(String message) {
        return createPleaseWaitDialog(message, R.string.uploading_data);
    }

    private android.app.AlertDialog createPleaseWaitDialog(String message, int titleID) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(titleID);
        builder.setMessage(message);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                BlueAlliance.getInstance(ViewScreen.this).cancelAll();
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }

    public android.app.AlertDialog alertUser(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();
    }
}
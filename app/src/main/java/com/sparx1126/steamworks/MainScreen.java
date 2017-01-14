package com.sparx1126.steamworks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {
    AutoCompleteTextView event;
    String[] regionals={"Fingerlakes regional 2017", "Cleveland regional 2017"};
    //private Button benchmark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        event=(AutoCompleteTextView)findViewById(R.id.event);

        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,regionals);

        event.setAdapter(adapter);
        event.setThreshold(1);

        //benchmark.setOnClickListener(this);
    }

}

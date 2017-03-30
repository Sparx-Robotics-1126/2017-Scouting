package com.sparx1126.steamworks;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.sparx1126.steamworks.components.Utility;

import org.gosparx.scouting.aerialassist.TeamData;

import static com.sparx1126.steamworks.R.color.colorGrayLight2;

public class TeamChecklistScreen extends AppCompatActivity {
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_checklist_screen);

        Utility utility = Utility.getInstance();
        int numberOfTeams = utility.getTeamList().size();

        TableLayout ll = (TableLayout) findViewById(R.id.checkBoxTable);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);

        int index = 0;

        TableRow row1= new TableRow(this);
        row1.setLayoutParams(lp);
        TextView numberOfTeamsTv = new TextView(this);
        numberOfTeamsTv.setText("# teams-" + String.valueOf(numberOfTeams));
        numberOfTeamsTv.setTextSize(20);
        numberOfTeamsTv.setWidth(200);
        row1.addView(numberOfTeamsTv);
        ll.addView(row1, index);
        index++;

        TableRow row2= new TableRow(this);
        row2.setLayoutParams(lp);
        TextView column0Tv = new TextView(this);
        column0Tv.setText("Team");
        column0Tv.setTextSize(20);
        row2.addView(column0Tv);
        TextView column1Tv = new TextView(this);
        column1Tv.setText("Benchmarked");
        column1Tv.setTextSize(20);
        row2.addView(column1Tv);
        TextView column2Tv = new TextView(this);
        column2Tv.setText("Scouted");
        column2Tv.setTextSize(20);
        row2.addView(column2Tv);
        ll.addView(row2, index);
        index++;

        for (String team: utility.getTeamList()) {

            TableRow row= new TableRow(this);
            row.setLayoutParams(lp);

            TextView teamTv = new TextView(this);
            teamTv.setText(team);
            teamTv.setTextSize(20);
            row.addView(teamTv);

            TextView benchmarkedtv = new TextView(this);
            String benchmarked = "no";
            String scouted = "no";
            int teamNumber = Integer.parseInt(team);
            if(TeamData.getTeamsMap().containsKey(teamNumber)) {
                TeamData teamData = TeamData.getTeamsMap().get(teamNumber);
                if (teamData.getBenchmarkingData().isBenchmarkingWasDoneButton()) {
                    benchmarked = "yes";
                }
                if (teamData.getScoutingDatas().size() > 0) {
                    scouted = "yes";
                }
            }
            benchmarkedtv.setText(benchmarked);
            benchmarkedtv.setTextSize(20);
            row.addView(benchmarkedtv);

            TextView scoutedTv = new TextView(this);
            scoutedTv.setText(scouted);
            scoutedTv.setTextSize(20);
            if (index % 2 == 0) {
                teamTv.setBackgroundResource(colorGrayLight2);
                scoutedTv.setBackgroundResource(colorGrayLight2);
                benchmarkedtv.setBackgroundResource(colorGrayLight2);
            }
            row.addView(scoutedTv);

            ll.addView(row,index);
            index++;
        }
    }
}

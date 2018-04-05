package ca.ryerson.scs.iteration2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.ResultSet;

public class CoachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach);

        findViewById(R.id.setMeeting).setOnClickListener( view -> {
            Intent openMeetingSetting = new Intent(CoachActivity.this, SetMeetingActivity.class);
            CoachActivity.this.startActivity(openMeetingSetting);
        });

        findViewById(R.id.viewSchedule).setOnClickListener( view -> {
            Intent openMembers = new Intent(CoachActivity.this, ViewScheduleActivity.class);
            CoachActivity.this.startActivity(openMembers);
        });

        findViewById(R.id.memberList).setOnClickListener( view -> {
            Intent openMembers = new Intent(CoachActivity.this, MembersActivity.class);
            CoachActivity.this.startActivity(openMembers);
        });

        findViewById(R.id.sendNotification).setOnClickListener( view -> {
            Intent openMembers = new Intent(CoachActivity.this, MembersActivity.class);
            CoachActivity.this.startActivity(openMembers);
        });

    }





    /**
     * Called when the user taps the Practice Reminder Button
     */


}

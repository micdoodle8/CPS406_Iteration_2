package ca.ryerson.scs.iteration2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TreasurerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasurer);

        findViewById(R.id.treasurer_view_income).setOnClickListener( view -> {

            Intent openMeetingSetting = new Intent(TreasurerActivity.this, FinancesActivity.class);
            this.startActivity(openMeetingSetting);
        });

        findViewById(R.id.treasurer_year_list).setOnClickListener( view -> {
            Intent openMembers = new Intent(TreasurerActivity.this, FinancesActivity.class);
            this.startActivity(openMembers);
        });
        
        findViewById(R.id.coachList).setOnClickListener( view -> {

            Intent openCoaches = new Intent(TreasurerActivity.this, CoachListActivity.class);
            TreasurerActivity.this.startActivity(openCoaches);
        });

        findViewById(R.id.setMeeting).setOnClickListener( view -> {

            Intent openMeetingSetting = new Intent(TreasurerActivity.this, SetMeetingActivity.class);
            TreasurerActivity.this.startActivity(openMeetingSetting);
        });

        findViewById(R.id.viewSchedule).setOnClickListener( view -> {
            Intent openMembers = new Intent(TreasurerActivity.this, ViewScheduleActivity.class);
            TreasurerActivity.this.startActivity(openMembers);
        });

        findViewById(R.id.memberList).setOnClickListener( view -> {
            Intent openMembers = new Intent(TreasurerActivity.this, MembersActivity.class);
            openMembers.putExtra("EXTRA_ISCOACH", true);
            TreasurerActivity.this.startActivity(openMembers);
        });

    }
}

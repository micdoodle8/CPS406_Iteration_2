package ca.ryerson.scs.iteration2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class SetMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_meeting);

        EditText date = (EditText)findViewById(R.id.dateInput);
        EditText organizer = (EditText)findViewById(R.id.organizerInput);
        EditText rate = (EditText)findViewById(R.id.rateInput);
        EditText hallId = (EditText)findViewById(R.id.hallIdInput);
        Button create = (Button)findViewById(R.id.create);

        create.setOnClickListener( view -> {

            DBHandler db = DBHandler.getInstance(getApplicationContext());

            db.meetingCreation(1,
                    Integer.parseInt(hallId.getText().toString()),
                    date.getText().toString(),
                    Integer.parseInt(rate.getText().toString()));

            finish();
        });




    }






    }

package ca.ryerson.scs.iteration2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewScheduleActivity extends AppCompatActivity{

    ArrayList<String> selectedItems;
    ListView meetings;
    Button confirm;
    String id;
    int meeting_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_schedule);

        id = getIntent().getStringExtra("EXTRA_ID");


        selectedItems=new ArrayList<String>();
    }


    public void onStart(){
        super.onStart();

        meetings = (ListView) findViewById(R.id.listView);
        meetings.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        confirm = (Button) findViewById(R.id.confirm);

        DBHandler dbHandler = DBHandler.getInstance(getApplicationContext());

        ArrayList<String> _meetings = dbHandler.getAllMeetings();

        //items given to list view
        ArrayAdapter<String> aadapter = new ArrayAdapter<String>(this,R.layout.listlayout,R.id.txt_title, _meetings);

        meetings.setAdapter(aadapter);
        //set on check mark
        meetings.setOnItemClickListener((parent, view, id, k) -> {
                // on selected item
                String selectedItem = ((TextView) view).getText().toString();

                if(selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
                else{
                    selectedItems.add(selectedItem); //add selected item to the list of selected items
                    //dbHandler.meetingSignUp(id, meeting_ids++);
                }


        });


        confirm.setOnClickListener( view ->{
            Log.d("---->", "confirmed");
        });


    }

}

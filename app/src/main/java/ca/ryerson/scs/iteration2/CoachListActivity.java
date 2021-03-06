package ca.ryerson.scs.iteration2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CoachListActivity extends AppCompatActivity {
    ArrayList<String> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_list);
        selectedItems=new ArrayList<String>();
    }

    public void onStart(){
        super.onStart();
        ListView coachlist=(ListView) findViewById(R.id.listView);
        //give multiple selections
        coachlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        DBHandler dbHandler = DBHandler.getInstance(getApplicationContext());

        ArrayList<String> members = dbHandler.getCoaches();

        //items given to list view
        ArrayAdapter<String> aadapter=new ArrayAdapter<String>(this,R.layout.listlayout,R.id.txt_title,members);
        coachlist.setAdapter(aadapter);
        //set on check mark
        coachlist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // on selected item
                String selectedItem = ((TextView) view).getText().toString();
                if(selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
                else
                    selectedItems.add(selectedItem); //add selected item to the list of selected items

            }

        });
    }
    public void coachConfirmation(View view){
        CharSequence confirmText = "Coach Confirmation Sent!";
        //  for(String item:selectedItems){
        //  if(selMembers=="")
        //     selMembers=item;
        // else
        //    selMembers+="/"+item;
        //  }
        Toast.makeText(this, confirmText, Toast.LENGTH_LONG).show();
    }
}

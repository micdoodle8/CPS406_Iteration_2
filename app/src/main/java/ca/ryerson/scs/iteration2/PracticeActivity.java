//package ca.ryerson.scs.iteration2;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.content.Intent;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ListView;
//import android.view.View;
//import java.util.ArrayList;
//
//
//public class PracticeActivity extends AppCompatActivity {
//    ArrayList<String> selectedItems;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_practice);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        selectedItems=new ArrayList<String>();
//    }
//    public void onStart(){
//        super.onStart();
//        ListView memberlist=(ListView) findViewById(R.id.listView);
//        //give multiple selections
//        memberlist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        String[] members={"John Johnson","Thomas Thompson","John Appleseed","Test Tester","Testing Names","hello hellos", "John Johnson","Thomas Thompson","John Appleseed","Test Tester","Testing Names",};
//        //items given to list view
//        ArrayAdapter<String> aadapter=new ArrayAdapter<String>(this,R.layout.listlayout,R.id.txt_title,members);
//        memberlist.setAdapter(aadapter);
//        //set on check mark
//        memberlist.setOnItemClickListener(new OnItemClickListener(){
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // on selected item
//                String selectedItem = ((TextView) view).getText().toString();
//                if(selectedItems.contains(selectedItem))
//                    selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
//                else
//                    selectedItems.add(selectedItem); //add selected item to the list of selected items
//
//            }
//
//        });
//    }
//
//    public void sendMessageConfirmation(View view){
//        CharSequence confirmText = "Message Sent!";
//        //  for(String item:selectedItems){
//        //  if(selMembers=="")
//        //     selMembers=item;
//        // else
//        //    selMembers+="/"+item;
//        //  }
//        Toast.makeText(this, confirmText, Toast.LENGTH_LONG).show();
//    }
//
//}
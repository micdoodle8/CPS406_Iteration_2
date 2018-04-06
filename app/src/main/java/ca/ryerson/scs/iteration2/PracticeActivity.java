package ca.ryerson.scs.iteration2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.view.View;
import java.util.ArrayList;


public class PracticeActivity extends AppCompatActivity {
    ArrayList<String> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_practice);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        selectedItems=new ArrayList<String>();
    }
    public void onStart(){
        super.onStart();

    }

    public void sendMessageConfirmation(View view){
        CharSequence confirmText = "Message Sent!";
      //  for(String item:selectedItems){
          //  if(selMembers=="")
           //     selMembers=item;
           // else
            //    selMembers+="/"+item;
      //  }
        Toast.makeText(this, confirmText, Toast.LENGTH_LONG).show();
    }

}

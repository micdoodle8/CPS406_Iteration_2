package ca.ryerson.scs.iteration2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MembersActivity extends AppCompatActivity {

    private int selectedCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        TableLayout tableLayout = findViewById(R.id.members_layout);
        Button notifyButton = findViewById(R.id.notifyButton);

        Context context = getApplicationContext();

        final ArrayList<String> members = DBHandler.getInstance(context).getCustomers();

        for (String member : members)
        {
            TableRow tableRow = new TableRow(context);

            CheckBox checkBox = new CheckBox(context);
            tableRow.addView(checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean selected) {
                    if (selected) {
                        selectedCount++;
                    } else {
                        selectedCount--;
                    }

                    if (selectedCount <= 0) {
                        notifyButton.setVisibility(View.INVISIBLE);
                    } else if (selectedCount == 1) {
                        notifyButton.setVisibility(View.VISIBLE);
                    }
                }
            });

            TextView column1 = new TextView(context);
            column1.setText(member);
            tableRow.addView(column1);

            TextView column2 = new TextView(context);
            column2.setText("Some data");
            tableRow.addView(column2);

            TextView column3 = new TextView(context);
            column3.setText("Some data");
            tableRow.addView(column3);

            tableLayout.addView(tableRow);
        }








//        GridView gridview = (GridView) findViewById(R.id.gridview);
//
//        final ArrayList<String> members = DBHandler.getInstance(context).getCustomers();
//
//
//        gridview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, members) {
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//                TextView tv = (TextView) view;
//
//                RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
//                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
//                );
//                tv.setLayoutParams(lp);
//
//                // Get the TextView LayoutParams
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();
//
//                // Set the TextView layout parameters
//                tv.setLayoutParams(params);
//
//                // Display TextView text in center position
//                tv.setGravity(Gravity.CENTER);
//
//                // Set the TextView text font family and text size
//                tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
//                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
//
//                // Set the TextView text (GridView item text)
//                tv.setText(members.get(position));
//
//                // Set the TextView background color
//                tv.setBackgroundColor(Color.parseColor("#FF65C6EB"));
//
//                // Return the TextView widget as GridView item
//                return tv;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package ca.ryerson.scs.iteration2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;


public class FinancesActivity extends AppCompatActivity {

    public ConnectToDatabase Conn = new ConnectToDatabase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        // Query database
        String[] data = new String[] {
            "Revenue",
                "$0.0",
            "Profit",
                "$0.0",
            "Coach Payments",
                "$0.0",
            "Hall Expenses",
                "$0.0",
            "Misc Expenses",
                "$0.0"
        };

        // Populate a List from Array elements
        final List<String> membersList = new ArrayList<String>(Arrays.asList(data));

        gridview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, membersList) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;

                RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
                );
                tv.setLayoutParams(lp);

                // Get the TextView LayoutParams
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();

                // Set the TextView layout parameters
                tv.setLayoutParams(params);

                // Display TextView text in center position
                tv.setGravity(Gravity.CENTER);

                // Set the TextView text font family and text size
                tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);

                // Set the TextView text (GridView item text)
                tv.setText(membersList.get(position));

                // Return the TextView widget as GridView item
                return tv;
            }
        });
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

    private double AmountOwed(String EarlyDate, String LaterDate){
        //AmountOwed to Coaches and Hall Rent
        //The dates are defaulted to the last month
        //where EarlyDate and LaterDate are supplied in #DD/MM/YYYY# format

        String sql = "SELECT SUM(Coach.Rate + Halls.Rate) as AmountOwed " +
                    "FROM Meetings " +
                    "JOIN Coach ON Meetings.Organizer = Coach.ID " +
                    "JOIN Halls ON Meetings.Hall_ID = Halls.ID " +
                    "WHERE Date BETWEEN " + EarlyDate + " AND " + LaterDate;

        ResultSet rs = Conn.RetrieveData(sql);
        try {
            return rs.getDouble("AmountOwed");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private double AmountEarned(String EarlyDate, String LaterDate){
        //Get money earned
        //The dates are defaulted to the last month
        //where EarlyDate and LaterDate are supplied in #DD/MM/YYYY# format

        String sql = "SELECT SUM(Amt) as MyTotal " +
                    "FROM Payment " +
                    "WHERE Date BETWEEN " + EarlyDate + " AND " + LaterDate;
        ResultSet rs = Conn.RetrieveData(sql);
        try {
            return rs.getDouble("AmountOwed");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

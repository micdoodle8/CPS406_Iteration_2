package ca.ryerson.scs.iteration2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;


public class FinancesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TableLayout tableLayout = findViewById(R.id.finances_layout);
//        GridView gridview = (GridView) findViewById(R.id.gridview);

        Context context = getApplicationContext();
        DBHandler handler = DBHandler.getInstance(context);

        // Query database
        String[] data = new String[] {
                "Revenue",
                "$" + String.format(Locale.CANADA, "%.2f", handler.getRevenue()),
                "Profit",
                "$" + String.format(Locale.CANADA, "%.2f", handler.getProfit()),
                "Coach Payments",
                "$" + String.format(Locale.CANADA, "%.2f", handler.getCoachPayments()),
                "Hall Expenses",
                "$" + String.format(Locale.CANADA, "%.2f", handler.getHallPayments()),
                "Misc Expenses",
                "$" + String.format(Locale.CANADA, "%.2f", handler.getOtherExpenses())
        };

        for (int i = 0; i < data.length; i += 2)
        {
            TableRow tableRow = new TableRow(context);

            TextView column1 = new TextView(context);
            column1.setText(data[i]);
            tableRow.addView(column1);

            TextView column2 = new TextView(context);
            column2.setText(data[i + 1]);
            tableRow.addView(column2);

            tableLayout.addView(tableRow);
        }

//        // Populate a List from Array elements
//        final List<String> membersList = new ArrayList<String>(Arrays.asList(data));
//
//        gridview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, membersList) {
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
//                tv.setText(membersList.get(position));
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

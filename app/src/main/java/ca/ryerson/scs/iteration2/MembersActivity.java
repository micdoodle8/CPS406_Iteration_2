package ca.ryerson.scs.iteration2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MembersActivity extends AppCompatActivity {

    private int selectedCount = 0;
    private CheckBox selectAllCheckbox;
    private List<CheckBox> checkboxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean isCoach = getIntent().hasExtra("EXTRA_ISCOACH") && getIntent().getBooleanExtra("EXTRA_ISCOACH", false);

        TableLayout tableLayout = findViewById(R.id.members_layout);
        Button notifyButton = findViewById(R.id.members_action_button);

        Context context = getApplicationContext();

        final ArrayList<String> members = DBHandler.getInstance(context).getCustomers();

        if (isCoach) {
            TableRow headerRow = findViewById(R.id.members_table_header);
            selectAllCheckbox = new CheckBox(context);
            headerRow.addView(selectAllCheckbox, 0);
            selectAllCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean selected) {
                    // This is a select all / unselect all checkbox
                    if (selected) {
                        for (CheckBox box : checkboxes) {
                            box.setChecked(true);
                        }
                    } else {
                        for (CheckBox box : checkboxes) {
                            box.setChecked(false);
                        }
                    }
                }
            });

            notifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CharSequence options[] = new CharSequence[] { "Notify Selected", "Remove Selected" };

                    AlertDialog.Builder builder = new AlertDialog.Builder(MembersActivity.this);
                    builder.setTitle("Perform Action...");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int item) {
                            if (item == 0) {
                                for (int i = 0; i < checkboxes.size(); ++i) {
                                    CheckBox box = checkboxes.get(i);
                                    if (box.isChecked()) {
                                        // TODO Notify customer
                                    }
                                }
                            } else if (item == 1) {
                                for (int i = 0; i < checkboxes.size(); ++i) {
                                    CheckBox box = checkboxes.get(i);
                                    if (box.isChecked()) {
                                        // TODO Remove customer
                                    }
                                }
                            }
                        }
                    });
                    builder.show();
                }
            });
        }

        for (String member : members)
        {
            TableRow tableRow = new TableRow(context);

            if (isCoach) {
                // Coaches can check off members to perform certain actions
                CheckBox checkBox = new CheckBox(context);
                tableRow.addView(checkBox);
                checkboxes.add(checkBox);
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
                            selectAllCheckbox.setChecked(false);
                        } else if (selectedCount == 1) {
                            notifyButton.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

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

package ca.ryerson.scs.iteration2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

        final ArrayList<Customer> members = DBHandler.getInstance(context).getCustomers();

        if (isCoach) {
            TableRow headerRow = findViewById(R.id.members_table_header);
            selectAllCheckbox = new CheckBox(context);
            headerRow.addView(selectAllCheckbox, 0);

            selectAllCheckbox.setOnCheckedChangeListener((compoundButton, selected) -> {
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
            });

            notifyButton.setOnClickListener(view -> {
                    CharSequence options[] = new CharSequence[] { "Notify Selected", "Remove Selected" };

                    AlertDialog.Builder builder = new AlertDialog.Builder(MembersActivity.this);
                    builder.setTitle("Perform Action...");

                    builder.setItems(options, (dialogInterface, item) -> {

                        List<Customer> selectedCustomers = new ArrayList<>();
                        for (int i = 0; i < checkboxes.size(); ++i) {
                            CheckBox box = checkboxes.get(i);
                            if (box.isChecked()) {
                                selectedCustomers.add(members.get(i));
                            }
                        }

                        if (item == 0) {
                            openMessageDialog(selectedCustomers);
                        } else if (item == 1) {
                            Log.d("------->", "remove");
                        }
                    });
                    builder.show();
            });
        }

        for (Customer member : members)
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
            column1.setText(member.getName());
            tableRow.addView(column1);

            TextView column2 = new TextView(context);
            column2.setText(member.getEmail());
            tableRow.addView(column2);

            tableLayout.addView(tableRow);
        }
    }

    private void openMessageDialog(List<Customer> selectedCustomers) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Send Message");

        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        builder1.setView(input);

        builder1.setPositiveButton("OK", (dialogInterface12, i12) -> {
            String customerEmails[] = new String[selectedCustomers.size()];
            for (int i = 0; i < selectedCustomers.size(); ++i) {
                customerEmails[i] = selectedCustomers.get(i).getEmail();
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, customerEmails);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Message from your Coach");
            intent.putExtra(Intent.EXTRA_TEXT, input.getText().toString());
            try {
                startActivity(Intent.createChooser(intent, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MembersActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });
        builder1.setNegativeButton("Cancel", (dialogInterface1, i1) -> dialogInterface1.cancel());
        builder1.show();
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

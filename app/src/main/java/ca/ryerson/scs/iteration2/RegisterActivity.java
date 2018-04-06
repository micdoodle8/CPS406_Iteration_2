package ca.ryerson.scs.iteration2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Auto-generated login template by Android Studio
 *
 * In this activity the user can register a new customer/coach account
 */
public class RegisterActivity extends AppCompatActivity {

    private UserRegisterTask mAuthTask = null;

    private EditText mNameView;
    private EditText mLastNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Switch isCoach;
    private int mCoachHourlyRate = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Previous values from login screen:
        String inputEmail = getIntent().getStringExtra("EXTRA_EMAIL");
        String password = getIntent().getStringExtra("EXTRA_PASSWORD");

        setContentView(R.layout.activity_register);
        mNameView = (EditText) findViewById(R.id.name);
        mLastNameView = (EditText) findViewById(R.id.name_last);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.inputEmailReg);
        if (inputEmail != null) {
            mEmailView.setText(inputEmail);
        }

        mPasswordView = (EditText) findViewById(R.id.password_reg);
        if (password != null) {
            mPasswordView.setText(password);
        }
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                return false;
            }
        });

        Button mRegisterButtonCustomer = (Button) findViewById(R.id.register_button);
        mRegisterButtonCustomer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        TextView label = findViewById(R.id.register_rate_label);
        SeekBar bar = findViewById(R.id.register_rate_bar);

        isCoach = findViewById(R.id.coach_switch);
        isCoach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    label.setVisibility(View.VISIBLE);
                    bar.setVisibility(View.VISIBLE);
                } else {
                    label.setVisibility(View.GONE);
                    bar.setVisibility(View.GONE);
                }
            }
        });

        int step = 1;
        int max = 50;
        int min = 15;
        bar.setMax((max - min) / step);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mCoachHourlyRate = (min + (i * step));
                label.setText("Hourly Rate: $" + mCoachHourlyRate);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mLoginFormView = findViewById(R.id.login_form_reg);
        mProgressView = findViewById(R.id.login_progress_reg);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid inputEmail, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void register() {
        if (mAuthTask != null) {
            return;
        }

        // Clear errors
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Retrieve values
        String name = mNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check name is valid
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        // Check last name is valid
        if (TextUtils.isEmpty(lastName)) {
            mLastNameView.setError(getString(R.string.error_field_required));
            focusView = mLastNameView;
            cancel = true;
        }

        // Check password is valid
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        else if (!LoginActivity.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check email is valid
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!LoginActivity.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // An error occurred somewhere, don't focus on the invalid field
            focusView.requestFocus();
        } else {
            // Start login process
            showProgress(true);
            mAuthTask = new UserRegisterTask(name + " " + lastName, email, password, isCoach.isChecked(), mCoachHourlyRate);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Display login progress
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    /**
     * Represents the registration task, which can be executed asynchronously
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mFullName;
        private final String mEmail;
        private final String mPassword;
        private final boolean mIsCoach;
        private final double mCoachHourlyRate;

        UserRegisterTask(String fullName, String email, String password, boolean isCoach, double coachHourlyRate) {
            mFullName = fullName;
            mEmail = email;
            mPassword = password;
            mIsCoach = isCoach;
            mCoachHourlyRate = coachHourlyRate;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Context context = getApplicationContext();
            DBHandler handler = DBHandler.getInstance(context);

            if (mIsCoach)
            {
                // Register as coach
                handler.addNewCoach(mFullName, mCoachHourlyRate);
                handler.addNewUser(mEmail, mPassword, "COACH", handler.getCoaches().size() + 1);

                Intent coachActivity = new Intent(RegisterActivity.this, CoachActivity.class);
                RegisterActivity.this.startActivity(coachActivity);
            }
            else
            {
                // Register as customer
                handler.addNewCustomer(mFullName, mEmail);
                handler.addNewUser(mEmail, mPassword, "CUSTOMER", handler.getCustomers().size() + 1);

                Intent customerActivity = new Intent(RegisterActivity.this, MembersActivity.class);
                RegisterActivity.this.startActivity(customerActivity);
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}


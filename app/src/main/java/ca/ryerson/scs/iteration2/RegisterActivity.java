package ca.ryerson.scs.iteration2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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
 * A login screen that offers login via inputEmail/password.
 */
public class RegisterActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;

    // UI references.
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
        // Set up the login form.
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

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String name = mNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid name
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        // Check for a valid last name
        if (TextUtils.isEmpty(lastName)) {
            mLastNameView.setError(getString(R.string.error_field_required));
            focusView = mLastNameView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
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

        // Check for a valid inputEmail address.
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
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(name + " " + lastName, email, password, isCoach.isChecked(), mCoachHourlyRate);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
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
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mFullName;
        private final String mEmail;
        private final String mPassword;
        private final boolean mIsCoach;
        private final double mCoachHourlyRate;

        UserLoginTask(String fullName, String email, String password, boolean isCoach, double coachHourlyRate) {
            mFullName = fullName;
            mEmail = email;
            mPassword = password;
            mIsCoach = isCoach;
            mCoachHourlyRate = coachHourlyRate;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            // Add the customer to the DB
            Context context = getApplicationContext();
            DBHandler handler = DBHandler.getInstance(context);

            if (mIsCoach)
            {
                // Register as coach
                handler.addNewCoach(mFullName, mCoachHourlyRate);
                handler.addNewUser(mEmail, mPassword, "COACH", handler.getCoaches().size() + 1);
            }
            else
            {
                // Register as customer
                handler.addNewCustomer(mFullName, mEmail);
                handler.addNewUser(mEmail, mPassword, "CUSTOMER", handler.getCustomers().size() + 1);
            }


            // Open main activity
            Intent openMain = new Intent(RegisterActivity.this, MainActivity.class);
            openMain.putExtra("EXTRA_LOGGED_IN_EMAIL", mEmail);
            startActivity(openMain);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}


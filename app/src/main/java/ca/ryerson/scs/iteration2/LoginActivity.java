package ca.ryerson.scs.iteration2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated login template by Android Studio
 *
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.inputEmail);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener( (view, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(view -> { attemptLogin(); });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Clear errors
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Retrieve values
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check password is valid
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check email address is valid
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
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
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Given an email, returns true if valid, false otherwise
     */
    static boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    /**
     * Given a password, returns true if valid, false otherwise
     */
    static boolean isPasswordValid(String password) {
        if (TextUtils.isEmpty(password)) {
            return false;
        } else {
            return true;
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
     * Create a new account instead of logging in
     */
    private void createNewAccount(String email, String password)
    {
        Intent openRegister = new Intent(LoginActivity.this, RegisterActivity.class);
        // Pass the data to the next activity
        openRegister.putExtra("EXTRA_EMAIL", email);
        openRegister.putExtra("EXTRA_PASSWORD", password);
        startActivity(openRegister);
    }

    /**
     * Represents the login task, which can be executed asynchronously
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        DBHandler db;
        private final String mEmail;
        private final String mPassword;
        private String userType;

        UserLoginTask(String email, String password) {
            db = DBHandler.getInstance(getApplicationContext());
            mEmail = email;
            mPassword = password;
            userType = "";
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            userType = db.authenticate(mEmail, mPassword);
            if (!userType.isEmpty()) return true;

            createNewAccount(mEmail, mPassword);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (!userType.isEmpty()) {
                if (success) {
                    switch (userType) {
                        case "CUSTOMER":
                            Intent customerActivity = new Intent(LoginActivity.this, ViewScheduleActivity.class);
                            LoginActivity.this.startActivity(customerActivity);
                            break;
                        case "COACH":
                            Intent coachActivity = new Intent(LoginActivity.this, CoachActivity.class);
                            LoginActivity.this.startActivity(coachActivity);
                            break;
                        case "TREASURER":
                            Intent treasurerActivity = new Intent(LoginActivity.this, TreasurerActivity.class);
                            LoginActivity.this.startActivity(treasurerActivity);
                            break;
                        default:
                            Intent placeholder = new Intent(LoginActivity.this, ViewScheduleActivity.class);
                            LoginActivity.this.startActivity(placeholder);
                            break;
                    }
                    finish();
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}


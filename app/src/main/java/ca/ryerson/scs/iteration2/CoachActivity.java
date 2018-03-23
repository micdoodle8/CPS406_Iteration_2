package com.example.sarmiran.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CoachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach);

        defineButtons();
    }

    public void defineButtons() {
        findViewById(R.id.practice_button).setOnClickListener(buttonClickListener);
        findViewById(R.id.payment_button).setOnClickListener(buttonClickListener);
    }

    /**
     * Called when the user taps the Practice Reminder Button
     */
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Do something in response to button
            int id = view.getId();
            if (id == R.id.practice_button) {
                Intent intent = new Intent(CoachActivity.this, PracticeActivity.class);
                startActivity(intent);
            } else if (id == R.id.payment_button) {
                Intent payment = new Intent(CoachActivity.this, PaymentActivity.class);
                startActivity(payment);
            }

        }

        ;
    };
}

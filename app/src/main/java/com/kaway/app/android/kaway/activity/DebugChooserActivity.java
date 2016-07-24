package com.kaway.app.android.kaway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.kaway.app.android.kaway.R;

public class DebugChooserActivity extends AppCompatActivity {

    Button jeepMode;
    Button passengerMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_chooser);

        jeepMode = (Button) findViewById(R.id.jeepButton);
        passengerMode = (Button) findViewById(R.id.passengerButton);

        jeepMode.setOnClickListener(v -> {
            onClickJeep();
        });

        passengerMode.setOnClickListener(v -> {
            onClickPassenger();
        });
    }

    private void onClickJeep() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.IS_JEEP_FLAG, true);
        startActivity(intent);
    }

    private void onClickPassenger() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.IS_JEEP_FLAG, false);
        startActivity(intent);
    }
}

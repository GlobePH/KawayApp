package com.kaway.app.android.kaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.kaway.app.android.kaway.activity.MainActivity;

public class BootActivity extends AppCompatActivity {

    boolean finishApp = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (!finishApp) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 0);
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            finishApp = true;
        }
    }
}

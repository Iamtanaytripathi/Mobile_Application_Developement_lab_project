package com.example.focusswipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String currentAppPackage = getIntent().getStringExtra("currentAppPackage");

        stopService(new Intent(this, AppCheckService.class));

        Button continueButton = findViewById(R.id.buttonYes);
        Button goBackButton = findViewById(R.id.buttonNo);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueButtonClick(v);
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGoBackButtonClick(v);
            }
        });
    }

    public void onContinueButtonClick(View view) {
        String currentAppPackage = getIntent().getStringExtra("currentAppPackage");
        if (currentAppPackage != null) {
            PackageManager packageManager = getPackageManager();
            Intent launchIntent = packageManager.getLaunchIntentForPackage(currentAppPackage);
            if (launchIntent != null) {
                startActivity(launchIntent);
            } else {
                Toast.makeText(SplashScreenActivity.this, "Unable to launch app", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    public void onGoBackButtonClick(View view) {
        startService(new Intent(this, AppCheckService.class));
        Intent intent = new Intent(this, SelectedAppsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
        startActivity(intent);
    }
}
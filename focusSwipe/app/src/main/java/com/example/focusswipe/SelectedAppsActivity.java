package com.example.focusswipe;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.SelectedAppsAdapter;

public class SelectedAppsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SelectedAppsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_apps);

        recyclerView = findViewById(R.id.selectedAppsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve the selected apps list from the intent
        List<ApplicationInfo> selectedApps = getIntent().getParcelableArrayListExtra("selectedApps");

        // Create and set the adapter for the RecyclerView
        adapter = new SelectedAppsAdapter(selectedApps);
        recyclerView.setAdapter(adapter);

        Button startServiceButton = findViewById(R.id.buttonStartService);
        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the AppCheckService
                if (isServiceRunning(AppCheckService.class)) {
                    showToast("Service is already running.");
                } else {
                    // Create an intent to start the AppCheckService
                    Intent serviceIntent = new Intent(SelectedAppsActivity.this, AppCheckService.class);
                    serviceIntent.putStringArrayListExtra("selectedApps", (ArrayList<String>) adapter.getSelectedAppPackageNames());
                    startService(serviceIntent);
                    showToast("Service started.");
                }
            }
        });

    }
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServices) {
            if (serviceClass.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

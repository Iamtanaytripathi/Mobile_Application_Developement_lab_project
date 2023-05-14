package com.example.focusswipe;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    }
}

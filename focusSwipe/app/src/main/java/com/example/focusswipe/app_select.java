package com.example.focusswipe;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerViewAdapter;

public class app_select extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<ApplicationInfo> appList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_select);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(this, appList);
        recyclerView.setAdapter(adapter);

        fetchInstalledApps();
    }

    private void fetchInstalledApps() {
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> installedApps = packageManager.getInstalledApplications(0);

        for (ApplicationInfo appInfo : installedApps) {
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                // Exclude system apps

                String appName = appInfo.loadLabel(packageManager).toString();
                Drawable appIcon = appInfo.loadIcon(packageManager);

                appList.add(appInfo);
            }
        }

        adapter.notifyDataSetChanged();

        Toast.makeText(this, "Total apps: " + appList.size(), Toast.LENGTH_SHORT).show();
    }
}

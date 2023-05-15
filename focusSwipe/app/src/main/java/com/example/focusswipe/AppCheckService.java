package com.example.focusswipe;

import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppCheckService extends Service {

    private static final String TAG = "AppCheckService";

    private List<String> selectedAppPackageNames;
    private Handler handler;
    private Runnable appCheckRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("selectedApps")) {
            selectedAppPackageNames = intent.getStringArrayListExtra("selectedApps");
             // Start the app checking process
            Log.d(TAG, "Received selected app package names: " + selectedAppPackageNames.toString());
        } else {
            stopSelf(); // Stop the service if no selected app package names are provided
        }
        startAppChecking();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopAppChecking(); // Stop the app checking process
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // This service does not support binding
    }

    private void startAppChecking() {
        appCheckRunnable = new Runnable() {
            @Override
            public void run() {
                checkCurrentApp(); // Check the currently opened app
                handler.postDelayed(this, 1000); // Run the app checking process every 1 second
            }
        };
        handler.post(appCheckRunnable);
    }

    private void stopAppChecking() {
        if (handler != null && appCheckRunnable != null) {
            handler.removeCallbacks(appCheckRunnable);
            appCheckRunnable = null;
        }
    }

    private void checkCurrentApp() {
        String currentAppPackage = getCurrentAppPackage(); // Get the package name of the currently opened app
        Log.d(TAG, "Selected app opened: " + currentAppPackage);
        if (currentAppPackage != null && selectedAppPackageNames.contains(currentAppPackage)) {
            Intent intent = new Intent(this, SplashScreenActivity.class);
            intent.putExtra("currentAppPackage", currentAppPackage);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private String getCurrentAppPackage() {
        String currentAppPackage = null;
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long currentTime = System.currentTimeMillis();
        List<UsageStats> appList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 1000 * 10, currentTime);
        if (appList != null && !appList.isEmpty()) {
            UsageStats recentApp = null;
            for (UsageStats usageStats : appList) {
                if (recentApp == null || usageStats.getLastTimeUsed() > recentApp.getLastTimeUsed()) {
                    recentApp = usageStats;
                }
            }
            if (recentApp != null) {
                currentAppPackage = recentApp.getPackageName();
            }
        }
        return currentAppPackage;
    }
}

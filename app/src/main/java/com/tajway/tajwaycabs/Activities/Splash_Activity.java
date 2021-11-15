package com.tajway.tajwaycabs.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.tajway.tajwaycabs.R;
import com.tajway.tajwaycabs.session.SessonManager;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

public class Splash_Activity extends AppCompatActivity implements Runnable {

    private static int SPLASH_TIME_OUT = 2000;
    Handler handler;
    SessonManager sessonManager;
    private static final int IMMEDIATE_APP_UPDATE_REQ_CODE = 123;
    private AppUpdateManager appUpdateManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        getSupportActionBar().hide();
        handler = new Handler();
        checkUpdate();
        appUpdateManager.registerListener(listener);

        sessonManager = new SessonManager(Splash_Activity.this);
    }


    @Override
    public void run() {
        Log.d("bbb", sessonManager.getToken());
        if (sessonManager.getToken().length() > 0) {
            Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(Splash_Activity.this, Login_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(this);
    }

    private void checkUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.clientVersionStalenessDays() != null
                    && appUpdateInfo.updatePriority() >= 4 /* high priority */
                    && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, Splash_Activity.this, IMMEDIATE_APP_UPDATE_REQ_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(this, SPLASH_TIME_OUT);
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            Log.e("appUpdateManager", "--1-" + appUpdateInfo.updateAvailability());
                            Log.e("appUpdateManager", "-2--" + UpdateAvailability.UPDATE_AVAILABLE);
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.UPDATE_AVAILABLE) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            IMMEDIATE,
                                            this,
                                            IMMEDIATE_APP_UPDATE_REQ_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMMEDIATE_APP_UPDATE_REQ_CODE) {

            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
                checkUpdate();
            }

        }
    }

    // Create a listener to track request state updates.
    InstallStateUpdatedListener listener = state -> {
        // (Optional) Provide a download progress bar.
        if (state.installStatus() == InstallStatus.DOWNLOADING) {
            long bytesDownloaded = state.bytesDownloaded();
            long totalBytesToDownload = state.totalBytesToDownload();

            // Implement progress bar.
        } else if (state.installStatus() == InstallStatus.INSTALLED) {
            removeInstallStateUpdateListener();
        }
        // Log state or install the update.
    };

    private void removeInstallStateUpdateListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(listener);
        }
    }


}
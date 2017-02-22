package com.rathor.hci.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.rathor.hci.R;
import com.rathor.hci.utils.Utils;


public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!Utils.isTablet(this))
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                ) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_CONTACTS
                           }, 11);
        } else {
            refreshUI();
        }

    }

    private void refreshUI() {
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                                         // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  },

                SPLASH_DISPLAY_LENGTH);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean isGranted = true;
        switch (requestCode) {
            case 11: {

                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                    } else {

                        isGranted = false;
                        AlertDialog.Builder alert = new AlertDialog.Builder(this);
                        alert.setTitle(getResources().getString(R.string.app_name));
                        alert.setMessage(permissions[i] + " Permission Required for run app successfully.\nGo to your device setting, click on apps,\nselect your app and enable required permission for App");
                        alert.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub;
                                        finish();
                                    }
                                });
                        alert.show();
                        break;
                    }
                }
            }
        }

        if (isGranted) {
            refreshUI();
        }
    }
}

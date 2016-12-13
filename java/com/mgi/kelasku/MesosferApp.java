package com.mgi.kelasku;

import android.app.Application;

import com.eyro.mesosfer.Mesosfer;

public class MesosferApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // uncomment this line below to show Mesosfer log in verbose mode
        // Mesosfer.setLogLevel(Mesosfer.LOG_LEVEL_VERBOSE);
        Mesosfer.setPushNotification(true);
        // initialize Mesosfer SDK
        Mesosfer.initialize(this, "vFGWluivZQ", "3NYLQ2aBx2Lh9aRIG2lq6FCGhb0kTG3W");
    }
}
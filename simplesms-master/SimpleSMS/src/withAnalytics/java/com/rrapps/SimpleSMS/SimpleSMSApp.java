package com.rrapps.SimpleSMS;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SimpleSMSApp extends SimpleSMSAppBase {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}

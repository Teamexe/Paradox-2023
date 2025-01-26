package com.exe.paradox.Essentials;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;

public class Application extends android.app.Application {
    private static RequestQueue mainRequestQueue;
    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        setupActivityListener();
        mainRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }



    public static RequestQueue getMainRequestQueue() {
        return mainRequestQueue;
    }

    private void setupActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);            }
            @Override
            public void onActivityStarted(Activity activity) {
            }
            @Override
            public void onActivityResumed(Activity activity) {

            }
            @Override
            public void onActivityPaused(Activity activity) {

            }
            @Override
            public void onActivityStopped(Activity activity) {
            }
            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }
}

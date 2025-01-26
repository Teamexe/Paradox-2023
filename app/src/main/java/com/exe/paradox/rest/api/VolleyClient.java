package com.exe.paradox.rest.api;

import com.android.volley.RequestQueue;
import com.exe.paradox.Essentials.Application;

public class VolleyClient {

//    public static String BASE_URL = "https://paradox-backend.onrender.com/";
    public static String BASE_URL = "http://206.189.132.227/";
//    public static String BASE_URL = "https://ea97-2409-4056-dc6-179f-4fd9-517c-eef4-f00e.ngrok-free.app/";
    public static String HOST= "quasar-edtech.vercel.app/edtech";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    private static RequestQueue requestQueue = Application.getMainRequestQueue();

}

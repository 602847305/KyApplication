package com.example.kyapplication.utils;

import android.util.Log;

public class F {

    public static void d(Object s)
    {
        if (s==null)
        {
            Log.d("Play TAG,","null");
        }else {
            Log.d("Play TAG,",s.toString());
        }

    }
    public static void d(String tag,Object s)
    {
        Log.d(tag,s.toString());
    }
}

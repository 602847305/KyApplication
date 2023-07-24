package com.example.kyapplication.utils;

import android.content.Context;

import com.example.kyapplication.App;

public class ResourceUtil {

    public static int getLayoutId( String paramString)
    {
        return App.app.getResources().getIdentifier(paramString, "layout",
                App.app.getPackageName());
    }

    public static int getStringId( String paramString) {
        return App.app.getResources().getIdentifier(paramString, "string",
                App.app.getPackageName());
    }

    public static int getDrawableId( String paramString) {
        return App.app.getResources().getIdentifier(paramString,
                "drawable", App.app.getPackageName());
    }

    public static int getStyleId( String paramString) {
        return App.app.getResources().getIdentifier(paramString,
                "style", App.app.getPackageName());
    }

    public static int getId( String paramString) {
        return App.app.getResources().getIdentifier(paramString,
                "id", App.app.getPackageName());
    }

    public static int getColorId( String paramString) {
        return App.app.getResources().getIdentifier(paramString,
                "color", App.app.getPackageName());
    }

    public static int getStringArrayId( String paramString) {
        return App.app.getResources().getIdentifier(paramString,
                "array", App.app.getPackageName());
    }

    public static int getAnimId( String paramString) {
        return App.app.getResources().getIdentifier(paramString,
                "anim", App.app.getPackageName());
    }
    public static int getRawId( String paramString) {
        return App.app.getResources().getIdentifier(paramString,
                "raw", App.app.getPackageName());
    }

    public static String getString( String paramString)
    {
        return App.app.getResources().getString(getStringId(paramString));
    }
    
}

package com.lime.mypol.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by seongsan on 2015-08-27.
 */
public class WindowUtil {
    public static int width;
    public static int height;
    public static void getDisplay(Context context){
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
    }
}

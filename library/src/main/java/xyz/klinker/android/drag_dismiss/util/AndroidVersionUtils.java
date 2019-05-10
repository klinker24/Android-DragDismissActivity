package xyz.klinker.android.drag_dismiss.util;

import android.os.Build;

public class AndroidVersionUtils {

    public static boolean isAndroidQ() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.P || Build.VERSION.CODENAME.equals("Q");
    }

}

package org.appspot.apprtc.csio;

import android.content.Context;
import android.provider.Settings;

public class CsioUtils {
    public CsioUtils() {

    }

    public String getLocalId() {
        long localId = System.currentTimeMillis();
        return String.valueOf(localId);
    }

    public String getDeviceId(Context context) {
        if (context == null) {
            throw new RuntimeException("CONTEXT CANNOT BE NULL");
        }
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }
}

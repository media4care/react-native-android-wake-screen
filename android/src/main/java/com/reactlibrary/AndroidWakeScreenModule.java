// AndroidWakeScreenModule.java

package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import android.app.ActivityManager;
import android.content.Context;
import android.os.PowerManager;
import android.os.Build;

import static androidx.core.content.ContextCompat.getSystemService;


public class AndroidWakeScreenModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public AndroidWakeScreenModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AndroidWakeScreen";
    }

    @ReactMethod
    public void wakeScreen() {
        final AppCompatActivity activity = getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            activity.setShowWhenLocked(true);
            activity.setTurnScreenOn(true);
        } else {
            PowerManager manager = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = manager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "m4c:wake");
            wl.acquire();
            wl.release();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            KeyguardManager manager = (KeyguardManager) getContext().getSystemService(Context.KEYGUARD_SERVICE);
            if (manager != null) {
                manager.requestDismissKeyguard(getActivity(), null);
            }
        }
    }
}

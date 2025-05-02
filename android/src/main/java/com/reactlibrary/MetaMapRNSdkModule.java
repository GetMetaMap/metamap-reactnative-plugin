package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.metamap.metamap_sdk.MetamapSdk;
import com.metamap.metamap_sdk.Metadata;

import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MetaMapRNSdkModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    // ---------- constants ----------
    /**  You can change this to any free int, *but* it must match in both places. */
    private static final int METAMAP_REQUEST_CODE = MetamapSdk.DEFAULT_REQUEST_CODE;  // <-- FIX ①
    private static final String TAG = "MetaMapRN";

    // ---------- state ----------
    private final ReactApplicationContext reactContext;

    public MetaMapRNSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        /*  Register once – avoids adding the listener on every call
            and guarantees onActivityResult is reached.                */
        reactContext.addActivityEventListener(this);                  // <-- FIX ②
    }

    @Override
    public String getName() {
        return "MetaMapRNSdk";
    }

    // ------------------------------------------------------------
    //  Public API
    // ------------------------------------------------------------
    @ReactMethod
    public void showFlowWithConfigurationId(
            final String clientId,
            @Nullable final String flowId,
            @Nullable final ReadableMap metadata,
            @Nullable final String configurationId,
            @Nullable final String encryptionConfigurationId
    ) {
        reactContext.runOnUiQueueThread(() -> {
            Activity currentActivity = getCurrentActivity();
            if (currentActivity == null) {
                Log.e(TAG, "❌ currentActivity is null. Cannot start flow.");
                return;
            }

            Log.d(TAG, "➡️ Starting MetaMap flow ‑ clientId=" + clientId + " flowId=" + flowId);

            MetamapSdk.INSTANCE.startFlow(
                    currentActivity,
                    clientId,
                    flowId,
                    convertToMetadata(metadata),
                    METAMAP_REQUEST_CODE,                       // <-- FIX ① (keep in sync)
                    configurationId,
                    encryptionConfigurationId,
                    verificationStartedCallback()
            );

            Log.d(TAG, "✅ startFlow() invoked");
        });
    }

    @ReactMethod
    public void showFlow(
            final String clientId,
            @Nullable final String flowId,
            @Nullable final ReadableMap metadata
    ) {
        showFlowWithConfigurationId(clientId, flowId, metadata, null, null);
    }

    // ------------------------------------------------------------
    //  Activity callbacks
    // ------------------------------------------------------------
    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode != METAMAP_REQUEST_CODE) return;              // <-- FIX ①

        WritableMap params = Arguments.createMap();
        if (data != null) {
            params.putString("identityId", data.getStringExtra(MetamapSdk.ARG_IDENTITY_ID));
            params.putString("verificationId", data.getStringExtra(MetamapSdk.ARG_VERIFICATION_ID));
        }

        String event = (resultCode == Activity.RESULT_OK) ? "verificationSuccess" : "verificationCanceled";
        sendEvent(event, params);
    }

    @Override public void onNewIntent(Intent intent) { /* not used */ }

    // ------------------------------------------------------------
    //  Helpers
    // ------------------------------------------------------------
    private Function2<String, String, Unit> verificationStartedCallback() {
        return (identityId, verificationId) -> {
            Log.d(TAG, "📞 verificationStarted");
            WritableMap params = Arguments.createMap();
            params.putString("identityId", identityId);
            params.putString("verificationId", verificationId);

            // Give JS ~½ s to attach listeners before emitting
            new Handler(Looper.getMainLooper()).postDelayed(
                    () -> sendEvent("verificationStarted", params),
                    500
            );
            return Unit.INSTANCE;
        };
    }

    private void sendEvent(String name, @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(name, params);
    }

    private Metadata convertToMetadata(@Nullable ReadableMap map) {
        if (map == null) return null;

        Metadata.Builder builder = new Metadata.Builder();
        for (Map.Entry<String, Object> entry : map.toHashMap().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (key.toLowerCase().contains("color") && value instanceof String) {
                // Accept both #RRGGBB and #AARRGGBB
                builder.with(key, Color.parseColor((String) value));
            } else {
                builder.with(key, value);
            }
        }
        builder.with("sdkType", "react-native-android");
        return builder.build();
    }
}
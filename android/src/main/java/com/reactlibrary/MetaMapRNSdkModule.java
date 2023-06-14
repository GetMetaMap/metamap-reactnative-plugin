package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.graphics.Color;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.metamap.metamap_sdk.MetamapSdk;
import com.metamap.metamap_sdk.Metadata;

import java.util.HashMap;
import java.util.Map;


import static android.app.Activity.RESULT_OK;

public class MetaMapRNSdkModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private final ReactApplicationContext reactContext;

    public MetaMapRNSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "MetaMapRNSdk";
    }

    @ReactMethod
    public void showFlowWithConfigurationId(final String clientId, @Nullable final String flowId, @Nullable final ReadableMap metadata, @Nullable final String configurationId, @Nullable final String encryptionConfigurationId) {
        reactContext.runOnUiQueueThread(new Runnable() {
            @Override
            public void run() {
                MetamapSdk.INSTANCE.startFlow(getReactApplicationContext().getCurrentActivity(),
                        clientId,
                        flowId,
                        convertToMetadata(metadata),
                        2576,
                        configurationId,
                        encryptionConfigurationId);
                reactContext.addActivityEventListener(MetaMapRNSdkModule.this);
            }
        });
    }

    @ReactMethod
    public void showFlow(final String clientId, @Nullable final String flowId, @Nullable final ReadableMap metadata) {
        reactContext.runOnUiQueueThread(new Runnable() {
            @Override
            public void run() {
                MetamapSdk.INSTANCE.startFlow(getReactApplicationContext().getCurrentActivity(),
                        clientId,
                        flowId,
                        convertToMetadata(metadata));
                reactContext.addActivityEventListener(MetaMapRNSdkModule.this);
            }
        });
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == MetamapSdk.DEFAULT_REQUEST_CODE) {
            if (data != null) {
                WritableMap params = Arguments.createMap();
                params.putString("identityId", data.getStringExtra(MetamapSdk.ARG_IDENTITY_ID));
                params.putString("verificationId", data.getStringExtra(MetamapSdk.ARG_VERIFICATION_ID));
                if (resultCode == RESULT_OK) {
                    sendEvent(reactContext, "verificationSuccess", params);
                } else {
                    sendEvent(reactContext, "verificationCanceled", params);
                }
            } else {
                sendEvent(reactContext, "verificationCanceled", null);
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    private Metadata convertToMetadata(ReadableMap data) {
        if (data != null) {
            HashMap<String, Object> metadataHashMap = data.toHashMap();
            Metadata.Builder metadataBuilder = new Metadata.Builder();
            for (Map.Entry<String, Object> entry : metadataHashMap.entrySet()) {
                String key = entry.getKey();
                if (key.toLowerCase().contains("color")) {
                    String hexColor = (String) entry.getValue();
                    int color = Color.parseColor(hexColor);
                    if (hexColor.length() == 9) {
                        color = Color.argb(Color.blue(color), Color.alpha(color), Color.red(color), Color.green(color));
                    }
                    metadataBuilder.with(key, color);
                } else {
                    metadataBuilder.with(entry.getKey(), entry.getValue());
                }
            }
            metadataBuilder.with("sdkType", "react-native-android");
            return metadataBuilder.build();
        } else {
            return null;
        }
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

}


package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

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
import com.getmati.mati_sdk.Metadata;
import com.getmati.mati_sdk.MatiSdk;

import java.util.HashMap;
import java.util.Map;


import static android.app.Activity.RESULT_OK;

public class MatiGlobalIdSdkModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private final ReactApplicationContext reactContext;

    public MatiGlobalIdSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "MatiGlobalIdSdk";
    }

    @ReactMethod
    public void showFlow(final String clientId, @Nullable final String flowId , @Nullable final ReadableMap metadata) {
        reactContext.runOnUiQueueThread(new Runnable() {
            @Override
            public void run() {
                MatiSdk.INSTANCE.startFlow(getReactApplicationContext().getCurrentActivity(),
                        clientId,
                        flowId,
                        convertToMetadata(metadata));
                reactContext.addActivityEventListener(MatiGlobalIdSdkModule.this);
            }
        });
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if(requestCode == MatiSdk.REQUEST_CODE) {
            if(resultCode == RESULT_OK && data != null) {
                  WritableMap params = Arguments.createMap();
                params.putString("identityId", data.getStringExtra(MatiSdk.ARG_VERIFICATION_ID));
               sendEvent(reactContext, "verificationSuccess", params);
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
                metadataBuilder.with(entry.getKey(), entry.getValue());
            }
            metadataBuilder.with("sdkType", "android_reactNative");
            return metadataBuilder.build();
        } else {
            return  null;
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


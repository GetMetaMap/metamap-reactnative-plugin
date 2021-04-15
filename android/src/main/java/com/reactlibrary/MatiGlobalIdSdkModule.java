package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.getmati.mati_sdk.MatiButton;
import com.getmati.mati_sdk.Metadata;
import com.getmati.mati_sdk.kyc.KYCActivity;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class MatiGlobalIdSdkModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    private final ReactApplicationContext reactContext;
    private MatiButton matiButton;

    public MatiGlobalIdSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "MatiGlobalIdSdk";
    }

    @ReactMethod
    public void setParams(final String clientId, @Nullable final String flowId , @Nullable final ReadableMap metadata) {

        reactContext.runOnUiQueueThread(new Runnable() {
            @Override
            public void run() {
                matiButton = new MatiButton(getActivity(), null);
                matiButton.setParams(clientId,
                        flowId,
                        "Default flow",
                        convertToMetadata(metadata));
                reactContext.addActivityEventListener(MatiGlobalIdSdkModule.this);
            }
        });
    }

    private AppCompatActivity getActivity() {
        return (AppCompatActivity)getReactApplicationContext().getCurrentActivity();
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if(requestCode == KYCActivity.REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                  WritableMap params = Arguments.createMap();
                params.putString("identityId", data.getStringExtra(KYCActivity.ARG_VERIFICATION_ID));
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
            return metadataBuilder.build();
        } else {
            return  null;
        }
    }

    @ReactMethod
    public void showFlow() {
        if (matiButton.getVm().getValue() != null) {
            MatiButton.State matiState = matiButton.getVm().getValue();
            if (matiState instanceof MatiButton.SuccessState) {
                MatiButton.SuccessState matiSuccess = (MatiButton.SuccessState) matiState;

                Intent intent = new Intent(reactContext, KYCActivity.class);
                intent.putExtra("ARG_ID_TOKEN", matiSuccess.getIdToken());
                intent.putExtra("ARG_CLIENT_ID", matiSuccess.getClientId());
                intent.putExtra("ARG_VERIFICATION_ID", matiSuccess.getVerificationId());
                intent.putExtra("ARG_ACCESS_TOKEN", matiSuccess.getAccessToken());
                intent.putExtra("ARG_VOICE_TXT", matiSuccess.getVoiceDataTxt());
                intent.putExtra("STATE_LANGUAGE_ID", matiSuccess.getIdToken());
                getActivity().startActivityForResult(intent, KYCActivity.REQUEST_CODE);
            } else {
                Log.e("Loading error", "Not ready yet, loading...");
            }
        } else {
            Log.e("Loading error", "Please check yours Mati client ID or internet connection");
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


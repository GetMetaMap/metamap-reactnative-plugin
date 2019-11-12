package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;


import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.facebook.react.bridge.ReadableMap;
import com.matilock.mati_kyc_sdk.LoginError;
import com.matilock.mati_kyc_sdk.LoginResult;
import com.matilock.mati_kyc_sdk.Mati;
import com.matilock.mati_kyc_sdk.MatiCallback;
import com.matilock.mati_kyc_sdk.MatiCallbackManager;
import com.matilock.mati_kyc_sdk.MatiLoginManager;
import com.matilock.mati_kyc_sdk.Metadata;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;


public class MatiGlobalIdSdkModule extends ReactContextBaseJavaModule implements ActivityEventListener, MatiCallback {

    private final ReactApplicationContext reactContext;
    private MatiCallbackManager mCallbackManager = MatiCallbackManager.createNew();

    public static WeakReference<MatiGlobalIdSdkModule> weakReferenceMatiGlobalIdSdkModule;
    Callback mOnCallback;

    public MatiGlobalIdSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        weakReferenceMatiGlobalIdSdkModule = new WeakReference<>(this);
    }

    public  Activity getActivity() {
        return getCurrentActivity();
    }

    @Override
    public String getName() {
        return "MatiGlobalIdSdk";
    }

    @ReactMethod
    public void init(final String clientId) {
        reactContext.runOnUiQueueThread(new Runnable() {
            @Override
            public void run() {
                Mati.init(reactContext, clientId);
                reactContext.addActivityEventListener(MatiGlobalIdSdkModule.this);
                MatiLoginManager.getInstance().registerCallback(mCallbackManager, MatiGlobalIdSdkModule.this);
            }
        });
    }

    @ReactMethod
    public void setMatiCallback(Callback callback) {
        mOnCallback = callback;
    }

    @ReactMethod
    public void metadata(final ReadableMap metadata)
    {
        reactContext.runOnUiQueueThread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> metadataHashMap = metadata.toHashMap();
                Metadata.Builder metadataBuilder = new Metadata.Builder();
                for (Map.Entry<String,Object> entry : metadataHashMap.entrySet())
                {
                    metadataBuilder.with(entry.getKey(), entry.getValue());
                }
                Mati.getInstance().setMetadata(metadataBuilder.build());
            }
        });
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onSuccess(LoginResult pLoginResult) {
        if(mOnCallback != null){
            mOnCallback.invoke(pLoginResult.isSuccess(), pLoginResult.getIdentityId());
        }
    }

    @Override
    public void onCancel() {
        if(mOnCallback != null){
            mOnCallback.invoke(false, "Cancel");
        }
    }

    @Override
    public void onError(LoginError pLoginError) {
        if(mOnCallback != null) {
            mOnCallback.invoke(false, pLoginError.getMessage());
        }
    }
}

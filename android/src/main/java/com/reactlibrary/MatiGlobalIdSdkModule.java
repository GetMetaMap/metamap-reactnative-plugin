package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.matilock.mati_kyc_sdk.LoginError;
import com.matilock.mati_kyc_sdk.LoginResult;
import com.matilock.mati_kyc_sdk.Mati;
import com.matilock.mati_kyc_sdk.MatiCallback;
import com.matilock.mati_kyc_sdk.MatiCallbackManager;

import java.lang.ref.WeakReference;

import javax.annotation.Nullable;

public class MatiGlobalIdSdkModule extends ReactContextBaseJavaModule implements ActivityEventListener, MatiCallback {

    private final ReactApplicationContext reactContext;
    private MatiCallbackManager mCallbackManager = MatiCallbackManager.createNew();

    public static WeakReference<MatiGlobalIdSdkModule> weakReferenceMatiGlobalIdSdkModule;
    Callback mOnSuccess;
    Callback mOnCancel;
    Callback mOnError;

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
            }
        });
    }

    @ReactMethod
    public void setMatiCallback(Callback onSuccess, Callback onCancel, Callback onError) {
        mOnSuccess = onSuccess;
        mOnCancel = onCancel;
        mOnError = onError;
    }



    @ReactMethod
    public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
        // TODO: Implement some actually useful functionality
        callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
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
        mOnSuccess.invoke(pLoginResult.isSuccess(), pLoginResult.getIdentityId());
    }

    @Override
    public void onCancel() {
        mOnCancel.invoke();
    }

    @Override
    public void onError(LoginError pLoginError) {
        mOnError.invoke(pLoginError.getMessage());
    }
}

package com.reactlibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ReactImageView;
import com.matilock.mati_kyc_sdk.MatiLoginButton;

import java.lang.ref.WeakReference;

import javax.annotation.Nonnull;

public class MatiLoginButtonManager extends SimpleViewManager<MatiLoginButton> {
    public static final String REACT_CLASS = "MatiLoginButton";

    @Nonnull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Nonnull
    @Override
    protected MatiLoginButton createViewInstance(@Nonnull ThemedReactContext reactContext) {
        Context context = null;
        if(MatiGlobalIdSdkModule.weakReferenceMatiGlobalIdSdkModule!= null){
            context = MatiGlobalIdSdkModule.weakReferenceMatiGlobalIdSdkModule.get().getActivity();
        }
        if(context == null){
            context = reactContext;
        }
        return new MatiLoginButton(context);
    }

    @ReactProp(name = "Text", defaultFloat = 0f)
    public void setBorderRadius(MatiLoginButton view, String text) {
        view.setText(text);
    }

    @ReactProp(name = "flowId", defaultFloat = 0f)
    public void setFlowId(MatiLoginButton view, String text) {
        view.mFlowId = text;
    }
}

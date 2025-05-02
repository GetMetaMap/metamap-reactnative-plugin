package com.reactlibrary;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Collections;
import java.util.List;

/**
 * React‑Native package that exposes MetaMapRNSdkModule.
 */
public final class MetaMapRNSdkPackage implements ReactPackage {

    /**
     * Registers MetaMapRNSdkModule with React‑Native.
     */
    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull final ReactApplicationContext reactContext) {
        // Singleton list because this package only contains one module
        return Collections.singletonList(new MetaMapRNSdkModule(reactContext));
    }

    /**
     * This package does not export any custom view managers.
     */
    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull final ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
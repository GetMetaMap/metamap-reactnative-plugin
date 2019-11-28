# Mati React Native Mudule Update

## If you don't change the public interface of the sdk:

### React Native IOS:

-Nothing to do the new sdk will be added via cocoapod.
-Run `pod update` in the IOS platform folder.

### React Native Android:

-Update the Mati sdk version in "android/build.gradle"

```
implementation 'com.matilock:mati-global-id-sdk:{new version number}'
```

-Sync gradle to pull the latest version of the SDK

## If you need to update the sdk public interface:

### React Native javascript:

Nothing to do the public interface of the javascript abstraction will be generated at runtime. You can edit the visual component in MatiLoginButton.js

### React Native IOS:

You can edit the module implementation by editing react-native-mati-global-id-sdk/ios/MatiGlobalIdSdk.m

You can edit the button view module implementation by editing react-native-mati-global-id-sdk/ios/MatiLoginButton.m

Check link for more detail:
![alt text](https://facebook.github.io/react-native/docs/native-modules-ios)

### React Native Android:

You can edit the module implementation by editing
react-native-mati-global-id-sdk/android/src/main/java/com/reactlibrary/MatiGlobalIdSdkModule.java

You can edit the button view module implementation by editing react-native-mati-global-id-sdk/android/src/main/java/com/reactlibrary/MatiLoginButtonManager.java

Check link for more detail:
![alt text](https://facebook.github.io/react-native/docs/native-modules-android)

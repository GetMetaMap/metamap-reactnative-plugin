---
title: "React Native"
excerpt: "Add the Mati button to your React Native app."
slug: "react-native"
category: 61141a8437375100442f3d20
hidden: true
---

# Mati React Native Usage Guide

This is a guide to add Mati to Android and iOS for React Native versions 0.60.x and higher. These instructions includes:
* [Clean installation](#install-mati-for-react-native)
* [Reinstallation](#reinstall-mati-for-react-native)

## React Native Demo App

You can go to GitHub to download the [Mati React Native demo app](https://github.com/GetMati/mati-mobile-examples/tree/main/reactNativeDemoApp)

## Install Mati for React Native

In a terminal, use the following command to install Mati for React Native:

```bash
npm install https://github.com/MatiFace/react-native-mati-global-id-sdk.git --save
```

## EXPO Managed Workflow

The following instructions installs and add Expo to manage your workflow, and assumes you already have `[yarn](https://classic.yarnpkg.com/lang/en/docs/install/)` installed on your system:
1. Install the SDK:
	```bash
	yarn add https://github.com/MatiFace/react-native-mati-global-id-sdk.git
	```
2. Add the followling line to your `app.json` file:
	```json
	"plugins": ["react-native-mati-global-id-sdk"]
	```
3. Run EXPO for your platform:
	For iOS:
	```bash
	expo run:ios
	```
	For Android:
	```bash
	expo run:android
	```


### Platform Installation
* [Install for Android](#install-for-android)
* [Install for iOS](#install-for-ios)

#### Install for Android

For Android check that the `minSdkVersion` in `<YOUR_APP>/build.gradle` is &#8805;21, then sync your Gradle files


#### Install for iOS

1. From your podfile directory, run the following command to fetch the project dependencies:
   ```bash
   pod install
   ```
1. Add the following to your `info.plist` file to grant camera, microphone, and photo gallery access:

   ```xml
   <key>NSCameraUsageDescription</key>
   <string>Mati needs access to your Camera</string>
   <key>NSPhotoLibraryUsageDescription</key>
   <string>Mati needs access to your media library</string>
   <key>NSMicrophoneUsageDescription</key>
   <string>Mati needs access to your Microphone</string>
   ```
   _**Note**_ The voiceliveness feature requires microphone access (`NSMicrophoneUsageDescription`).
<br/>

   _**IMPORTANT**_ KNOWN ISSUE
   <details><summary><b>Click here to learn more about the podfile x86_64 issues for Flipper</b></summary>
   <p>

   You may see an x86_64 error similar to the following:
	   
   ```
   /Flipper/xplat/Flipper/FlipperRSocketResponder.cpp normal x86_64 c++ com.apple.compilers.llvm.clang.1_0.compiler
   ```
   This error is because React Native does not support Flipper (included by default), so you must remove Flipper.

   ##### Remove Flipper

   1. In your podfile:
      * Replace
          `use_flipper!` or `use-flipper!()`
          with
          `use_frameworks!`

      * For React Native v0.64+ replace:
        ```ruby
         post_install do |installer|
          react_native_post_install(installer)
         end
         ```
         with
         ```ruby
         post_install do |installer|
          react_native_post_install(installer)

           installer.pods_project.targets.each do |target|
             target.build_configurations.each do |config|
               config.build_settings['BUILD_LIBRARY_FOR_DISTRIBUTION'] = 'YES'
             end

             if (target.name&.eql?('FBReactNativeSpec'))
               target.build_phases.each do |build_phase|
                 if (build_phase.respond_to?(:name) && build_phase.name.eql?('[CP-User] Generate Specs'))
                   target.build_phases.move(build_phase, 0)
                 end
               end
             end
           end
         end
         ```

   1. Then run the following commands in your terminal:
    ```bash
    pod clean
    pod install
    ```

 ##### Learn More About the Issue
 * Flipper https://github.com/facebook/react-native/issues/29984
 * 0.64 FBReactNativeSpec https://github.com/facebook/react-native/issues/31034

 ##### Example Mati Podfiles on GitHub
 * [Podfile Version 0.60+](https://github.com/GetMati/mati-mobile-examples/blob/main/reactnative-podexamples/Podfile_063)
 * [Podfile Version 0.64](https://github.com/GetMati/mati-mobile-examples/blob/main/reactnative-podexamples/Podfile_064)

   </p>
   </details>
=======
  _**IMPORTANT**_ KNOWN ISSUE
  <details><summary><b>Click here to learn more about the podfile x86_64 issues for Flipper</b></summary>
  <p>

    You may see an x86_64 error similar to the following:
      ```
    /Flipper/xplat/Flipper/FlipperRSocketResponder.cpp normal x86_64 c++ com.apple.compilers.llvm.clang.1_0.compiler
    ```
    This error is because React Native does not support Flipper (included by default), so you must remove Flipper.

    ##### Remove Flipper

    1. In your podfile:
        * Replace
            `use_flipper!` or `use-flipper!()`
            with
            `use_frameworks!`

        * For React Native v0.64+ replace:
            ```ruby
            post_install do |installer|
              react_native_post_install(installer)
            end
            ```
            with
            ```ruby
            post_install do |installer|
              react_native_post_install(installer)

              installer.pods_project.targets.each do |target|
                target.build_configurations.each do |config|
                  config.build_settings['BUILD_LIBRARY_FOR_DISTRIBUTION'] = 'YES'
                end

                if (target.name&.eql?('FBReactNativeSpec'))
                  target.build_phases.each do |build_phase|
                    if (build_phase.respond_to?(:name) && build_phase.name.eql?('[CP-User] Generate Specs'))
                      target.build_phases.move(build_phase, 0)
                    end
                  end
                end
              end
            end
            ```

    1. Then run the following commands in your terminal:
        ```bash
        pod clean
        pod install
        ```

    ##### Learn More About the Issue
    * Flipper https://github.com/facebook/react-native/issues/29984
    * 0.64 FBReactNativeSpec https://github.com/facebook/react-native/issues/31034

    ##### Example Mati Podfiles on GitHub
    * [Podfile Version 0.60+](https://github.com/GetMati/mati-mobile-examples/blob/main/reactnative-podexamples/Podfile_063)
    * [Podfile Version 0.64](https://github.com/GetMati/mati-mobile-examples/blob/main/reactnative-podexamples/Podfile_064)

  </p>
  </details>

## Reinstall Mati for React Native

To reinstall Mati for React Native, you will need to:

1. Uninstall your current version of Mati:
    ```bash
    npm uninstall react-native-mati-global-id-sdk
    ```
1. Install the latest version of Mati:
    ```bash
    npm install https://github.com/MatiFace/react-native-mati-global-id-sdk.git --save
    ```
1. Update your IDE files:
  * Android: Sync your Gradle files
  * iOS: Clean and update your pod files:
      ```bash
      pod clean && pod update
      ```

## Example Mati React Native Implementation

The following is a example component.

```ruby
import React, {Component} from 'react';
import {
  NativeModules,
  NativeEventEmitter,
  Button,
  View
} from 'react-native';

import {
  MatiGlobalIdSdk,
} from 'react-native-mati-global-id-sdk';

export default class App extends Component {
  constructor() {
    super();
    console.log('Constructor Called.');
  }

  componentDidMount() {
	 //set listening callbacks
  	const MatiVerifyResult = new NativeEventEmitter(NativeModules.MatiGlobalIdSdk)
 	 MatiVerifyResult.addListener('verificationSuccess', (data) => console.log(data))
 	 MatiVerifyResult.addListener('verificationCanceled', (data) => console.log(data))
  }

  //call showFlow when button is clicked
  handleMatiClickButton = () => {

	 //set 3 params clientId (cant be null), flowId, metadata
  	  var yourMetadata = { param1: "value1", param2: "value2" }

   	 MatiGlobalIdSdk.showFlow("YOUR_CLIENT_ID", "YOUR_FLOW_ID", yourMetadata);
  }

  //Add button to view graph
  render() {
    return (
      <View
        style={{
          flex: 1,
          justifyContent: 'center',
          alignItems: 'center',
          backgroundColor: 'powderblue',
        }}>
        <Button onPress={this.handleMatiClickButton} title="Click here"/>
      </View>
    );
  }
}


```

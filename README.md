## Install MetaMap for React Native Expo 

https://www.npmjs.com/package/react-native-expo-metamap-sdk

## Install MetaMap for React Native

1. In a terminal, use the following command to install MetaMap for React Native:
```bash
npm i react-native-metamap-sdk
```

#### Install for Android

For Android check that the `minSdkVersion` in `<YOUR_APP>/build.gradle` is &#8805;21, then sync your Gradle files


#### Install for iOS

From your podfile directory, run the following command to fetch the project dependencies:
```bash
   pod install
   ```
Add the following to your `info.plist` file to grant camera, microphone, and photo gallery access: 
```bash
   <key>NSCameraUsageDescription</key>
   <string>MetaMap needs access to your Camera</string>
   <key>NSPhotoLibraryUsageDescription</key>
   <string>MetaMap needs access to your media library</string>
   <key>NSMicrophoneUsageDescription</key>
   <string>MetaMap needs access to your Microphone</string>
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

##### Example MetaMap Podfiles on GitHub
* [Podfile Version 0.60+](https://github.com/GetMati/mati-mobile-examples/blob/main/reactnative-podexamples/Podfile_063)
* [Podfile Version 0.64](https://github.com/GetMati/mati-mobile-examples/blob/main/reactnative-podexamples/Podfile_064)

  </p>
  </details>
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

    ##### Example MetaMap Podfiles on GitHub
    * [Podfile Version 0.60+](https://github.com/GetMati/mati-mobile-examples/blob/main/reactnative-podexamples/Podfile_063)
    * [Podfile Version 0.64](https://github.com/GetMati/mati-mobile-examples/blob/main/reactnative-podexamples/Podfile_064)

  </p>
  </details>


2.1 The following is an example of the class Component.

```bash
import React, {Component} from 'react';
import {
  NativeModules,
  NativeEventEmitter,
  Button,
  View
} from 'react-native';

import {
  MetaMapRNSdk,
} from 'react-native-metamap-sdk';

export default class App extends Component {
  constructor() {
    super();
    console.log('Constructor Called.');
  }

  componentDidMount() {
	 //set listening callbacks
  	const MetaMapVerifyResult = new NativeEventEmitter(NativeModules.MetaMapRNSdk)
 	 MetaMapVerifyResult.addListener('verificationSuccess', (data) => console.log(data))
 	 MetaMapVerifyResult.addListener('verificationCanceled', (data) => console.log(data))
  }

  //call showFlow when button is clicked
  handleMetaMapClickButton = () => {

	 //set 3 params clientId (cant be null), flowId, metadata
  	  var yourMetadata = { param1: "value1", param2: "value2" }

   	 MetaMapRNSdk.showFlow("YOUR_CLIENT_ID", "YOUR_FLOW_ID", yourMetadata);
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
        <Button onPress={this.handleMetaMapClickButton} title="Click here"/>
      </View>
    );
  }
}
```

2.2 The following is an example of the Function Component.

```bash
import React, {Component, useEffect} from 'react';
import {
  NativeModules,
  NativeEventEmitter,
  Button,
  View
} from 'react-native';

import {
  MetaMapRNSdk,
} from 'react-native-metamap-sdk';


function App(props) {

    useEffect(() => {
     	const MetaMapVerifyResult = new NativeEventEmitter(NativeModules.MetaMapRNSdk)
     	MetaMapVerifyResult.addListener('verificationSuccess', (data) => console.log(data))
     	MetaMapVerifyResult.addListener('verificationCanceled', (data) => console.log(data))
    })
    const handleMetaMapClickButton = (props) => {

            //set 3 params clientId (cant be null), flowId, metadata
         var yourMetadata = { param1: "value1", param2: "value2" }
       	 MetaMapRNSdk.showFlow("610b96fb7cc893001b135505", "611101668083a1001b13cc80", yourMetadata);
      }

    return (
          <View
            style={{
              flex: 1,
              justifyContent: 'center',
              alignItems: 'center',
              backgroundColor: 'powderblue',
            }}>
            <Button onPress = {() => handleMetaMapClickButton()}  title="Click here"/>
          </View>
        );
}
export default App;
```



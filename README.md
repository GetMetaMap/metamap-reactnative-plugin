# Mati module for Android And IOS SDK documentation

## Recommended version of React Native is 0.60.x +

## Start
Add the Mati SDK Plugin to your project by command

INSTALL: npm install https://github.com/MatiFace/react-native-mati-global-id-sdk.git --save

UNINSTALL: npm uninstall react-native-mati-global-id-sdk

## For iOS platform please check information at end of readme

## Mati SDK initialization

The following is a example component.

```
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
#EXAMPLE
Please check examples here:
https://github.com/GetMati/mati-mobile-examples/tree/main/reactNativeDemoApp

## IOS build

In the IOS platform find the Podfile file. 

### The targeted OS version should be a minimum of 11.4.
Run "pod install" to fetch the project dependencies.

The following permissions are needed to capture video and access the photo gallery.

### Info.plist – IMPORTANT add this descriptions to your project

For voiceliveness feature please add NSMicrophoneUsageDescription

```
<key>NSCameraUsageDescription</key>
<string>Mati needs access to your Camera</string>
<key>NSPhotoLibraryUsageDescription</key>
<string>Mati needs access to your media library</string>
<key>NSMicrophoneUsageDescription</key>
<string>Mati needs access to your Microphone</string>
```

# Known issue – PODFILE (x86_64 issues) – FLIPPER
In react native Flipper doesnt have support yet

So you have to remove it from yours project. (It is adding by default)

### If you have this error:
/Flipper/xplat/Flipper/FlipperRSocketResponder.cpp normal x86_64 c++ com.apple.compilers.llvm.clang.1_0.compiler or other x86_64 issues

### You have to make changes in Podfile:
It's because of use_flipper in Podfile for iOS project.

#### So you have to remove it, replace this:

use_flipper! or  use_flipper!()

#### to this:

use_frameworks!

### If you are use lates react native 0.64 add this lines to the end of Podfile

##### replace this:
  post_install do |installer|
    react_native_post_install(installer)
  end
  
#### to this:

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

## after this use commands:
pod clean 
pod install

#### More info about this issues 
Flipper https://github.com/facebook/react-native/issues/29984 

0.64 FBReactNativeSpec https://github.com/facebook/react-native/issues/31034

# Links to right Podfiles
0.60+ – https://github.com/GetMati/mati-mobile-examples/blob/main/reactnative-podexamples/Podfile_063

0.64 – https://github.com/GetMati/mati-mobile-examples/blob/main/reactnative-podexamples/Podfile_064

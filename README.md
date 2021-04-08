# Mati module for Android And IOS SDK documentation

## Recommended version of React Native is 0.63.*

### Use this command to install
react-native init MyNewApp --version 0.63.0 

0.64 – doesnt support because have troubles with arm (apple)

## Start
Create a new React Native project.
Add the SDK module to your package.json by command

npm install https://github.com/MatiFace/react-native-mati-global-id-sdk.git --save

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

  //set 3 params clientId (cant be null), flowId, metadata
  MatiGlobalIdSdk.setParams("YOUR_CLIENT_ID", "YOUR_FLOW_ID", YOUR_METADATA);

  //set listening callbacks
  const MatiVerifyResult = new NativeEventEmitter(NativeModules.MatiGlobalIdSdk)
  MatiVerifyResult.addListener('verificationSuccess', (data) => console.log(data))
  MatiVerifyResult.addListener('verificationCanceled', (data) => console.log(data))
  }

  //call showFlow when button is clicked
  handleMatiClickButton = () => {
    MatiGlobalIdSdk.showFlow();
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

## IOS build

In the IOS platform find the Podfile file. The targeted OS version should be a minimum of 11.4. Run "pod install" to fetch the project dependencies.

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

## Known issue
### If you have this error:
/Flipper/xplat/Flipper/FlipperRSocketResponder.cpp normal x86_64 c++ com.apple.compilers.llvm.clang.1_0.compiler

### You have to make changes in Podfile:
It's because of use_flipper in Podfile for iOS project.
use_flipper!

So, I was needed to indicate Flipper-Folly version with use_flipper as
use_flipper!({ 'Flipper-Folly' => '2.3.0' })

### after this use commands:
pod clean 
pod install


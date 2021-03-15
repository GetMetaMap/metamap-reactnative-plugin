# Mati module for Android And IOS SDK documentation

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

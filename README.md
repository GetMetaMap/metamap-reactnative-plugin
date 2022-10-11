---
title: "React Native"
excerpt: "Add the MetaMap button to your React Native app."
slug: "react-native"
category: 61ae8e8dba577a0010791480
---


| LTS version (Recommended for most users): | Current Version(Latest features) |
|-------------------------------------------|----------------------------------|
| 4.3.0                                     | 4.5.2                            |

## Install MetaMap for React Native Expo 

https://www.npmjs.com/package/react-native-expo-metamap-sdk

## Install MetaMap for React Native

1. In a terminal, use the following command to install MetaMap for React Native:
```bash
npm i react-native-metamap-sdk@4.3.0  
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
   
   <key>NSLocationWhenInUseUsageDescription</key>
   <string>MetaMap will use your location information to provide best possible verification experience.</string>
	
   <key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
   <string>MetaMap will use your location information to provide best possible verification experience.</string>
	
   <key>NSLocationAlwaysUsageDescription</key>
   <string>MetaMap will use your location information to provide best possible verification experience.</string>
   ```

2.1 The following is an example of the class Component:

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

2.2 The following is an example of the Function Component:

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
## Metadata Usage

Metadata is an additional optional parameter that you can receive using a webhook after passing verification:

Set the Language:
```bash
yourMetadata: {"fixedLanguage": "es"}
```

Set the Button Color
```bash
yourMetadata: {"buttonColor": "hexColor"}
```

Set the Title color of the button:
```bash
yourMetadata: {"buttonTextColor": "hexColor"}
```

Set identity Id as parameter for re-verification:
```bash
metadata: {"identityId": "value"}
   ```

## Some error codes you may get during integration

`402` - MetaMap services are not paid: please contact your customer success manager

`403` - MetaMap credentials issues: please check your client id and MetaMap id

---
title: "React Native"
excerpt: "Add the MetaMap button to your React Native app."
slug: "react-native"
category: 61ae8e8dba577a0010791480
---


| LTS version (Recommended for most users): | Current Version(Latest features) |
|-------------------------------------------|----------------------------------|
| 5.1.6                                     | 5.1.6                            |

## Install MetaMap for React Native

In a terminal, use the following command to install MetaMap for React Native, where `<version_number>` is either the LTS or current version:

```bash
npm i react-native-metamap-sdk@<version_number>  
```
Then install for [Android](#install-for-android) or [iOS](#install-for-ios)

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
   <details>
   <summary>Example MetaMap React Native Expo Implementation</summary>
   <p>

The following is an example of the class Component:

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
  	  var metaData = { param1: "value1", param2: "value2" }

   	 MetaMapRNSdk.showFlow("YOUR_CLIENT_ID", "YOUR_FLOW_ID", metaData);
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

The following is an example of the Function Component:

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
         var metaData = { param1: "value1", param2: "value2" }
       	 MetaMapRNSdk.showFlow("YOUR_CLIENT_ID", "YOUR_FLOW_ID", metaData);
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
</p>
</details>

## Metadata Usage

Metadata is an additional optional parameter that can be used to replace certain settings:

### Set the Language:
By default the SDK language is set to "en" but it is editable to the language from the list: "es", "fr", "pt", "ru", "tr", "de", "it", "pl", "th".
```bash
metaData: {"fixedLanguage": "value"}
```

### Set the Button Color
By default main button color is white but it is editable by using hex Color format "hexColor".
```bash
metaData: {"buttonColor": "value"}
```

### Set the Title color of the button:
By default main button title color is black but it is editable by using hex Color format "hexColor".
```bash
metaData: {"buttonTextColor": "hexColor"}
```

### Set identity Id as parameter for re-verification:
```bash
metadata: {"identityId": "value"}
   ```

### Set encryption Configuration Id as parameter for encrypting data.
```bash
metaData: ["encryptionConfigurationId": "value"]
   ```


### Set customization fonts as parameter.
to add custom fonts, the project needs to have these font files, otherwise SDK will use default fonts:
```bash
metadata: ["regularFont": "REGULAR_FONT_NAME.ttf", "boldFont":  "BOLD_FONT_NAME.ttf"]
   ```

## Some error codes you may get during integration

`402` - MetaMap services are not paid: please contact your customer success manager

`403` - MetaMap credentials issues: please check your client id and MetaMap id

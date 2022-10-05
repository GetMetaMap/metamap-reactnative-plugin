---
title: "React Native Expo"
excerpt: "Add the MetaMap button to your React Native Expo app."
slug: "react-native-expo"
category: 61ae8e8dba577a0010791480
---

# MetaMap React Expo Native Usage Guide

This plugin uses the latest versions of the MetaMap iOS and Android SDKs. For more information on the latest native SDK versions, go to:
* [Android](https://docs.getmati.com/docs/android-changelog)
* [iOS](https://docs.getati.com/docs/ios-changelog)

<!--For changes to the plugin, go to the [changelog page](https://docs.getmati.com/docs/react-native-expo-changelog)-->
## Install MetaMap for React Native Expo 

The following instructions installs and add Expo to manage your workflow, and assumes you already have [`yarn`](https://classic.yarnpkg.com/lang/en/docs/install/) installed on your system:
1. Install the SDK:
	```bash
	npm i react-native-expo-metamap-sdk
	```
2. Add the following line to your `app.json` file:
	```JSON
	"plugins":["react-native-metamap-sdk"]
	```
3. Run EXPO for your platform:
	For iOS:
	```bash
	expo run: ios
	```
	For Android:
	```bash
	expo run: android
	```


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
   <string>MetaMap needs access to your Camera</string>
   <key>NSPhotoLibraryUsageDescription</key>
   <string>MetaMap needs access to your media library</string>
   <key>NSMicrophoneUsageDescription</key>
   <string>MetaMap needs access to your Microphone</string>
	 <key>NSLocationAlwaysUsageDescription</key>
   <string>MetaMap will use your location information to provide best possible verification experience.</string>
   ```
<details>
<summary>Example MetaMap React Native Expo Implementation</summary>
<p>
The following is an example of the class Component.

```ruby
import React, {Component} from 'react';
import {
  NativeModules,
  NativeEventEmitter,
  Button,
  View
} from 'react-native';

import {
  MetaMapRNSdk,
} from 'react-native-expo-metamap-sdk';

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

The following is an example of the Function Component.

```ruby
import React, {Component, useEffect} from 'react';
import {
  NativeModules,
  NativeEventEmitter,
  Button,
  View
} from 'react-native';

import {
  MetaMapRNSdk,
} from 'react-native-expo-metamap-sdk';


function App(props) {

    useEffect(() => {
     	const MetaMapVerifyResult = new NativeEventEmitter(NativeModules.MetaMapRNSdk)
     	MetaMapVerifyResult.addListener('verificationSuccess', (data) => console.log(data))
     	MetaMapVerifyResult.addListener('verificationCanceled', (data) => console.log(data))
    })
    const handleMetaMapClickButton = (props) => {

            //set 3 params clientId (cant be null), flowId, metadata
         var yourMetadata = { param1: "value1", param2: "value2" }
       	 MetaMapRNSdk.showFlow("YOUR_CLIENT_ID", "YOUR_FLOW_ID", yourMetadata);
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

## Some error codes you may see during integration

`402` - MetaMap services are not paid: please contact your customer success manager

`403` - MetaMap credentials issues: please check your client id and MetaMap id

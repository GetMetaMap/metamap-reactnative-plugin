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
  StyleSheet,
  View
} from 'react-native';


import {
  MatiLoginButton,
  MatiGlobalIdSdk,
} from 'react-native-mati-global-id-sdk';

const styles = StyleSheet.create({
  matiButtonStyle: {
    width: 300,
    ...Platform.select({
      ios: {
        height: 60,
      },
      android: {
        height: 40,
      },
    }),
  },
});

export default class App extends Component {
  constructor() {
    super();
    console.log('Constructor Called.');

	//Init SDk
	MatiGlobalIdSdk.init('your client ID here');

	//Send metadata
    MatiGlobalIdSdk.metadata({key: 'value'});
  }

  componentDidMount() {

	//register to login callback
    MatiGlobalIdSdk.setMatiCallback((isSuccess, identityIdOrError) => {
      if (isSuccess) {
        console.log(
          'isSuccess:' + isSuccess + ' identityId:' + identityIdOrError,
        );
      } else {
        console.log('isSuccess:' + isSuccess + ' error:' + identityIdOrError);
      }
    });
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
        //If you want to work with default flow.
        <MatiLoginButton style={styles.matiButtonStyle} Text="Click Here" />
        //If you want to work with specific flow, please add "flowId" parameter.
        <MatiLoginButton style={styles.matiButtonStyle} Text="Click Here" flowId="5ea2f0ce6zz8ad001bb12309" />
      </View>
    );
  }
}

```

## IOS build

In the IOS platform find the Podfile file. The targeted OS version should be a minimum of 9. Run "pod install" to fetch the project dependencies.

The following permissions are needed to capture video and access the photo gallery.

### Info.plist – IMPORTANT add this descriptions to your project

```
<key>NSCameraUsageDescription</key>
<string>Mati needs access to your Camera</string>
<key>NSPhotoLibraryUsageDescription</key>
<string>Mati needs access to your media library</string>
```

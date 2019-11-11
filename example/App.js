/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  NativeModules,
  requireNativeComponent,
} from 'react-native';

// import {
//   Header,
//   LearnMoreLinks,
//   Colors,
//   DebugInstructions,
//   ReloadInstructions,
// } from 'react-native/Libraries/NewAppScreen';

import {
  MatiLoginButton,
  MatiGlobalIdSdk,
} from 'react-native-mati-global-id-sdk';

export default class App extends Component {
  constructor() {
    super();
    console.log('Constructor Called.');
    MatiGlobalIdSdk.init('5dc09bd3047ea0001c4b20ba');
  }

  render() {
    return (
      <View
        style={{
          flex: 1,
          justifyContent: 'center',
          backgroundColor: 'powderblue',
        }}>
        <MatiLoginButton style={{width: 300, height: 60}} Text="Click Here" />
      </View>
    );
  }
}

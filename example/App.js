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
    MatiGlobalIdSdk.init('5dc09bd3047ea0001c4b20ba');
    MatiGlobalIdSdk.metadata({key: 'value'});
  }

  componentDidMount() {
    MatiGlobalIdSdk.setMatiCallback(
      (isSuccess, identityId) =>{
        console.log('onSuccess');
      },
      (cancel) =>{
        console.log('cancel:' + cancel);
      },
      (loginError) =>{
        console.log('onError:' + loginError);
      }
    );
  }

  render() {
    return (
      <View
        style={{
          flex: 1,
          justifyContent: 'center',
          alignItems: 'center',
          backgroundColor: 'powderblue',
        }}>
        <MatiLoginButton style={styles.matiButtonStyle} Text="Click Here" />
      </View>
    );
  }
}

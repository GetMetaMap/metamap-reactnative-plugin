/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {StyleSheet, View} from 'react-native';

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

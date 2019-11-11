# react-native-mati-global-id-sdk

## Getting started

`$ npm install react-native-mati-global-id-sdk --save`

### Mostly automatic installation

`$ react-native link react-native-mati-global-id-sdk`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-mati-global-id-sdk` and add `MatiGlobalIdSdk.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libMatiGlobalIdSdk.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.reactlibrary.MatiGlobalIdSdkPackage;` to the imports at the top of the file
  - Add `new MatiGlobalIdSdkPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-mati-global-id-sdk'
  	project(':react-native-mati-global-id-sdk').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-mati-global-id-sdk/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-mati-global-id-sdk')
  	```


## Usage
```javascript
import MatiGlobalIdSdk from 'react-native-mati-global-id-sdk';

// TODO: What to do with the module?
MatiGlobalIdSdk;
```

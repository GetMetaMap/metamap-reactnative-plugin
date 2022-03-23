import { NativeModules } from 'react-native';

const { MetaMapRNSdk } = NativeModules;

module.exports = {
	get MetaMapRNSdk() {
		return MetaMapRNSdk;
	}
};

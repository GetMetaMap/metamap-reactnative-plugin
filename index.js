import { NativeModules } from 'react-native';

const { MatiGlobalIdSdk } = NativeModules;

module.exports = {
	get MatiGlobalIdSdk() {
		return MatiGlobalIdSdk;
	}
};

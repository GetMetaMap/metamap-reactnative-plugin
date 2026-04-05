import { NativeModules, TurboModuleRegistry } from 'react-native';

const legacyModule = NativeModules.MetaMapRNSdk;
const turboModule = TurboModuleRegistry.get('MetaMapRNSdk');
const MetaMapRNSdk = turboModule ?? legacyModule;

function getMetaMapModuleOrThrow() {
	if (MetaMapRNSdk) {
		return MetaMapRNSdk;
	}

	throw new Error(
		'react-native-metamap-sdk: Native module "MetaMapRNSdk" is not linked. ' +
		'Run platform installation steps and rebuild the app. ' +
		'For iOS, run "pod install" in the ios directory.'
	);
}

module.exports = {
	get MetaMapRNSdk() {
		return getMetaMapModuleOrThrow();
	}
};

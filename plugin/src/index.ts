import {
  withPlugins,
  AndroidConfig,
  withProjectBuildGradle,
  ConfigPlugin,
WarningAggregator,
ExportedConfigWithProps,
 withDangerousMod,
  createRunOncePlugin,
} from '@expo/config-plugins';
import { ExpoConfig } from '@expo/config-types';
import path from 'path';
import fs from 'fs';
import { InstallationPage } from './constants';

const pkg = require('react-native-expo-metamap-sdk/package.json');

const CAMERA_USAGE = 'Allow $(PRODUCT_NAME) to access your camera';
const MICROPHONE_USAGE = 'Allow $(PRODUCT_NAME) to access your microphone';

async function readFileAsync(path: string): Promise<string> {
  return fs.promises.readFile(path, 'utf8');
}

async function saveFileAsync(path: string, content: string): Promise<void> {
  return fs.promises.writeFile(path, content, 'utf8');
}

function addLines(content: string, find: string | RegExp, offset: number, toAdd: string[]) {
  const lines = content.split('\n');

  let lineIndex = lines.findIndex((line) => line.match(find));

  for (const newLine of toAdd) {
    if (!content.includes(newLine)) {
      lines.splice(lineIndex + offset, 0, newLine);
      lineIndex++;
    }
  }

  return lines.join('\n');
}

// Because we need the package to be added AFTER the React and Google maven packages, we create a new allprojects.
// It's ok to have multiple allprojects.repositories, so we create a new one since it's cheaper than tokenizing
// the existing block to find the correct place to insert our camera maven.
const gradleMaven =
  'allprojects { repositories { maven { url "$rootDir/../node_modules/react-native-expo-metamap-sdk/android/maven" } } }';

const withAndroidCameraGradle: ConfigPlugin = (config) => {
  return withProjectBuildGradle(config, (config) => {
    if (config.modResults.language === 'groovy') {
      config.modResults.contents = setGradleMaven(config.modResults.contents);
    } else {
      throw new Error('Cannot add camera maven gradle because the build.gradle is not groovy');
    }
    return config;
  });
};

export function setGradleMaven(buildGradle: string): string {
  if (buildGradle.includes('react-native-expo-metamap-sdk/android/maven')) {
    return buildGradle;
  }

  return buildGradle + `\n${gradleMaven}\n`;
}

const withCamera: ConfigPlugin<{
  cameraPermission?: string;
  microphonePermission?: string;
} | void> = (config, { cameraPermission, microphonePermission } = {}) => {
  if (!config.ios) config.ios = {};
  if (!config.ios.infoPlist) config.ios.infoPlist = {};
  config.ios.infoPlist.NSCameraUsageDescription =
    cameraPermission || config.ios.infoPlist.NSCameraUsageDescription || CAMERA_USAGE;
  config.ios.infoPlist.NSMicrophoneUsageDescription =
    microphonePermission || config.ios.infoPlist.NSMicrophoneUsageDescription || MICROPHONE_USAGE;

  return withPlugins(config, [
    [
      AndroidConfig.Permissions.withPermissions,
      [
        'android.permission.CAMERA',
        // Optional
        'android.permission.RECORD_AUDIO',
      ],
    ],
    withAndroidCameraGradle,
  ]);
};

async function editPodfile(config: ExportedConfigWithProps, action: (podfile: string) => string) {
  const podfilePath = path.join(config.modRequest.platformProjectRoot, 'Podfile');
  try {
    const podfile = action(await readFileAsync(podfilePath));
    return await saveFileAsync(podfilePath, podfile);
  } catch (e) {
    WarningAggregator.addWarningIOS(
      'expo-dev-menu',
      `Couldn't modified AppDelegate.m - ${e}.
See the expo-dev-client installation instructions to modify your AppDelegate manually: ${InstallationPage}`
    );
  }
}

const withDevMenuPodfile: ConfigPlugin = (config) => {
  return withDangerousMod(config, [
    'ios',
    async (config) => {
      await editPodfile(config, (podfile) => {
	
        podfile = podfile.replace("platform :ios, '11.0'", "platform :ios, '11.4'");
        // Match both variations of Ruby config:
        // unknown: pod 'expo-dev-menu', path: '../node_modules/expo-dev-menu', :configurations => :debug
        // Rubocop: pod 'expo-dev-menu', path: '../node_modules/expo-dev-menu', configurations: :debug
        if (
          !podfile.match(
            /pod ['"]expo-dev-menu['"],\s?path: ['"][^'"]*node_modules\/expo-dev-menu['"],\s?:?configurations:?\s(?:=>\s)?:debug/
          )
        ) {
          const packagePath = path.dirname(require.resolve('expo-dev-menu/package.json'));
          const relativePath = path.relative(config.modRequest.platformProjectRoot, packagePath);
          podfile = addLines(podfile, 'use_react_native', 0, [
            `  pod 'expo-dev-menu', path: '${relativePath}', :configurations => :debug`,
          ]);
        }
        return podfile;
      });
      return config;
    },
  ]);
};


const index = (config: ExpoConfig) => {
config = withDevMenuPodfile(config);
config = withCamera(config);
return config;
};


export default createRunOncePlugin(index, pkg.name, pkg.version);
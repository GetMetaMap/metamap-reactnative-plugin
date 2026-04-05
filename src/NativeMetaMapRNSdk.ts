import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export type MetadataValue = string | number | boolean | null | MetadataObject | MetadataValue[];
export interface MetadataObject {
  [key: string]: MetadataValue;
}

export interface Spec extends TurboModule {
  showFlow(clientId: string, flowId?: string | null, metadata?: MetadataObject | null): void;
  showFlowWithConfigurationId(
    clientId: string,
    flowId?: string | null,
    metadata?: MetadataObject | null,
    configurationId?: string | null,
    encryptionConfigurationId?: string | null
  ): void;
}

export default TurboModuleRegistry.get<Spec>('MetaMapRNSdk');

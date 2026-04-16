import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import type { UnsafeObject } from 'react-native/Libraries/Types/CodegenTypes';

/**
 * Metadata is an arbitrary key/value map at runtime. TurboModule codegen does
 * not support TypeScript index signatures (`{ [key: string]: ... }`), so we use
 * `UnsafeObject` here for compatibility with React Native's codegen.
 */
export interface Spec extends TurboModule {
  showFlow(clientId: string, flowId?: string | null, metadata?: UnsafeObject | null): void;
  showFlowWithConfigurationId(
    clientId: string,
    flowId?: string | null,
    metadata?: UnsafeObject | null,
    configurationId?: string | null,
    encryptionConfigurationId?: string | null
  ): void;
}

export default TurboModuleRegistry.get<Spec>('MetaMapRNSdk');

/**
 * Metadata accepted by `showFlow` / `showFlowWithConfigurationId`.
 *
 * All entries are forwarded to the native MetaMap SDK as-is.
 * Well-known keys are listed below; any additional key/value pairs are also
 * forwarded and accessible inside your flow configuration.
 */
export interface MetaMapMetadata {
  /**
   * Pre-fill the phone-number field on the phone-verification step.
   * Pass the national number without country prefix (e.g. `"5551234567"`).
   */
  phoneNumber?: string;

  /**
   * ISO 3166-1 alpha-2 country code used as the default country on the
   * phone-verification step (e.g. `"US"`, `"MX"`, `"GB"`).
   */
  phoneCountryCode?: string;

  /** Any other key/value pairs supported by the MetaMap SDK. */
  [key: string]: unknown;
}

/**
 * The MetaMap native module interface.
 *
 * Obtain the module via the package's default export (see `index.js`):
 * ```ts
 * import { MetaMapRNSdk } from 'react-native-metamap-sdk';
 * ```
 */
export interface MetaMapRNSdkInterface {
  /**
   * Launch the MetaMap verification flow.
   *
   * @param clientId  Your MetaMap client ID.
   * @param flowId    The flow (template) ID to run, or `null` for the default.
   * @param metadata  Optional metadata forwarded to the SDK.
   *
   * @example
   * ```ts
   * MetaMapRNSdk.showFlow('my-client-id', 'my-flow-id', {
   *   phoneNumber: '5551234567',
   *   phoneCountryCode: 'US',
   * });
   * ```
   */
  showFlow(
    clientId: string,
    flowId?: string | null,
    metadata?: MetaMapMetadata | null,
  ): void;

  /**
   * Launch the MetaMap verification flow with an optional configuration / encryption ID.
   *
   * @param clientId                  Your MetaMap client ID.
   * @param flowId                    The flow (template) ID, or `null` for the default.
   * @param metadata                  Optional metadata forwarded to the SDK.
   * @param configurationId           Optional configuration override ID.
   * @param encryptionConfigurationId Optional encryption configuration ID.
   */
  showFlowWithConfigurationId(
    clientId: string,
    flowId?: string | null,
    metadata?: MetaMapMetadata | null,
    configurationId?: string | null,
    encryptionConfigurationId?: string | null,
  ): void;
}

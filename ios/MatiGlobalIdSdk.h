#import <React/RCTEventEmitter.h>
#import <React/RCTBridgeModule.h>

#import <MetaMapSDK/MetaMapSDK.h>

@interface MatiGlobalIdSdk : RCTEventEmitter <RCTBridgeModule, MetaMapButtonResultDelegate>

@end

#import <React/RCTEventEmitter.h>
#import <React/RCTBridgeModule.h>

#import <MetaMapSDK/MetaMapSDK.h>

@interface MetaMapRNSdk : RCTEventEmitter <RCTBridgeModule, MetaMapButtonResultDelegate>

@end

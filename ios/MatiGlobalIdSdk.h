#import <React/RCTEventEmitter.h>
#import <React/RCTBridgeModule.h>

#import <MatiSDK/MatiSDK.h>

@interface MatiGlobalIdSdk : RCTEventEmitter <RCTBridgeModule, MatiButtonResultDelegate>

@end

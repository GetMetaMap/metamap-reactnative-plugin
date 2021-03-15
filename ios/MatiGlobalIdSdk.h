#import <React/RCTEventEmitter.h>
#import <React/RCTBridgeModule.h>

#import <MatiSDK/MatiSDK.h>

@interface MatiGlobalIdSdk : RCTEventEmitter <RCTBridgeModule, MatiButtonResultDelegate>

@property (nonatomic, strong) MatiButton *matiButton;

@end

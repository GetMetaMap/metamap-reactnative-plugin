#import "MetaMapRNSdk.h"
#import <React/RCTEventEmitter.h>
#import <React/RCTUtils.h>

static NSString *const kEventVerificationSuccess  = @"verificationSuccess";
static NSString *const kEventVerificationCanceled = @"verificationCanceled";
static NSString *const kEventVerificationStarted  = @"verificationStarted";

@implementation MetaMapRNSdk {
  BOOL _hasListeners;
  NSInteger _listenerCount;
}

RCT_EXPORT_MODULE()

#pragma mark - RN 0.71+ listener contract
- (void)addListener:(NSString *)eventName
{
  [super addListener:eventName];
  _listenerCount += 1;
  _hasListeners = YES;
}

- (void)removeListeners:(double)count
{
  [super removeListeners:count];
  _listenerCount -= (NSInteger)count;
  if (_listenerCount <= 0) {
    _listenerCount = 0;
    _hasListeners = NO;
  }
}

#pragma mark - Required overrides
+ (BOOL)requiresMainQueueSetup
{
  // We show a native UI immediately; initialise on main.
  return YES;
}

- (NSArray<NSString *> *)supportedEvents
{
  return @[
    kEventVerificationSuccess,
    kEventVerificationCanceled,
    kEventVerificationStarted
  ];
}

- (void)invalidate
{
  // Called when the bridge is deallocated (e.g. hot reload)
  _hasListeners = NO;
  _listenerCount = 0;
}

#pragma mark - React‑exposed API
RCT_EXPORT_METHOD(showFlow:(NSString *)clientId
                  flowId:(NSString *)flowId
                  metadata:(NSDictionary *)metadata)
{
  [self startFlowWithClientId:clientId
                       flowId:flowId
              configurationId:nil
    encryptionConfigurationId:nil
                     metadata:metadata];
}

RCT_EXPORT_METHOD(showFlowWithConfigurationId:(NSString *)clientId
                  flowId:(NSString *)flowId
                  configurationId:(NSString *)configurationId
                  encryptionConfigurationId:(NSString *)encryptionConfigurationId
                  metadata:(NSDictionary *)metadata)
{
  [self startFlowWithClientId:clientId
                       flowId:flowId
              configurationId:configurationId
    encryptionConfigurationId:encryptionConfigurationId
                     metadata:metadata];
}

#pragma mark - Internal helper
- (void)startFlowWithClientId:(NSString *)clientId
                       flowId:(NSString *)flowId
              configurationId:(NSString *)configurationId
    encryptionConfigurationId:(NSString *)encryptionConfigurationId
                     metadata:(NSDictionary *)metadata
{
  dispatch_async(dispatch_get_main_queue(), ^{
    NSMutableDictionary *mutableMetadata =
        metadata ? [metadata mutableCopy] : [NSMutableDictionary dictionary];
    mutableMetadata[@"sdkType"] = @"react-native-ios";

    [MetaMap.shared showMetaMapFlowWithClientId:clientId
                                         flowId:flowId
                                configurationId:configurationId
                      encryptionConfigurationId:encryptionConfigurationId
                                       metadata:mutableMetadata];

    [MetaMapButtonResult shared].delegate = self;
  });
}

#pragma mark - MetaMapButtonResultDelegate
- (void)verificationCreatedWithIdentityId:(NSString *)identityId
                           verificationID:(NSString *)verificationID
{
  if (_hasListeners) {
    [self sendEventWithName:kEventVerificationStarted
                       body:@{@"identityId": identityId ?: [NSNull null],
                              @"verificationId": verificationID ?: [NSNull null]}];
  }
}

- (void)verificationSuccessWithIdentityId:(NSString *)identityId
                           verificationID:(NSString *)verificationID
{
  if (_hasListeners) {
    [self sendEventWithName:kEventVerificationSuccess
                       body:@{@"identityId": identityId ?: [NSNull null],
                              @"verificationId": verificationID ?: [NSNull null]}];
  }
}

- (void)verificationCancelledWithIdentityId:(NSString *)identityId
                             verificationID:(NSString *)verificationID
{
  if (_hasListeners) {
    [self sendEventWithName:kEventVerificationCanceled
                       body:@{@"identityId": identityId ?: [NSNull null],
                              @"verificationId": verificationID ?: [NSNull null]}];
  }
}
@end

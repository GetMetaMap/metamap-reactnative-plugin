#import "MetaMapRNSdk.h"
#import <React/RCTUtils.h>

static NSString *const kEventVerificationSuccess = @"verificationSuccess";
static NSString *const kEventVerificationCanceled = @"verificationCanceled";
static NSString *const kEventVerificationStarted = @"verificationStarted";

@interface MetaMapRNSdk () <MetaMapButtonResultDelegate>
@property(nonatomic, assign) BOOL hasListeners;
@end

@implementation MetaMapRNSdk

RCT_EXPORT_MODULE()

#pragma mark - React Native API

RCT_EXPORT_METHOD(showFlow
:(NSString *)
clientId
        flowId
:(NSString *)
flowId
        metadata
:(NSDictionary *)metadata)
{
    [self startFlowWithClientId:clientId
                         flowId:flowId
                configurationId:nil
      encryptionConfigurationId:nil
                       metadata:metadata];
}

RCT_EXPORT_METHOD(showFlowWithConfigurationId
:(NSString *)
clientId
        flowId
:(NSString *)
flowId
        configurationId
:(NSString *)
configurationId
        encryptionConfigurationId
:(NSString *)
encryptionConfigurationId
        metadata
:(NSDictionary *)metadata)
{
    [self startFlowWithClientId:clientId
                         flowId:flowId
                configurationId:configurationId
      encryptionConfigurationId:encryptionConfigurationId
                       metadata:metadata];
}

#pragma mark - Private helpers

- (void)startFlowWithClientId:(NSString *)clientId
                       flowId:(NSString *)flowId
              configurationId:(NSString *)configurationId
    encryptionConfigurationId:(NSString *)encryptionConfigurationId
                     metadata:(NSDictionary *)metadata {
    dispatch_async(dispatch_get_main_queue(), ^{
        NSMutableDictionary *mutableMetadata = metadata
                                               ? [metadata mutableCopy]
                                               : [NSMutableDictionary dictionary];
        mutableMetadata[@"sdkType"] = @"react-native-ios";

        [MetaMap.shared showMetaMapFlowWithClientId:clientId
                                             flowId:flowId
                                    configurationId:configurationId
                          encryptionConfigurationId:encryptionConfigurationId
                                           metadata:mutableMetadata];

        [MetaMapButtonResult shared].delegate = self;
        self.hasListeners = YES;
    });
}

- (NSArray

<NSString *> *)supportedEvents
{
    return @[kEventVerificationSuccess,
             kEventVerificationCanceled,
             kEventVerificationStarted];
}

#pragma mark - MetaMapButtonResultDelegate

- (void)verificationCreatedWithIdentityId:(NSString *)identityId
                           verificationID:(NSString *)verificationID {
    if (self.hasListeners) {
        [self sendEventWithName:kEventVerificationStarted
                           body:@{@"identityId": identityId ?: [NSNull null],
                                  @"verificationId": verificationID ?: [NSNull null]}];
    }
}

- (void)verificationSuccessWithIdentityId:(NSString *)identityId
                           verificationID:(NSString *)verificationID {
    if (self.hasListeners) {
        [self sendEventWithName:kEventVerificationSuccess
                           body:@{@"identityId": identityId ?: [NSNull null],
                                  @"verificationId": verificationID ?: [NSNull null]}];
    }
}

- (void)verificationCancelledWithIdentityId:(NSString *)identityId
                             verificationID:(NSString *)verificationID {
    if (self.hasListeners) {
        [self sendEventWithName:kEventVerificationCanceled
                           body:@{@"identityId": identityId ?: [NSNull null],
                                  @"verificationId": verificationID ?: [NSNull null]}];
    }
}

@end
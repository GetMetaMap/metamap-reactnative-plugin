#import "MetaMapRNSdk.h"


@implementation MetaMapRNSdk {
    bool hasListeners;
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(showFlow:(NSString * _Nonnull)clientId flowId:(NSString * _Nullable)flowId metadata:(NSDictionary<NSString *, id> * _Nullable)metadata)
{
    dispatch_async(dispatch_get_main_queue(), ^(void){
        NSMutableDictionary *mutableDictionary = [metadata mutableCopy];     //Make the dictionary mutable to change/add
        mutableDictionary[@"sdkType"] = @"react-native-ios";
        [MetaMap.shared showMetaMapFlowWithClientId: clientId flowId: flowId configurationId: nil encryptionConfigurationId: nil metadata: metadata];
        [MetaMapButtonResult shared].delegate = self;
        self->hasListeners = YES;
    });
}

-(NSArray<NSString *> *)supportedEvents { return @[@"verificationSuccess", @"verificationCanceled"]; }

- (void)verificationSuccessWithIdentityId:(NSString *)identityId verificationID:(nullable NSString *)verificationID {
    if (hasListeners) {
        [self sendEventWithName:@"verificationSuccess" body:@{@"identityId": identityId, @"verificationId": verificationID}];
    }
}

- (void)verificationCancelled {
    if (hasListeners) {
        [self sendEventWithName:@"verificationCanceled" body: nil];
    }
}

@end

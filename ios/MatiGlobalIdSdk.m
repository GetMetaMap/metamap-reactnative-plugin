#import "MatiGlobalIdSdk.h"


@implementation MatiGlobalIdSdk {
    bool hasListeners;
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(showFlow:(NSString * _Nonnull)clientId flowId:(NSString * _Nullable)flowId metadata:(NSDictionary<NSString *, id> * _Nullable)metadata)
{
    dispatch_async(dispatch_get_main_queue(), ^(void){
        [Mati.shared showMatiFlowWithClientId: clientId flowId: flowId metadata: metadata];
        [MatiButtonResult shared].delegate = self;
        self->hasListeners = YES;
    });
}

-(NSArray<NSString *> *)supportedEvents { return @[@"verificationSuccess", @"verificationCanceled"]; }

- (void)verificationSuccessWithIdentityId:(NSString *)identityId verificationID:(nullable NSString *)verificationID {
    if (hasListeners) {
        [self sendEventWithName:@"verificationSuccess" body:@{@"With Identity Id": identityId, @"With Verification Id": verificationID}];
    }
}

- (void)verificationCancelled {
    if (hasListeners) {
        [self sendEventWithName:@"verificationCanceled" body: nil];
    }
}

@end

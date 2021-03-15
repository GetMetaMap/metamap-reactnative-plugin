#import "MatiGlobalIdSdk.h"


@implementation MatiGlobalIdSdk {
    bool hasListeners;
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(setParams:(NSString * _Nonnull)clientId flowId:(NSString * _Nullable)flowId metadata:(NSDictionary<NSString *, id> * _Nullable)metadata)
{
    dispatch_async(dispatch_get_main_queue(), ^(void){
        self.matiButton = [[MatiButton alloc] init];
        [self.matiButton setParamsWithClientId: clientId flowId: flowId metadata: metadata];
        [MatiButtonResult shared].delegate = self;
        self->hasListeners = YES;
    });
}

RCT_EXPORT_METHOD(showFlow)
{
    dispatch_async(dispatch_get_main_queue(), ^(void){
        [self.matiButton sendActionsForControlEvents:UIControlEventTouchUpInside];
    });
}

-(NSArray<NSString *> *)supportedEvents { return @[@"verificationSuccess", @"verificationCanceled"]; }

- (void)verificationSuccessWithIdentityId:(NSString *)identityId {
    if (hasListeners) {
        [self sendEventWithName:@"verificationSuccess" body:@{@"With Identity Id": identityId}];
    }
}

- (void)verificationCancelled {
    if (hasListeners) {
        [self sendEventWithName:@"verificationCanceled" body: nil];
    }
}

@end

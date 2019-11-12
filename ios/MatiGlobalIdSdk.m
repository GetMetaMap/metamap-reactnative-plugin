#import "MatiGlobalIdSdk.h"


@implementation MatiGlobalIdSdk {
    RCTResponseSenderBlock onMatiCallback;    
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(init:(NSString *)clientId)
{
    [MFKYC registerWithClientId:clientId metadata:nil];
}

RCT_EXPORT_METHOD(setMatiCallback:(RCTResponseSenderBlock)callback)
{
    [MFKYC instance].delegate = self;
    onMatiCallback = callback;
}

RCT_EXPORT_METHOD(metadata:(NSDictionary *)metadata)
{
   [MFKYC instance].metadata = metadata;
}

- (void)mfKYCLoginSuccessWithIdentityId:(NSString *)identityId {
    if(onMatiCallback != nil){
        onMatiCallback(@[@YES, identityId]);
    }
}

- (void)mfKYCLoginCancelled {
    if(onMatiCallback != nil){
        onMatiCallback(@[@NO, @"Cancel"]);
    }
}

@end

#import "MatiGlobalIdSdk.h"


@implementation MatiGlobalIdSdk {

RCTResponseSenderBlock onSuccessLocal;
RCTResponseSenderBlock onCancelLocal;
RCTResponseSenderBlock onErrorLocal;
    
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(init:(NSString *)clientId)
{
    [MFKYC registerWithClientId:clientId metadata:nil];
}

RCT_EXPORT_METHOD(setMatiCallback:(RCTResponseSenderBlock)onSuccess onCancelCallback:(RCTResponseSenderBlock) onCancel  onErrorCallback: (RCTResponseSenderBlock)onError)
{
    [MFKYC instance].delegate = self;
    onSuccessLocal = onSuccess;
    onCancelLocal = onCancel;
    onErrorLocal = onError;
}

RCT_EXPORT_METHOD(metadata:(NSDictionary *)metadata)
{
   [MFKYC instance].metadata = metadata;
}

- (void)mfKYCLoginSuccessWithIdentityId:(NSString *)identityId {
    onSuccessLocal(@[identityId]);
}

- (void)mfKYCLoginCancelled {
     onSuccessLocal(@[]);
}

@end

#import "MatiLoginButton.h"
#import <MatiGlobalIDSDK/MatiGlobalIDSDK.h>

@implementation MatiLoginButton

RCT_EXPORT_MODULE(MatiLoginButton)

- (UIView *)view
{
    // TODO: Implement some actually useful functionality
    MFKYCButton* matiButton = [[MFKYCButton alloc] init];
    matiButton.frame = CGRectMake(0, 20, 320, 60);//you can change position,width an height
    matiButton.title = @"Custom Title";
    matiButton.tag = 100;
    UIView * wrapper = [[UIView alloc] init];
    [wrapper addSubview:matiButton];
    return wrapper;
}

RCT_CUSTOM_VIEW_PROPERTY(Text, NSString, UIView)
{
    MFKYCButton * matiButton  = [view viewWithTag:100];
    matiButton.title = json ? [RCTConvert NSString:json] : @"Custom Title";
  
}


@end

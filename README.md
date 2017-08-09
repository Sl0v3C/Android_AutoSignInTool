# Android_AutoSignInTool
Android sign in tool for 3rd party apps automatically or semi-automatically.

## Requirements
Need enable the accessbility in Settings --> Accessibility --> turn on 签到助手.  
JD & JD Finance need user to sign in manually for the last operation, this app will help enter into the sign in UI automatically.

## Usage
When enter into the app, it will aleart that you should enable the accessibility feature in the settings.  
Then it will show the supported 3rd party apps in the View, you can select which you want to sign in.  
When you choose one app the ```start``` button will show in the UI. You can tap it to begin.

## Notice
JD app & JD Finance need user to tap the sign in button manually since it use the tencent webkit-x5 instead of the Google original one.  
App will wait for user's action in 20 seconds. If timeout, it will keep on running next case.  
Nowadays only support 4 apps: JD, JD Finance, SMZDM, Tencent Comics.
If you need any other app, you can create issues to me.   
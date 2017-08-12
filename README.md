# Content
[Android_AutoSignInTool](#android_autosignintool)  
　1. [Requirements](#requirements)  
　2. [Usage](#usage)  
　3. [Notice](#notice)  
[中文说明(README.md Chinese version)](#中文说明readme.md-chinese-version)  
　1. [要求](#要求)  
　2. [用法](#用法)  
　3. [注意](#注意)  
  
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
App will wait for user's tap action in 20 seconds. If timeout, it will keep on running next case or exit current task.  
Nowadays only support 4 apps: ```JD```, ```JD Finance```, ```SMZDM```, ```Tencent Comics```.  
If you need any other app, you can create an issue to me.  
Fork, stars, issues & PRs are welccome!  

# 中文说明(README.md Chinese version)
AutoSignIn tool是一款能帮助用户自动或半自动进行签到的工具。

## 要求
首先进入软件，会判断是否开启了签到助手的辅助功能，会提示用户去设置-->辅助功能-->签到助手去开启。  
由于```京东```和```京东金融```的某些webview是采用腾讯的webkit，导致无法被Accessibility服务获取到node信息，所以只能进行半自动的签到服务，需要用户在某些UI界面进行签到并点击事件来触发app继续工作。  
当勾选任何一个第三方app时，开始按钮才会显示出来，点击该按钮就开始自动签到。

## 用法
进入app，会提示需要开启签到助手辅助功能；  
其次会显示出所有支持签到的第三方应用列表；  
点击开始按钮开始进行辅助签到功能。  

## 注意
```京东```和```京东金融```需要用户手动点击签到并产生点击事件来激活本应用继续执行。  
本应用在等待用户点击事件时会有20秒的超时，如果超时会继续执行或者会退出当前任务(京东金融手势密码等待超时)。
目前只支持了4款第三方应用：```京东```, ```京东金融```, ```什么值得买```, ```腾讯动漫```.  
需要其他的任何第三方app，请提issue给我。  
欢迎各种fork, 点亮星星， 提issue和PRs给我。

package com.pyy.signin;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.concurrent.TimeUnit;

import static com.pyy.signin.SignInService.autoCondition;
import static com.pyy.signin.SignInService.autoLock;
import static com.pyy.signin.Utils.delay;

/**
 * Created by pyy on 2017/8/9.
 */

public class autoSignInSMZDM {
    private boolean found = false;
    final String SMZDM = "com.smzdm.client.android";

    public void doSMZDM(AccessibilityService service) {
        autoLock.lock();
        try {
            delay(8000);
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            delay(4000);
            Utils.reLaunch(service, SMZDM);
            delay(4000);
            iteratorSMZDM(service.getRootInActiveWindow());
            autoCondition.await(10, TimeUnit.SECONDS); // 10s

            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            delay(2000);
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            delay(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        autoLock.unlock();
        MainPage.condition.signal();
    }

    private void iteratorSMZDM(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0 && info.getText() != null) {
            if (info.getText().equals("签到")) {
                AccessibilityNodeInfo parent = info.getParent();
                if ("android.widget.RelativeLayout".equals(parent.getClassName())
                        && parent.isClickable()) {
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    found = true;
                    return;
                }
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if(info.getChild(i)!=null){
                    if (found) {
                        found = false;
                        break;
                    }
                    iteratorSMZDM(info.getChild(i));
                }
            }
        }
        return ;
    }
}

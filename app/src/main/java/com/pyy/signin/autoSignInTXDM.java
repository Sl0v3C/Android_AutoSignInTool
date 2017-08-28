package com.pyy.signin;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;
import static com.pyy.signin.SignInService.autoLock;
import static com.pyy.signin.Utils.delay;

/**
 * Created by pyy on 2017/8/9.
 */

public class autoSignInTXDM {
    private boolean found = false;
    final String TXDM = "com.qq.ac.android";

    public void doTXDM(AccessibilityService service) {
        autoLock.lock();
        try {
            delay(6000);
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            delay(4000);
            Utils.reLaunch(service, TXDM);
            delay(4000);
            iteratorTXDM(service.getRootInActiveWindow(), service);
            delay(1000);
            service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            delay(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        autoLock.unlock();
        MainPage.condition.signal();
    }

    private void iterator(AccessibilityNodeInfo info, AccessibilityService service) {
        if (info.getText() != null) {
            if (info.getText().equals("签到") && info.isClickable()) {
                info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                delay(3000);
                iterator(service.getRootInActiveWindow(), service);
                found = true;
                return;
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if(info.getChild(i)!=null){
                    if (found) {
                        found = false;
                        break;
                    }
                    iterator(info.getChild(i), service);
                }
            }
        }
        return ;
    }

    private void iteratorTXDM(AccessibilityNodeInfo info, AccessibilityService service) {
        List<AccessibilityNodeInfo> node  = info.findAccessibilityNodeInfosByViewId("com.qq.ac.android:id/tab_layout_center");
        if (node != null) {
            AccessibilityNodeInfo find = node.get(0);
            if ("android.widget.RelativeLayout".equals(find.getClassName())
                    && find.isClickable()) {
                find.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                delay(3000);
                iterator(service.getRootInActiveWindow(), service);
                found = true;
                return;
            }

        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if(info.getChild(i)!=null){
                    if (found) {
                        found = false;
                        break;
                    }
                    iteratorTXDM(info.getChild(i), service);
                }
            }
        }
        return ;
    }
}

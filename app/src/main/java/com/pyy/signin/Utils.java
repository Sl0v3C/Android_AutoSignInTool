package com.pyy.signin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyy on 2017/8/5.
 */

public class Utils {
    final static String logTag = "[SignIn_Utils]";

    public static void checkSignInServiceEnabled(Context context) {
        if (Utils.isSignInServiceEnabled(context) == false) { // this stands for Context instance of this Activity.
            Utils.showAlertInfo(context);
        }
    }

    public static void showAlertInfo(final Context context) {
        new AlertDialog.Builder(context).setTitle("温馨提示")//设置对话框标题
                .setMessage("使用签到助手请开启辅助功能中的签到助手服务开关")//设置显示的内容
                .setPositiveButton("去开启",new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        context.startActivity(intent);
                    }

                }).setNegativeButton("稍后开启",new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件
                // TODO Auto-generated method stub
            }
        }).show();//在按键响应事件中显示此对话框
    }

    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void prt(String text) {
        Log.i(logTag, " " + text);
    }

    // This method used to checck the SignIn service enabled or not.
    public static boolean isSignInServiceEnabled(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            // get all the enabled service name, like:
            // com.pyy.signin/com.pyy.signin.SignInService:com.baidu.appsearch/com.baidu.appsearch.util.AppAccessibilityService
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                // Check the enabled service is the same one with this app.
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }


}

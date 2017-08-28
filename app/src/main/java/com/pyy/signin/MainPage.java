package com.pyy.signin;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pyy on 2017/8/4.
 */

public class MainPage extends Activity {
    final String logTag = "[SignIn_MainPage]";
    private ListView appListView;
    ArrayList<AppInfo> applist;
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    static boolean flag = false;
    private static final int REQUEST_PACKAGE_USAGE_STATS_PERMISSION = 1010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkPermission(REQUEST_PACKAGE_USAGE_STATS_PERMISSION);
        }
        Utils.checkSignInServiceEnabled(this);
        appListView = findViewById(R.id.appListView);
        Button start = findViewById(R.id.button);
        getAppList(start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new SignInThread().start();
            }
        });
    }

    private void checkPermission(int requestCode) {
        if (requestCode == REQUEST_PACKAGE_USAGE_STATS_PERMISSION) {
            if (!hasPermission()) {
                //如果用户没有开启"可访问使用记录"，则跳转到该设置项并提醒用户打开
                Utils.showToast("\"签到助手\"提示：\n请开启\"可访问使用记录\"权限", this);
                startActivityForResult(
                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        REQUEST_PACKAGE_USAGE_STATS_PERMISSION);
            }
        }
    }

    //检测用户是否对本app开启了“Apps with usage access”权限
    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager)
                getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    class SignInThread extends Thread {
        @Override
        public void run() {
            super.run();
            processSignIn();
        }
    }

    private void processSignIn() {
        for (int i = 0; i < applist.size(); i++) {
            AppInfo tempInfo = applist.get(i);
            if (tempInfo.chosen) {
                autoSignIn(tempInfo);
            }
        }
    }

    private void autoLaunch(String name) {
        PackageManager packageManager = this.getPackageManager();
        Intent mBootUpIntent = packageManager.getLaunchIntentForPackage(name);
        //mBootUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(mBootUpIntent);
    }

    private void autoSignIn(AppInfo info) {
        lock.lock();
        flag = true;
        switch (info.appName) {
            case "京东":
                try {
                    autoLaunch(info.packageName);
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    lock.unlock();
                }
                break;
            case "京东金融":
                try {
                    autoLaunch(info.packageName);
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    lock.unlock();
                }
                break;
            case "什么值得买":
                try {
                    autoLaunch(info.packageName);
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    lock.unlock();
                }
                break;
            case "腾讯动漫":
                try {
                    autoLaunch(info.packageName);
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    lock.unlock();
                }
                break;
            default:
                break;

        }

    }

    private void getAppList(Button btn) {
        applist = AppInfo.showSupportApps(this);
        AppAdapter adapter = new AppAdapter(this, R.layout.app_list, applist, btn);
        appListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}

package com.pyy.signin;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pyy on 2017/8/6.
 */

class AppAdapter extends ArrayAdapter<AppInfo> {
    private ArrayList<AppInfo> appList;
    private LayoutInflater inflater;
    private int showCnt;
    private Button start;

    AppAdapter(Context context, int resource, ArrayList<AppInfo> objects, Button btn) {
        super(context, resource, objects);
        start = btn;
        this.appList = objects;
        inflater = LayoutInflater.from(context);
        showCnt = 0;
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(getContext(),
                text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public AppInfo getItem(int i) {
        return appList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (view == null)
            vi = inflater.inflate(R.layout.app_list, null);
        final AppInfo appInfo = appList.get(i);
        TextView appName = vi.findViewById(R.id.app_name);
        final CheckBox chosen = vi.findViewById(R.id.app_chosen);
        chosen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(chosen.isChecked()){
                    showCnt++;
                    start.setVisibility(View.VISIBLE);
                    appInfo.chosen = true;
                    switch (appInfo.appName) {
                        case "京东":
                            showToast("京东:当签到助手进入到签到界面后，用户手动点击签到。" +
                                    "然后双击右上角分享按钮，签到助手才会继续。\n" +
                                    "所有等待用户点击都会有20秒超时。");
                            break;
                        case "京东金融":
                            showToast("京东金融:设置手势密码的，需要用户手动解锁，助手会30秒超时。\n" +
                                    "签到领京豆后点击\"京豆明细\"触发助手继续自动化;" +
                                    "钢蹦需要用户手动领取，领取之后点击左上角X按钮触发助手继续。\n" +
                                    "所有等待用户点击都会有20秒超时。");
                            break;
                        default:
                            break;
                    }
                }else{
                    showCnt--;
                    if (showCnt == 0) {
                        start.setVisibility(View.INVISIBLE);
                    }
                    appInfo.chosen = false;
                }
            }
        });
        ImageView appIcon = vi.findViewById(R.id.app_icon);
        appIcon.setImageDrawable(appInfo.appIcon);
        appName.setText(appInfo.appName);
        return vi;
    }
}

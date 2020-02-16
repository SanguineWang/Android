package com.example.helloworld.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.helloworld.R;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 *  Created by wangziyang .
 *
 *   @desc :用户信息页，用于添加好友展示，查询用户信息
 */
public class UserInfoActivity extends Activity {
    private final static String TAG = "UserInfoActivity";
    private ImageView iv_avatar;
    private TextView tv_nickname;
    private TextView tv_username;
    private TextView tv_region;
    private TextView tv_signatrue;
    private Button bt_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String appkey = null;
        String reason = "关联请求";
        Log.i(TAG, "initData: intent:" + username);
        bt_add.setOnClickListener(v -> {
            ContactManager.sendInvitationRequest(username, appkey, reason, new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        Toast.makeText(getApplicationContext(), "申请成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i(TAG, "ContactManager.sendInvitationRequest" + ", responseCode = " + i + " ; Desc = " + s);
                        Toast.makeText(getApplicationContext(), "申请失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        JMessageClient.getUserInfo(username, new GetUserInfoCallback() {
            @Override
            public void gotResult(int status, String desc, UserInfo userInfo) {
                if (status == 0) {
                    String username = "账号 " + userInfo.getUserName();
                    String region = "地区 " + userInfo.getRegion();
                    String signatrue = "签名 " + userInfo.getSignature();

                    tv_nickname.setText(userInfo.getNickname());
                    tv_username.setText(username);
                    tv_region.setText(region);
                    tv_signatrue.setText(signatrue);

                    if (userInfo.getAvatarFile() != null) {
                        iv_avatar.setImageBitmap(BitmapFactory.decodeFile(userInfo.getAvatarFile().toString()));
                    } else {
                        Log.e(TAG, "本地没有 " + userInfo.getUserName() + "的头像");
                        iv_avatar.setImageResource(R.drawable.ic_launcher_background);
                    }

                } else {
                    Log.e(TAG, "gotResult: 没获取到好友： " + username + " 的信息");
                }
            }
        });


    }

    private void initView() {
        setContentView(R.layout.activity_user_info);
        iv_avatar = findViewById(R.id.iv_user_avatar);
        tv_nickname = findViewById(R.id.tv_user_name);
        tv_username = findViewById(R.id.tv_user_account_number);
        tv_region = findViewById(R.id.tv_user_region);
        tv_signatrue = findViewById(R.id.tv_user_signature);
        bt_add = findViewById(R.id.bt_user_add_friend);
    }
}

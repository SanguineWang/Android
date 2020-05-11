package com.example.helloworld.setrelative;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.helloworld.user.UserInfoActivity;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * update by wang on 2020/2/10.
 *
 * @desc :
 */
public class AddFriendActivity extends Activity {
    private static final String TAG = "AddFriendActivity";
    private EditText mEt_userName;
    private EditText mEt_reason;
    private Button mButton;
    private Button mbt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }


    private void initData() {
//        //发送添加好友请求
//        mButton.setOnClickListener(v -> {
//            String name = mEt_userName.getText().toString();
//            String appkey = null;
//            String reason = mEt_reason.getText().toString();
//            ContactManager.sendInvitationRequest(name, appkey, reason, new BasicCallback() {
//                @Override
//                public void gotResult(int i, String s) {
//                    if (i == 0) {
//                        Toast.makeText(getApplicationContext(), "申请成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Log.i("AddFriendActivity", "ContactManager.sendInvitationRequest" + ", responseCode = " + i + " ; Desc = " + s);
//                        Toast.makeText(getApplicationContext(), "申请失败", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        });
        //查找用户信息
        mbt_search.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(AddFriendActivity.this, UserInfoActivity.class);
            String name = mEt_userName.getText().toString();
            intent.putExtra("username", name);
            JMessageClient.getUserInfo(name, new GetUserInfoCallback() {
                @Override
                public void gotResult(int status, String desc, UserInfo userInfo) {
                    if (status == 0) {
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "没有该用户", Toast.LENGTH_SHORT).show();

                        Log.e(TAG, "gotResult: 没获取到：" + name + " 的信息");

                    }
                }
            });


        });
    }


    private void initView() {
        setContentView(R.layout.activity_add_friend);
        mEt_userName = (EditText) findViewById(R.id.et_user_name);
        mbt_search = findViewById(R.id.bt_search);
    }
}

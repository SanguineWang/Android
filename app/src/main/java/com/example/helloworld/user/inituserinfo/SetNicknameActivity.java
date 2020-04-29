package com.example.helloworld.user.inituserinfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.helloworld.R;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * @ClassName SetNicknameActivity
 * @Description :设置昵称
 * @Author Mr.wang
 * @Date 2020/2/16 22:15
 * @Version 1.0
 */
public class SetNicknameActivity extends Activity {
    private static final String TAG = "SetNicknameActivity";
    private EditText et_new_nickname;
    private Button bt_next;
    private UserInfo mMyInfo;
    private String nickname;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {


        bt_next.setOnClickListener(v -> {
            if (et_new_nickname.getText() != null) {
                nickname = et_new_nickname.getText().toString();
                mMyInfo.setNickname(nickname);
                JMessageClient.updateMyInfo(UserInfo.Field.nickname, mMyInfo, new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        if (responseCode == 0) {
                            Toast.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "昵称设置成功");
                        } else {
                            Toast.makeText(getApplicationContext(), "设置失败", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "昵称设置失败");

                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "请输入您的昵称", Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void initView() {
        setContentView(R.layout.activity_set_nickname);
        et_new_nickname=findViewById(R.id.ed_init_nickname);
        bt_next=findViewById(R.id.bt_nickname_next);
    }
}

package com.example.helloworld.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.helloworld.user.inituserinfo.SetAvatarActivity;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * update by wang on 2020/2/10.
 *  @desc:   我的主页
 */


public class MineActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "MineActivity";
    private TextView tv_username;
    private TextView tv_user_id;
    private Button bt_logout;
    private String img;
    private ImageView iv_Avatar;
    private String nickname = "暂未设置昵称";
    private String username ;
    private String Avatar ="null";
    private String Avatarfile= "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
        initData();
    }

    private void initView() {

        setContentView(R.layout.activity_about_mine);
        tv_username = findViewById(R.id.tv_username);
        tv_user_id = findViewById(R.id.tv_user_id);
        iv_Avatar = findViewById(R.id.iv_avatar);

        findViewById(R.id.bt_logout).setOnClickListener(this);
        findViewById(R.id.bt_update_my_info).setOnClickListener(this);
        findViewById(R.id.bt_update_user_avatar).setOnClickListener(this);
        findViewById(R.id.bt_update_user_password).setOnClickListener(this);
    }

    private void initData() {
        UserInfo userInfo = JMessageClient.getMyInfo();

        if (userInfo != null) {
            Log.e(TAG, "initData: userinfo" +userInfo.toString());
            if (!userInfo.getNickname().equals("")){
                tv_username.setText(userInfo.getNickname()); //昵称
            }else {
                tv_username.setText(nickname);
            }

            tv_user_id.setText("账号"+userInfo.getUserName());  //账号

            if (userInfo.getAvatarFile() != null) {
                iv_Avatar.setImageBitmap(BitmapFactory.decodeFile(userInfo.getAvatarFile().toString()));//头像
            }else{
                iv_Avatar.setImageResource(R.drawable.init_avatar);
            }

            //log调试打印
           if (userInfo.getAvatar()!=null){
               Avatar = userInfo.getAvatar();
           }

            if (userInfo.getAvatarFile() != null) {
                Avatarfile = userInfo.getAvatarFile().toString();
            }
            if (userInfo.getNickname() != null) {
                nickname = userInfo.getNickname();
            }
            username = userInfo.getUserName();
            Log.e(TAG,"userInfo nickname"+nickname);
            Log.e(TAG,"userInfo username"+username);
            Log.e(TAG,"userInfo  Avatar"+Avatar);
            Log.e(TAG,"userInfo Avatarfile"+ Avatarfile);
            //
        } else {
            Toast.makeText(MineActivity.this, "获取userInfo失败", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "获取userInfo失败");
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        ProgressDialog mProgressDialog;
        switch (v.getId()) {
            case R.id.bt_logout://下线
                mProgressDialog = ProgressDialog.show(MineActivity.this, "提示：", "正在加载中。。。");
                UserInfo myInfo = JMessageClient.getMyInfo();
                if (myInfo != null) {
                    JMessageClient.logout();
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "注销成功", Toast.LENGTH_SHORT).show();
                    intent.setClass(MineActivity.this, LoginActivity.class);
                    setResult(8);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MineActivity.this, "注销失败", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
                break;
            case R.id.bt_update_my_info://更新个人信息
                intent.setClass(MineActivity.this, UpdateUserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_update_user_avatar://更新头像
                intent.setClass(MineActivity.this, SetAvatarActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_update_user_password://更新密码
                intent.setClass(MineActivity.this, UpdatePassword.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

package com.example.helloworld.user;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.helloworld.R;
/**
 *  Created by wangziyang .
 *
 *   @desc :用户手册，用于展示用户须知，服务条例
 */
public class UserNoticeActivity extends Activity {
    private TextView tv_user_notice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        setContentView(R.layout.activity_user_notice);
        tv_user_notice =findViewById(R.id.tv_see_notice);
    }
}

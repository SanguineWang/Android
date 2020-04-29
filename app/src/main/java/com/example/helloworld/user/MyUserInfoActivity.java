package com.example.helloworld.user;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.helloworld.R;

/**
 *  Created by wangziyang .
 * @desc :个人信息
 */
public class MyUserInfoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        setContentView(R.layout.activity_my_info);
    }
}

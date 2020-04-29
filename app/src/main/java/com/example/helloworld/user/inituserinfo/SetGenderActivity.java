package com.example.helloworld.user.inituserinfo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.helloworld.R;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * @ClassName SetGenderActivity
 * @Description 设置性别
 * @Author Mr.wang
 * @Date 2020/2/16 22:38
 * @Version 1.0
 */
public class SetGenderActivity extends Activity {
    private static final String TAG = "SetGenderActivity";
    private UserInfo mMyInfo;
    private RadioButton mRb_male;
    private RadioButton mRb_female;
    private RadioGroup mRg_gender;
    private Button Bt_next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mRg_gender.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == mRb_female.getId()) {
                mMyInfo.setGender(UserInfo.Gender.female);
            } else if (checkedId == mRb_male.getId()) {
                mMyInfo.setGender(UserInfo.Gender.male);
            }
        });
        Bt_next.setOnClickListener(v -> {
            JMessageClient.updateMyInfo(UserInfo.Field.gender, mMyInfo, new BasicCallback() {
                @Override
                public void gotResult(int responseCode, String responseMessage) {
                    if (responseCode == 0) {
                        Toast.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "性别设置成功");
                    } else {
                        Toast.makeText(getApplicationContext(), "设置失败", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "性别设置失败");

                    }
                }
            });
        });
    }

    private void initView() {
        setContentView(R.layout.activity_set_gender);
        mRg_gender = findViewById(R.id.rg_gender);
        mRb_male = (RadioButton) findViewById(R.id.rb_male);
        mRb_female = (RadioButton) findViewById(R.id.rb_female);
        Bt_next =findViewById(R.id.bt_gender_next);


    }
}

package com.example.helloworld.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.helloworld.user.inituserinfo.SetBirthdayActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by wangziyang .
 *
 * @desc :更新用户信息
 */
public class UpdateUserInfoActivity extends Activity {
    private   static final  String TAG= "UpdateUserInfoActivity";
    private EditText mEt_nickname;
    private EditText mEt_birthday;
    private EditText mEt_region;
    private EditText mEt_signature;
    private Button mBt_updateUserInfo;
    private InputMethodManager mImm;
    private RadioButton mRb_male;
    private RadioButton mRb_female;
    private RadioButton mRb_unknown;
    private RadioGroup mRg_gender;
    private UserInfo mMyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyInfo = JMessageClient.getMyInfo();
        Log.d(TAG,mMyInfo.toString());
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }
    public static String longToDate(long lo){
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        return sd.format(date);
    }
    public static void setEditTextReadOnly(TextView view){
        if (view instanceof android.widget.EditText){
            view.setCursorVisible(false);             //设置输入框中的光标不可见
            view.setFocusable(false);                 //无焦点
            view.setFocusableInTouchMode(false);      //触摸时也得不到焦点
        }
    }
    private void initData() {
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setEditTextReadOnly(mEt_birthday);
        mEt_birthday.setText(longToDate(mMyInfo.getBirthday()));
        mEt_nickname.setText(mMyInfo.getNickname());
        mEt_signature.setText(mMyInfo.getSignature());
        mEt_region.setText(mMyInfo.getRegion());
        if (mMyInfo.getGender()== UserInfo.Gender.male){
            mRb_male.setSelected(true);
        }else {
            if (mMyInfo.getGender()== UserInfo.Gender.female){
                mRb_female.setSelected(true);
            }else {
                mRb_unknown.setSelected(true);
            }
        }
        Log.d(TAG,"birthday"+ longToDate(mMyInfo.getBirthday()));
        Log.d(TAG,"getGender"+ mMyInfo.getGender());

        mBt_updateUserInfo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mImm.isActive()) {
                            mImm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                        if (mMyInfo != null) {
                            //设置nickname
                            if (!mMyInfo.setNickname(mEt_nickname.getText().toString())) {
                               Log.d(TAG,"设置nickname失败");
                            }
                            //设置region
                            if (!mMyInfo.setRegion(mEt_region.getText().toString())) {
                                Log.d(TAG,"设置region失败");
                            }

                            //设置signature
                            if (!mMyInfo.setSignature(mEt_signature.getText().toString())) {
                                Log.d(TAG,"设置signature失败");
                            }
                            //设置birthday
                            String data = mEt_birthday.getText().toString();
                            if (!TextUtils.isEmpty(data)) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                                try {
                                    long time = sdf.parse(data).getTime();
                                    mMyInfo.setBirthday(time);
                                } catch (Exception e) {
                                    Log.d(TAG,"设置birthday失败");
                                    e.printStackTrace();
                                }
                            }

                            JMessageClient.updateMyInfo(UserInfo.Field.all, mMyInfo, new BasicCallback() {
                                @Override
                                public void gotResult(int responseCode, String responseMessage) {
                                    if (responseCode == 0) {
                                        Toast.makeText(UpdateUserInfoActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                        Log.i("UpdateUserInfoActivity", "更新信息成功updateAllUserinfo," + " responseCode = " + responseCode + "; desc = " + responseMessage);
                                    } else {
                                        Toast.makeText(UpdateUserInfoActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                                        Log.i("UpdateUserInfoActivity", "更新信息失败updateAllUserinfoupdateAllUserinfo," + " responseCode = " + responseCode + "; desc = " + responseMessage);

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(UpdateUserInfoActivity.this, "更新失败info == null", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        );
        mEt_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(UpdateUserInfoActivity.this, SetBirthdayActivity.class);
                startActivity(intent);
            }
        });
        mRg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mRb_female.getId()) {
                    mMyInfo.setGender(UserInfo.Gender.female);
                } else if (checkedId == mRb_male.getId()) {
                    mMyInfo.setGender(UserInfo.Gender.male);
                } else if (checkedId == mRb_unknown.getId()) {
                    mMyInfo.setGender(UserInfo.Gender.unknown);
                }
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_update_userinfo);

        mEt_nickname = (EditText) findViewById(R.id.et_nickname);
        mEt_birthday = (EditText) findViewById(R.id.et_birthday);
        mEt_region = (EditText) findViewById(R.id.et_region);
        mEt_signature = (EditText) findViewById(R.id.et_signature);

        mRb_male = (RadioButton) findViewById(R.id.rb_male);
        mRb_female = (RadioButton) findViewById(R.id.rb_female);
        mRb_unknown = (RadioButton) findViewById(R.id.rb_unknown);

        mRg_gender = (RadioGroup) findViewById(R.id.rg_gender);

        mBt_updateUserInfo = (Button) findViewById(R.id.bt_update_user_info);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                mImm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

}

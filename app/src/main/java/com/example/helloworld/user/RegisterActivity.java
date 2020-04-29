package com.example.helloworld.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by wangziyang .
 *
 * @desc :注册界面
 */
public class RegisterActivity extends Activity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static final String TAG = "RegisterActivity";
    private EditText mEt_userName;
    private EditText mEt_password;
    private CheckBox mCb_if_agree_notice;
    private TextView mTv_see_notice;
    private Button mBt_register;
    private TextView mBt_back;
    private ProgressDialog mProgressDialog = null;
    private String avatarPath;
    private int exist = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    //注册功能实现
    private void initData() {
        mBt_back.setOnClickListener(v -> {
            finish();
        });
        mCb_if_agree_notice.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mBt_register.setEnabled(true);
            } else {
                mBt_register.setEnabled(false);
            }
        });
        mBt_register.setOnClickListener(v -> {
            mProgressDialog = ProgressDialog.show(RegisterActivity.this, "提示：", "正在加载中。。。");

            final String userName = mEt_userName.getText().toString();
            final String password = mEt_password.getText().toString();

/**
 *判断用户名是否存在
 *
 */
            JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
                @Override
                public void gotResult(int status, String desc, UserInfo userInfo) {
                    if (status == 0) {
                        exist = 1;
                        Toast.makeText(getApplicationContext(), "账号已存在", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, " 用户名已存在");
                    } else {
                        exist = 0;
                        Log.i(TAG, "用户名可以使用");
                    }
                }
            });

/**=================     调用SDK注册接口    =================*/
           if (exist==0){
               JMessageClient.register(userName, password, new BasicCallback() {
                   @Override
                   public void gotResult(int responseCode, String registerDesc) {
                       if (responseCode == 0) {
                           mProgressDialog.dismiss();
                           Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                           Log.i(TAG, "JMessageClient.register " + ", responseCode = " + responseCode + " ; registerDesc = " + registerDesc);
                           Intent result = new Intent();
                           result.putExtra(LoginActivity.KEY_USERNAME, userName);
                           result.putExtra(LoginActivity.KEY_PWD, password);
                           setResult(RESULT_OK, result);
                           finish();
                       } else {
                           mProgressDialog.dismiss();
                           Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                           Log.i(TAG, "JMessageClient.register " + ", responseCode = " + responseCode + " ; registerDesc = " + registerDesc);
                       }
                   }
               });
           }

        });

//        mBt_send_checking_code.setOnClickListener(v -> {
//           //发短信
//        });
        mTv_see_notice.setOnClickListener(v -> {
            Intent notice = new Intent();
            notice.setClass(RegisterActivity.this, UserNoticeActivity.class);
            startActivity(notice);
        });

    }

    private void initView() {
        setContentView(R.layout.activity_register_constraintlayout);
        mEt_userName = (EditText) findViewById(R.id.et_register_username);
        mEt_password = (EditText) findViewById(R.id.et_register_password);
//        mEt_checking_code = (EditText) findViewById(R.id.et_checking_code);
        mCb_if_agree_notice = findViewById(R.id.cb_if_agree_notice);
        mTv_see_notice = findViewById(R.id.tv_see_notice);
//        mBt_send_checking_code = (Button) findViewById(R.id.bt_send_checking_code);
        mBt_register = (Button) findViewById(R.id.bt_register);
        mBt_back = findViewById(R.id.bt_register_cancel);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            if (null != cursor) {
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                avatarPath = cursor.getString(columnIndex);
//                cursor.close();
//                mEt_avatar.setText(avatarPath);
//            }
//        }
//    }
}

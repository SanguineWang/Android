package com.example.helloworld.user;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.helloworld.R;
import com.example.helloworld.Utils.PermissionsUtils;
import com.example.helloworld.test.MainActivity_first;
import com.example.helloworld.user.inituserinfo.SetBirthdayActivity;
import com.example.helloworld.user.sms.MainSmsActivity;
import com.google.gson.Gson;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.model.DeviceInfo;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.jpush.sms.SMSSDK;

/**
 * update by wang on 2020/2/10.
 * @desc: 登录
 */
public class LoginActivity extends Activity {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PWD = "pwd";
    public static final int REQ_CODE_FOR_REGISTER = 1;
    public EditText mEd_userName;
    public EditText mEd_password;
    private Button mBt_login;
//    private Button mBt_login_with_infos;
    private Button mBt_gotoRegister;
    private ProgressDialog mProgressDialog = null;
//    private RadioGroup mRgType;
//    private boolean isTestVisibility = false;
    private  static  final    String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);

      //初始化极光im
        JMessageClient.setDebugMode(true);
        if (JMessageClient.init(getApplicationContext(), true)){
            Log.e("init", "onCreate: 初始化成功" );
        }
        else
            Log.e("init", "onCreate: 初始化失败" );
//        //初始化极光sms
//        SMSSDK.getInstance().initSdk(this);
//        SMSSDK.getInstance().setDebugMode(true);
//        SMSSDK.getInstance().setIntervalTime(30*1000);
        initView();
        initData();
    }
    //---------------------------------------------------------
//    权限申请接口回调
    //创建监听权限的接口对象
    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
//            Toast.makeText(LoginActivity.this, "权限通过", Toast.LENGTH_SHORT).show();
            Log.e("init", "onCreate: 权限通过" );
        }

        @Override
        public void forbitPermissons() {
//            finish();
            Toast.makeText(LoginActivity.this, "缺少必要权限,请到设置里打开", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_FOR_REGISTER && RESULT_OK == resultCode && data != null) {
            mEd_userName.setText(data.getStringExtra(KEY_USERNAME));
            mEd_password.setText(data.getStringExtra(KEY_PWD));
        }
    }

    /**
     * #################    应用入口,登陆或者是注册    #################
     */
    private void initData() {
        /**=================     获取个人信息不是null，说明已经登陆，无需再次登陆，则直接进入type界面    =================*/
        UserInfo myInfo = JMessageClient.getMyInfo();
        if (myInfo != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity_first.class);
            startActivity(intent);
            finish();
        }
        /**=================     调用注册接口    =================*/
        mBt_gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                intent.setClass(getApplicationContext(), MainSmsActivity.class);

                intent.setClass(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQ_CODE_FOR_REGISTER);
            }
        });
        mBt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = ProgressDialog.show(LoginActivity.this, "提示：", "正在加载中。。。");
                mProgressDialog.setCanceledOnTouchOutside(true);
                String userName = mEd_userName.getText().toString();
                String password = mEd_password.getText().toString();
                /**=================     调用SDk登陆接口    =================*/
                JMessageClient.login(userName, password, new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String LoginDesc) {
                        if (responseCode == 0) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                            Log.i("CourseListActivity", "JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + LoginDesc);
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), MainActivity_first.class);




                            startActivity(intent);
                            finish();
                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                            Log.i("CourseListActivity", "JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + LoginDesc);
                        }
                    }
                });
            }
        });

//        mBt_login_with_infos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mProgressDialog = ProgressDialog.show(LoginActivity.this, "提示：", "正在加载中。。。");
//                mProgressDialog.setCanceledOnTouchOutside(true);
//                String userName = mEd_userName.getText().toString();
//                String password = mEd_password.getText().toString();
//                /**=================     调用SDk登陆接口    =================*/
//                JMessageClient.login(userName, password, new RequestCallback<List<DeviceInfo>>() {
//                    @Override
//                    public void gotResult(int responseCode, String responseMessage, List<DeviceInfo> result) {
//                        if (responseCode == 0) {
//                            mProgressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
//                            Log.i("CourseListActivity", "JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + responseMessage);
//                            Intent intent = new Intent(getApplicationContext(), MainActivity_first.class);
//                            Gson gson = new Gson();
//                            intent.putExtra("deviceInfos", gson.toJson(result));
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            mProgressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "登陆失败", Toast.LENGTH_SHORT).show();
//                            Log.i("CourseListActivity", "JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + responseMessage);
//                        }
//                    }
//                });
//            }
//        });


    }

    private void initView() {
        setContentView(R.layout.activity_login_constraintlayout);
        mEd_userName = (EditText) findViewById(R.id.ed_login_username);
        mEd_password = (EditText) findViewById(R.id.ed_login_password);
        mBt_login = (Button) findViewById(R.id.bt_login);
//        mBt_login_with_infos = (Button) findViewById(R.id.bt_login_with_infos);
        mBt_gotoRegister = (Button) findViewById(R.id.bt_goto_regester);
//        mRgType = (RadioGroup) findViewById(R.id.rg_environment);;
//        if (!isTestVisibility) {
//            mRgType.setVisibility(View.GONE);
//        } else {
//            //供jmessage sdk测试使用，开发者无需关心。
////            Boolean isTestEvn = invokeIsTestEvn();
////            Boolean isQAEvn = invokeIsQAEvn();
//            mRgType.check(R.id.rb_public);
//            if (isTestEvn) {
//                mRgType.check(R.id.rb_test);
//            } else if (isQAEvn) {
//                mRgType.check(R.id.rb_qa);
//            }
//        }
    }
}

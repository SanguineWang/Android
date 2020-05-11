package com.example.helloworld.test;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.helloworld.R;
import com.example.helloworld.Utils.ApiFunctionUtils;
import com.example.helloworld.Utils.WriteTxtUtil;
import com.example.helloworld.setrelative.Adapter.FriendListAdapter;
import com.example.helloworld.setrelative.FriendContactManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * @ClassName SmsActivity
 * @Description TODO
 * @Author Mr.wang
 * @Date 2020-05-10 19:28
 * @Version 1.0
 */
public class SmsActivity extends Activity implements SensorEventListener {
    private final  String TAG="SmsActivity";
    private EditText ed_phone;
//    private Button bt_send;
//    private TextView tv_deviceinfo;
    SensorManager sensormanager;//传感器
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String address;
    private String number="0";
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initLocation();
//        sensormanager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
    }
    private void SOSCall(){
        if (number.length()==11){
            ApiFunctionUtils.getInstance().call(SmsActivity.this,number);
        }else
            Toast.makeText(getApplicationContext(), "没有亲属号码,拨打电话失败", Toast.LENGTH_SHORT).show();
    }
    private void SoSSMS(){
        if (number.length()==11){
            ApiFunctionUtils.getInstance().sendMsg(SmsActivity.this,address,number);
        }else
            Toast.makeText(getApplicationContext(), "没有亲属号码，发送短信失败", Toast.LENGTH_SHORT).show();

    }
    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //传感器
    @Override
    protected void onResume() {
        super.onResume();

//        sensormanager.registerListener(this, sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
//        WriteTxtUtil.getInstance().saveAction("T,X,Y,Z,E");//csv格式
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
//        sensormanager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        float[] values=event.values;
        StringBuilder x=new StringBuilder();
        StringBuilder y=new StringBuilder();
        StringBuilder z=new StringBuilder();

        StringBuilder csv=new StringBuilder();
        x.append("X:").append(values[0]).append("\n");
        y.append("Y:").append(values[1]).append("\n");
        z.append("Z:").append(values[2]).append("\n");
        String data = x.toString() + y.toString() + z.toString();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time = simpleDateFormat.format(date);

        csv.append(time).append(',')
                .append(values[0]).append(',')
                .append(values[1]).append(',')
                .append(values[2]);
        Log.d("SmsActivity", csv.toString());
//        tv_deviceinfo.setText(data);
//        WriteTxtUtil.getInstance().saveAction(csv.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //回调定位监听
    private class MyAMapLocationListener implements AMapLocationListener {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    address=aMapLocation.getAddress();
                    SoSSMS();
                    SOSCall();
                    Log.e("SmsActivity","位置："+ aMapLocation.getAddress());
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("SmsActivity", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    }
    private void initData() {
        getFirstRelativeNumber();
//        String phone=ed_phone.getText().toString();
//        String message="test";
//        bt_send.setOnClickListener(v -> {
//            sendSMS();
//        });

    }
    private void getFirstRelativeNumber(){

        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {
                if (i == 0) {
                    if (list.size() == 0) {
                        Toast.makeText(getApplicationContext(), "没有亲属", Toast.LENGTH_SHORT).show();
                    }else {
                        number=list.get(0).getUserName();
                        ed_phone.setText(number);
                        Log.d(TAG,"number"+number);
                    }
                    Log.i(TAG, "获取成功ContactManager.getFriendList" + ", responseCode = " + i + " ; LoginDesc = " + s);
                } else {
                    Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "获取失败ContactManager.getFriendList" + ", responseCode = " + i + " ; LoginDesc = " + s);
                }
            }
        });
    }
    /**
     * 发短信 
     */
//    private void sendSMS() {
//        String content = "【紧急求助】 位置："+address;
//        String phone = ed_phone.getText().toString().trim();
//        if (!phone.isEmpty()) {
//            SmsManager manager = SmsManager.getDefault();
//            ArrayList<String> strings = manager.divideMessage(content);
//            for (int i = 0; i < strings.size(); i++) {
//                manager.sendTextMessage(phone, null, content, null, null);
//            }
//            Toast.makeText(SmsActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
//        }
//    }


    private void initView() {
        setContentView(R.layout.activity_sms);
        ed_phone=findViewById(R.id.eT_phoneNumber);
//        bt_send=findViewById(R.id.bt_send_sms);
//        tv_deviceinfo=findViewById(R.id.tv_device);

    }
}

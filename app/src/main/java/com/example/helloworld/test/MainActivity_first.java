package com.example.helloworld.test;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.R;
import com.example.helloworld.circle.CircleActivity;
import com.example.helloworld.friendCircle.FriendCircleActivity;
import com.example.helloworld.learn.CourseListActivity;
import com.example.helloworld.location.MapActivity;
import com.example.helloworld.setrelative.FriendContactManager;
import com.example.helloworld.setrelative.ShowFriendReasonActivity;
import com.example.helloworld.user.MineActivity;
import com.example.helloworld.user.inituserinfo.CutAvatarActivity;
import com.example.helloworld.user.inituserinfo.SetAvatarActivity;
import com.example.helloworld.user.inituserinfo.SetBirthdayActivity;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;

public class MainActivity_first extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_course_list);
//          RequestCourseUtils.getInstance().gethttpresult("http://114.115.169.146:8080/queryCourseList");
//        System.out.println("gethttpresult的结果"+RequestCourseUtils.getInstance().gethttpresult("http://114.115.169.146:8080/queryCourseList"));

//        RequestCourseUtils.getInstance().okhttpDate(getString(R.string.CourseUrl));//测试成功
        Button button2 = (Button) findViewById(R.id.bt_1);
        Button button1 = (Button) findViewById(R.id.bt_4);
        Button button3 = (Button) findViewById(R.id.bt_3);
        Button button5 = (Button) findViewById(R.id.bt_5);
        Button button6 = (Button) findViewById(R.id.bt_6);

        Button bt_sms = (Button) findViewById(R.id.bt_sms);

        bt_sms.setOnClickListener(this);
        button2.setOnClickListener(this);
        button1.setOnClickListener(this);
        button3.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_sms:
                intent.setClass(MainActivity_first.this, SmsActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_1:
                intent.setClass(MainActivity_first.this, SetAvatarActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_4:
                intent.setClass(MainActivity_first.this, FriendCircleActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_3:
                intent.setClass(MainActivity_first.this, MapActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_5:
                intent.setClass(MainActivity_first.this, FriendContactManager.class);
                startActivity(intent);
                break;
            case R.id.bt_6:
//                intent.setClass(MainActivity_first.this, MineActivity.class);
                intent.setClass(MainActivity_first.this, MineActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 好友相关事件监听
     *
     * @param event
     */
    public void onEvent(ContactNotifyEvent event) {
        String reason = event.getReason();
        String fromUsername = event.getFromUsername();
        String appkey = event.getfromUserAppKey();

        Intent intent = new Intent(getApplicationContext(), ShowFriendReasonActivity.class);
        intent.putExtra(ShowFriendReasonActivity.EXTRA_TYPE, event.getType().toString());
        switch (event.getType()) {
            case invite_received://收到好友邀请
                intent.putExtra("invite_received", "fromUsername = " + fromUsername + "\nfromUserAppKey" + appkey + "\nreason = " + reason);
                intent.putExtra("username", fromUsername);
                intent.putExtra("appkey", appkey);
                startActivity(intent);
                break;
            case invite_accepted://对方接收了你的好友邀请
                intent.putExtra("invite_accepted", "对方接受了你的好友邀请");
                startActivity(intent);
                break;
            case invite_declined://对方拒绝了你的好友邀请
                intent.putExtra("invite_declined", "对方拒绝了你的好友邀请\n拒绝原因:" + event.getReason());
                startActivity(intent);
                break;
            case contact_deleted://对方将你从好友中删除
                intent.putExtra("contact_deleted", "对方将你从好友中删除");
                startActivity(intent);
                break;
            case contact_updated_by_dev_api://好友关系更新，由api管理员操作引起
                intent.putExtra("contact_updated_by_dev_api", "好友关系被管理员更新");
                startActivity(intent);
                break;
            default:
                break;
        }

    }



}

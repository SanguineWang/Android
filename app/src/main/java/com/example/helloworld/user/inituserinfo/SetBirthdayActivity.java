package com.example.helloworld.user.inituserinfo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.helloworld.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * @ClassName SetBirthdayActivity
 * @Description 设置出生日期
 * @Author Mr.wang
 * @Date 2020/2/16 23:41
 * @Version 1.0
 */
public class SetBirthdayActivity extends AppCompatActivity {
    private final String TAG = "SetBirthdayActivity";
    private TextView tvTime;
    private TimePickerView pvTime;
    private Button bt_next;
    private UserInfo mMyInfo;
    private Date datetime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyInfo = JMessageClient.getMyInfo();
        initView();
        initData();
    }

    private void initData() {
        //时间选择器
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020, 2, 18);
        pvTime = new TimePickerBuilder(SetBirthdayActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvTime.setText(getTime(date));
                datetime = date;

            }

        })
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确认")//确认按钮文字
                .isCyclic(true)//是否循环滚动
                .setContentTextSize(24)
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .build();
        tvTime.setOnClickListener(v -> {
            pvTime.show();
        });
        bt_next.setOnClickListener(v -> {
            Log.i(TAG, "initData: getDateTime(datetime) :" + datetime);

            //设置birthday
            String data;
            if (datetime != null) {
                data = getDateTime(datetime);//获取时间选择器的时间

            } else {
                data = "20000312";//默认值
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try {
                long time = sdf.parse(data).getTime();
                mMyInfo.setBirthday(time);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "initData: 不符合格式");
            }
            JMessageClient.updateMyInfo(UserInfo.Field.birthday, mMyInfo, new BasicCallback() {
                @Override
                public void gotResult(int responseCode, String responseMessage) {
                    if (responseCode == 0) {
                        Log.i(TAG, "update userinfo.birthday 成功" + "\n");
                    } else {
                        Log.i(TAG, "update userinfo.birthday 失败" + "\n");
                    }
                }
            });

            //完成返回
            finish();

        });

    }

    private String getDateTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }

    private String getTime(Date date) {
        String format = "yyyy'年'MM'月'dd'日'";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    private void initView() {
        setContentView(R.layout.activity_set_birthday);
        tvTime = findViewById(R.id.tv_birthday_time);
        bt_next = findViewById(R.id.bt_birthday_next);
    }

}

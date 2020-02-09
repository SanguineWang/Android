package com.example.helloworld.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.R;
import com.example.helloworld.circle.CircleActivity;
import com.example.helloworld.learn.CourseListActivity;
import com.example.helloworld.location.MapActivity;
import com.example.helloworld.setrelative.FriendContactManager;
import com.example.helloworld.user.MineActivity;

public class MainActivity_first extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        button2.setOnClickListener(this);
        button1.setOnClickListener(this);
        button3.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_1:
                intent.setClass(MainActivity_first.this, CourseListActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_4:
                intent.setClass(MainActivity_first.this, CircleActivity.class);
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
                intent.setClass(MainActivity_first.this, MineActivity.class);
                startActivity(intent);
                break;
        }
    }
}

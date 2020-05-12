package com.example.helloworld.learn;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.R;
import com.example.helloworld.learn.Adapter.VideoAdapter;
import com.example.helloworld.learn.entity.Video;

import java.util.List;

import cn.jzvd.Jzvd;

/**
 * @ClassName VideoActivity
 * @Description TODO
 * @Author Mr.wang
 * @Date 2020-05-12 10:02
 * @Version 1.0
 */
public class VideoActivity extends AppCompatActivity {
   private ListView lv_videos;
   private List<Video> videoList;
   private VideoAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }
    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
    private void initView() {
        setContentView(R.layout.activity_video_list);
        //列表
        lv_videos=findViewById(R.id.lv_video_list);
    }

    private void initData() {
        //数据源
        videoList=VideosDatabase.getInstance().getVideos();
        //创建适配器
        adapter=new VideoAdapter(this,videoList);
        //设置适配器
        lv_videos.setAdapter(adapter);
    }


}

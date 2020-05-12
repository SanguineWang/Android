package com.example.helloworld.learn.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.helloworld.R;
import com.example.helloworld.learn.entity.Video;
//import com.squareup.picasso.Picasso;

import java.util.List;

import cn.jzvd.JzvdStd;

/**
 * @ClassName VideoAdapter
 * @Description TODO
 * @Author Mr.wang
 * @Date 2020-05-12 15:49
 * @Version 1.0
 */
public class VideoAdapter extends BaseAdapter {
    private static final String TAG = "VideoAdapter";
    Context context;
    private List<Video> videoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_item_video, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //获取指定video item
        Video video = videoList.get(position);
        //填充发布者信息
        holder.nameTv.setText(video.getAuthor());
        //发布者Icon
        String iconUrl = video.getIcon();
        if (!TextUtils.isEmpty(iconUrl)) {
            Log.e(TAG, "发布者icon为空");
            Glide.with(context)
                    .load(iconUrl)
                    .into(holder.iconIv);
        }
        //设置视频 路径，标题和播放模式
        holder.jzvdStd.setUp(
                video.getVideo(),
                video.getTitle(),
                JzvdStd.SCREEN_NORMAL
        );
        //封面和位置
        String coverUrl = video.getCover();
        if (!TextUtils.isEmpty(coverUrl)) {
            Log.e(TAG, "封面为空");
            Glide.with(context)
                    .load(coverUrl)
                    .into(holder.jzvdStd.posterImageView);
        }
        holder.jzvdStd.positionInList = position;
        return convertView;
    }

    class ViewHolder {
        JzvdStd jzvdStd;
        ImageView iconIv;
        TextView nameTv;

        public ViewHolder(View view) {
            jzvdStd = view.findViewById(R.id.video_item_v);
            iconIv = view.findViewById(R.id.video_item_iv);
            nameTv = view.findViewById(R.id.video_item_tv_name);
        }
    }
}

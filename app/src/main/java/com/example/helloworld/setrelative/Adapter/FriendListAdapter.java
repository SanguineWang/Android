package com.example.helloworld.setrelative.Adapter;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helloworld.R;
import com.example.helloworld.setrelative.FriendContactManager;


import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendListAdapter extends BaseAdapter {
    private  static final String TAG = "FriendListAdapter";
    private List<UserInfo> mList;
    private LayoutInflater mInflater;//布局装载器对象
    private String Avatarfile;
    private UserInfo itemUserInfo;
    public FriendListAdapter(FriendContactManager friendContactManager, List<UserInfo> items) {
        mInflater = LayoutInflater.from(friendContactManager);
        mList = items;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_friend_list, null);
        }
        /**
         * 找到item布局文件中对应的控件
         */
        TextView nickname = (TextView) convertView.findViewById(R.id.tv_firends_nickname);
        ImageView friend_avatar = (ImageView) convertView.findViewById(R.id.iv_friends_avatar);
        //获取相应索引的ItemBean对象
        UserInfo item = mList.get(i);
        /**
         * 设置控件的对应属性值
         */
        nickname.setText(item.getNickname());//昵称

        //可能会出现好友没有头像缩略图，所以调用获取用户信息来下载
        //头像缩略图会在调用JMessageClient.getUserInfo(String, GetUserInfoCallback) 时自动下载。
            JMessageClient.getUserInfo(item.getUserName() , new GetUserInfoCallback() {
                @Override
                public void gotResult(int status, String desc, UserInfo userInfo) {
                    if (status == 0) {
                        if (userInfo.getAvatarFile() != null) {
                            Avatarfile = userInfo.getAvatarFile().toString();
                            friend_avatar.setImageBitmap(BitmapFactory.decodeFile(Avatarfile));

                        } else {
                            Log.e(TAG, "本地没有 "+userInfo.getUserName()+"的头像");
                            friend_avatar.setImageResource(R.drawable.ic_launcher_background);
                        }

                    } else {
                        Log.e(TAG, "gotResult: 没获取到好友： " +userInfo.getUserName()+"的信息" );
                    }
                }
            });

        return convertView;
    }
}

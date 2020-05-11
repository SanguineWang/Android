package com.example.helloworld.user.inituserinfo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.R;
import com.example.helloworld.user.inituserinfo.Bean.BitmapBean;
import com.github.cropbitmap.LikeQQCropView;

/**
 * @ClassName CutAvatarActivity
 * @Description 裁剪，移动和缩放
 * @Author Mr.wang
 * @Date 2020/2/20 18:01
 * @Version 1.0
 */
public class CutAvatarActivity extends AppCompatActivity {

    private LikeQQCropView likeView;
    private Button cut;
//    private ImageView aftercut;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {

        likeView.setBitmapForWidth(BitmapBean.selectedimagepath, 1080);

        cut.setOnClickListener(v -> {
            /**裁剪*/
//            aftercut.setImageBitmap(likeView.clip());
            BitmapBean.bitmap = likeView.clip();
            BitmapBean.aftercut=1;
            finish();
        });

        likeView.setRadius(150);/**设置裁剪框圆角*/

        likeView.setMaxScale(3);/**设置图片最大放大倍数*/
        likeView.setDoubleClickScale((float) 1.8);/**设置双击图片放大倍数*/

    }

    private void initView() {
        setContentView(R.layout.activity_cut_avatar);
        likeView = findViewById(R.id.lv_cut_avatar);
        cut = findViewById(R.id.bt_checked);
//        aftercut = findViewById(R.id.iv_cuted);

    }
}



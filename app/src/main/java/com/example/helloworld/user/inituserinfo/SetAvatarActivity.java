package com.example.helloworld.user.inituserinfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.R;
import com.example.helloworld.user.UpdateUserAvatar;
import com.example.helloworld.user.inituserinfo.Bean.BitmapBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;


/**
 * @ClassName SetAvatarActivity
 * @Description ：设置头像
 * @Author Mr.wang
 * @Date 2020/2/16 22:39
 * @Version 1.0
 */
public class SetAvatarActivity extends AppCompatActivity {
    private static final String TAG = "SetAvatarActivity";
    private ProgressDialog mProgressDialog;
    private ImageView iv_init_avatar;
    private Button bt_avatar_next;
    private String mPicturePath;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "getFile:  Bitmap 转file失败");
        }
        return file;
    }

    /**
     * 保存Bitmap到文件中
     *
     * @param bitmap \
     */
    public File saveBitmap(Bitmap bitmap) {
        String filePath;
        filePath = Environment.getExternalStorageDirectory() + "/temp.jpg";
        File f = new File(filePath);
        FileOutputStream fos = null;
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return f;
    }

    private void initData() {
        iv_init_avatar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, RESULT_LOAD_IMAGE);

        });
        bt_avatar_next.setOnClickListener(v -> {
            mProgressDialog = ProgressDialog.show(SetAvatarActivity.this, "提示：", "正在加载中。。。");
            mProgressDialog.setCanceledOnTouchOutside(true);
            if (BitmapBean.bitmap != null) {
                File file = saveBitmap(BitmapBean.bitmap);
                try {
                    JMessageClient.updateUserAvatar(file, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_SHORT).show();
                                Log.i("UpdateUserAvatar", "JMessageClient.updateUserAvatar" + ", responseCode = " + i + " ; LoginDesc = " + s);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mProgressDialog.dismiss();
                Toast.makeText(SetAvatarActivity.this, "请选择图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_set_avatar);
        iv_init_avatar = (ImageView) findViewById(R.id.iv_init_avatar);
        bt_avatar_next = findViewById(R.id.bt_avatar_next);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mPicturePath != null && BitmapBean.aftercut != 1) {
            Intent intent = new Intent(SetAvatarActivity.this, CutAvatarActivity.class);
            startActivity(intent);

        }
        if (BitmapBean.aftercut == 1) {
            iv_init_avatar.setImageBitmap(BitmapBean.bitmap);

            BitmapBean.aftercut = 0;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (null != cursor) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mPicturePath = cursor.getString(columnIndex);
                cursor.close();
            }
            BitmapBean.selectedimagepath = mPicturePath;

        }


    }
}


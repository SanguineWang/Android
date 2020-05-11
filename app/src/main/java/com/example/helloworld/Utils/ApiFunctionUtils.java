package com.example.helloworld.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.helloworld.setrelative.Old.entity.Relative;

import java.util.ArrayList;
import java.util.List;

public class ApiFunctionUtils {
    private static ApiFunctionUtils apiFunctionUtils;
    private final String TAG = "ApiFunctionUtils";

    private ApiFunctionUtils() {
    }

    public static ApiFunctionUtils getInstance() {
        if (apiFunctionUtils == null) {
            apiFunctionUtils = new ApiFunctionUtils();
        }
        return apiFunctionUtils;
    }

    /**
     * 拨打电话
     * 传入电话号码
     *
     * @param activity
     * @param phoneNum
     */
    public void call(Activity activity, String phoneNum) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            activity.startActivity(intent);
            Toast.makeText(activity, "拨打成功", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(activity, "拨打失败", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 传入亲属电话集合
     * 传入当前位置
     *
     * @param activity
     * @param location
     * @param phoneNum
     */
    public void sendMsg(Activity activity, String location, String phoneNum) {
        //对象
        //短信内容
        String content = "【紧急求助】位置:" + location;
        try {

                ArrayList<String> messages = SmsManager.getDefault().divideMessage(content);
                for (String text : messages) {
                    SmsManager.getDefault().sendTextMessage(phoneNum, null, text, null, null);
                }

            Toast.makeText(activity, "短信发送成功", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(activity, "短信发送失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 已测试
     * 获取通讯录
     * 并返回集合Relative对象
     *
     * @param context
     * @return
     */
    public List<Relative> getContects(Context context) {
        List<Relative> contects = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            String phoneNumber;
            String phoneName;
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Log.e("contect:", phoneName + "  " + phoneNumber);
                    Relative relative = new Relative(phoneName, phoneNumber);
                    contects.add(relative);
                }
                cursor.close();
            } else {
                Log.e(TAG, "getContects: cursor为null！");
            }

        } catch (Exception e) {
            e.getStackTrace();
            Log.e("通讯录：", "查找失败");
        }
        return contects;
    }


}

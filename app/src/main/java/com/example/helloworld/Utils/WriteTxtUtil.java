package com.example.helloworld.Utils;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName WriteTxtUtil
 * @Description 保存txt信息
 * @Author Mr.wang
 * @Date 2020-05-11 9:43
 * @Version 1.0
 */
public class WriteTxtUtil {

        private final static String TAG="WriteTxtUtil";
        private static WriteTxtUtil manager;

        private WriteTxtUtil() {
        }

        public static WriteTxtUtil getInstance() {
            if (manager == null) {
                manager = new WriteTxtUtil();
            }
            return manager;
        }

    /**
     * 保存动作数据
     * data 保存的内容
     * time 时间作为txt文件名
     */
    public void saveAction(String data) {
        //新建文件夹
        String folderName = "TheOldSensor";
        File sdCardDir = new File(Environment.getExternalStorageDirectory(), folderName);
        Log.d(TAG, "文件保存在："+sdCardDir.getAbsolutePath());
        if (!sdCardDir.exists()) {
            if (!sdCardDir.mkdirs()) {
                try {
                    sdCardDir.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        String fileName = "Sensor.txt";
//        String fileName = "Sensor.csv";
        //新建文件
        File saveFile = new File(sdCardDir, fileName);
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            writeData(data, false, saveFile);
        } catch (Exception e) {
            Log.i(TAG, "新建catch " + e.toString());
        }

    }
    /**
     * @param content        写入内容
     * @param isClearContent 是否清楚原来内容 true 覆盖数据 false 累加内容
     */
//每次都重新写入
    public void writeData(String content, Boolean isClearContent, File saveFile) {

//换行每次
//        content=content+"\r\n";
        try {
            if (isClearContent) {
                final FileOutputStream outStream = new FileOutputStream(saveFile);
                try {
                    //内容写入文件
                    outStream.write(content.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "writeTxtToFile: --------------" + e.toString());
                } finally {
                    try {
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //内容追加
                BufferedWriter out = null;
                try {
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile, true)));
                    out.write(content + "\r\n");
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.d(TAG,"保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

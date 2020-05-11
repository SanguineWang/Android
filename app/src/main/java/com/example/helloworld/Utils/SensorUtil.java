package com.example.helloworld.Utils;

/**
 * @ClassName SensorUtil
 * @Description TODO
 * @Author Mr.wang
 * @Date 2020-05-10 21:57
 * @Version 1.0
 */
public class SensorUtil {
    private static SensorUtil sensorUtil;
    private SensorUtil(){}

    public SensorUtil getInstance(){
        if (sensorUtil ==null){
            sensorUtil =new SensorUtil();
        }
        return sensorUtil;
    }
    public void getService(){
//        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    };
    public void stop(){

    }
}

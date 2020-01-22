package com.example.helloworld.Utils;

public class WebSocketUtils {
//单例
    private static WebSocketUtils webSocketUtils;

    private WebSocketUtils() {
    }
    public static WebSocketUtils getInstance(){
        if (webSocketUtils == null){
            webSocketUtils =new WebSocketUtils();
        }
        return webSocketUtils;
    }

}

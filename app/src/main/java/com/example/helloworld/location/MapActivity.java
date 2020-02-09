package com.example.helloworld.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.helloworld.Utils.PermissionsUtils;
import com.example.helloworld.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.content.LocationContent;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class MapActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {
    //初始化地图对象
    private MapView mMapView = null;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    //定位蓝点
    private MyLocationStyle myLocationStyle;
    public static final String TAG = "MapActivity";
    private ProgressDialog progressDialog;
    private static Integer scale = 5;
    private static final String hear_from_userName = "123456";//接受来自“test001”
    private static final String send_to_userName = "test001";//向"test001"发送消息
    private static List<UserInfo> friendsList; //向好友列表发送消息且收到好友列表的消息
    private static List<String> friendNameList;
    private Marker marker;
    private List<Marker> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);
        //初始化视图
        initView();
        //创建地图
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        //显示定位蓝点
        initMap();
        //初始化数据
        initData();

    }

    private void initView() {
        setContentView(R.layout.activity_main5_map);
        mMapView = (MapView) findViewById(R.id.map);
        //创建地图
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        setUpMap();
    }

    /**
     * 设置地图属性
     */
    private void setUpMap() {

        //设置地图的放缩级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(scale));
        // 设置定位监听
        aMap.setLocationSource(this);
        //初始化定位蓝点样式类
        myLocationStyle = new MyLocationStyle();
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。

//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
// 只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;
// 定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;
// 连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
// 连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
// 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
////以下三种模式从5.1.0版本开始提供
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
// 连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
// 连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);
// 连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        myLocationStyle.showMyLocation(true);
        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(location -> {
            //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取

            /**
             * getLatestMessage()
             * 获取最新好友集合+提取名字
             */
            Log.e(TAG, "setUpMap()/setOnMyLocationChangeListener gotresult前" + friendNameList);
            ContactManager.getFriendList(new GetUserInfoListCallback() {
                @Override
                public void gotResult(int i, String s, List<UserInfo> list) {
                    //获取好友
                    if (i == 0) {
                        if (list.size() == 0) {
                            Log.e(TAG, "gotResult: 没有好友");
                        } else {
                            //提取用户名
                            List<String> friendNames = getFriendsName(list);
                            Log.i(TAG, "setUpMap 好友列表：" + friendNames.toString());
                            //消息接收
                            List<Message> messageList = new ArrayList<>();
                            for (String fn : friendNames) {
                                Log.i(TAG, "gotResult: 进入与：" + fn + "的会话");

                                Conversation conversation = JMessageClient.getSingleConversation(fn);
                                Message latestMessage = null;
                                if (null != conversation) {
                                    latestMessage = getLastestMessageFromFriend(conversation, fn);
                                    if (latestMessage != null) {
                                        UserInfo userInfo = JMessageClient.getMyInfo();
                                        String myname = userInfo.getUserName();
//                                Log.e(TAG, "GotResult/个人信息 "+userInfo.toString());
                                        if (!latestMessage.getFromUser().getUserName().equals(myname)) {
                                            messageList.add(latestMessage);
//                                    Log.i(TAG, "好友" + latestMessage.getFromUser().getUserName() + "我的名" + myname);
                                        } else {
                                            Log.e(TAG, "拿到自己发的啦！" + latestMessage.getFromUser().getUserName());
                                        }

                                    } else {
                                        Log.e(TAG, "与" + fn + "的会话没有对方位置消息" + "latestMessage为null");
                                    }
                                } else {
                                    Log.e(TAG, "最新会话为空");
                                }
                                Log.e(TAG, "退出与：" + fn + " 的会话");
                            }

                            /**
                             * 添加标记点
                             * addmark
                             */
//                    LatLng latLng = new LatLng(39.906901, 116.397972);
//                    final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
                            if (messageList.size() == 0) {
                                Log.e(TAG, "有好友但是没有位置信息");
                            } else {
                                for (Message message : messageList) {
                                    Log.i(TAG, "待添加marker的消息 " + message.toString() + "内容类型" + message.getContentType());
//                        message.getContent().toString();

                                    LocationContent latestMessageContent = (LocationContent) message.getContent();
                                    if (latestMessageContent == null) {
                                        Log.e(TAG, "LocationContent是空的");
                                    } else {
                                        String la = latestMessageContent.getLatitude().toString();
                                        String lo = latestMessageContent.getLongitude().toString();
                                        String address = latestMessageContent.getAddress();
                                        String frieNa = message.getFromUser().getNickname();
                                        Log.i(TAG, "待添加marker的位置: 经纬度" + la + "   " + lo + "好友昵称：" + frieNa + "地址：" + address);
                                        //经纬度类型转换
                                        addMark(Double.parseDouble(la), Double.parseDouble(lo), frieNa, address);
//                            LatLng latLng2 = new LatLng(Double., (double) latestMessageContent.getLongitude());

                                    }

//                        addMark((double) latestMessageContent.getLatitude(), (double) latestMessageContent.getLongitude(), message.getFromUser().getUserName(), latestMessageContent.getAddress());
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "获取好友列表失败 " +
                                "获取失败" + ", responseCode = " + i + " ; LoginDesc = " + s);
                    }


                }
            });
            Log.e(TAG, "setUpMap()/setOnMyLocationChangeListener gotresult后" + friendNameList);

        });
    }

    private void initData() {

//        List<Relative> relativeList;
        //获取实例化功能工具类，获取通讯录
//        relativeList = ApiFunctionUtils.getInstance().getContects(this);测试成功
        //拨打电话
//        ApiFunctionUtils.getInstance().call(this,"13069896563");测试成功
        //发短信
//        List<String> numbers=new ArrayList<>();测试成功
//        numbers.add("13069896563");
//        ApiFunctionUtils.getInstance().sendMsg(this,"xxx" ,numbers);
    }

    /**
     * 获取会话记录中好友发送的最新一条消息
     *
     * @param conversation
     * @param friendname
     * @return
     */
    private Message getLastestMessageFromFriend(Conversation conversation, String friendname) {

        List<Message> allMessage = conversation.getAllMessage();
        Message lastestMessageFromFriend = null;
        if (allMessage != null) {

            Optional<Message> optionalMessage = allMessage.stream()
                    .filter(message -> message.getFromUser().getUserName().equals(friendname))
                    .max(Comparator.comparing(Message::getCreateTime));
            if (optionalMessage.isPresent()) {
                lastestMessageFromFriend = optionalMessage.get();
                Log.i(TAG, "getMessageFromFriend:与" + friendname + "会话流中取到的最新消息 ：" + lastestMessageFromFriend.toString());

            } else {
                Log.e(TAG, "getLastestMessageFromFriend: 与：" + friendname + "的会话中，它的地理信息为空");
            }

        } else {
            Log.e(TAG, "getMessageFromFriend: 消息为空");
        }
        return lastestMessageFromFriend;
    }

    /**
     * 从好友列表信息提取名字
     * 为发送消息做准备
     *
     * @param userInfoList
     * @return
     */
    private List<String> getFriendsName(List<UserInfo> userInfoList) {

        return userInfoList.stream()
                .map(UserInfo::getUserName)
                .collect(Collectors.toList());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        //删除会话
//        for (String username : friendNameList) {
//            Log.e(TAG, "onDestroy: " + friendNameList.toString());
//            JMessageClient.deleteSingleConversation(username);
//        }

        JMessageClient.unRegisterEventReceiver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
    //--定位监听---------

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }

    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    public void onEventMainThread(OfflineMessageEvent event) {
        Conversation conversation = event.getConversation();
        List<Message> newMessageList = event.getOfflineMessageList();//获取此次离线期间会话收到的新消息列表
        List<Integer> offlineMsgIdList = new ArrayList<Integer>();
        if (conversation != null && newMessageList != null) {
            for (Message msg : newMessageList) {
                offlineMsgIdList.add(msg.getId());
            }
            Log.e(TAG, "onEventMainThread:收到" + newMessageList.size() + "条来自" + conversation.getTargetId() + "的离线消息");
            Log.e(TAG, "onEventMainThread:会话类型 = " + conversation.getType() + "\n消息ID = " + offlineMsgIdList);
///
//              mTv_showOfflineMsg.append("会话类型 = " + conversation.getType() + "\n消息ID = " + offlineMsgIdList + "\n\n");
        } else {
            Log.e(TAG, "会话为空");

        }
    }

    //定位回调  在回调方法中调用“mListener.onLocationChanged(amapLocation);”可以在地图上显示系统小蓝点。
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                Log.e("位置：", aMapLocation.getAddress());
                /**
                 * 发送经纬度 sendLocation(double latitude, double longitude, Integer scale, String address)
                 *
                 */
                sendLocation(aMapLocation.getLatitude(), aMapLocation.getLongitude(), scale, aMapLocation.getAddress());


            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("定位AmapErr", errText);
            }
        }
    }


    /**
     * @param latitude
     * @param longitude
     * @param scale
     * @param address
     */
    private void sendLocation(double latitude, double longitude, Integer scale, String address) {
        LocationContent content = new LocationContent(latitude, longitude, scale, address);

        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {

                if (i == 0) {
                    friendsList = list;
                    if (friendsList.size() == 0) {
                        Log.e(TAG, "gotResult: 没有好友");
                    }
//                    Log.i(TAG, "sendLocation /goresult好友列表：" + friendsList.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "ContactManager.getFriendList " +
                            "获取失败" + ", responseCode = " + i + " ; LoginDesc = " + s);
                }
                //提取用户名
                friendNameList = getFriendsName(friendsList);
                Log.i(TAG, "sendLocation /gotresult 关联好友个数：" + friendNameList.size());

                if (friendNameList.size() == 0) {
                    Log.e(TAG, "好友名为空");
                }
                for (String friendName : friendNameList) {
                    Conversation conversation = Conversation.createSingleConversation(friendName);
                    Message msg = conversation.createSendMessage(content);
                    msg.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i == 0) {
                                Log.e(TAG, "eLocationContent定位" + "发送成功" + ", responseCode = " + i + " ; Desc = " + s);
                            } else {
                                Log.e(TAG, "LocationContent定位" + "发送失败" + ", responseCode = " + i + " ; Desc = " + s);
                            }
                        }
                    });
                    JMessageClient.sendMessage(msg);
                }
            }
        });


    }

    private void addMark(double latitude, double longitude, String username, String address) {

        /**
         * 持续定位
         * 下次定位之前删除之前的定位点
         */
        if (list != null) {
            for (Marker marker : list) {
                marker.remove();
            }
        }
        //          经纬度
        LatLng latLng = new LatLng(latitude, longitude);
//        绘制点标记
        Log.i(TAG, "addMark: " + latLng.toString());
        MarkerOptions options = new MarkerOptions();
        options.position(latLng)
                .title(username)
//        点标记的内容
                .snippet(address);
//        添加蓝点
        marker = aMap.addMarker(options);
        Log.i(TAG, "addMark: 添加成功");
        list.add(marker);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}



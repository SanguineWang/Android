package com.example.helloworld.learn;

import com.example.helloworld.learn.entity.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName VideosDatabase
 * @Description TODO
 * @Author Mr.wang
 * @Date 2020-05-12 15:01
 * @Version 1.0
 */
public class VideosDatabase {
    private static VideosDatabase videosDatabase;

    private VideosDatabase() {
    }
    public static VideosDatabase getInstance() {
        if (videosDatabase == null) {
            videosDatabase = new VideosDatabase();
        }
        return videosDatabase;
    }
    public static void main(String[] args) {
       VideosDatabase.getInstance().getVideos().forEach(video -> {
           System.out.println(video.getId());
       });
    }


    private static List<Video> videos=new ArrayList<>();
    public List<Video> getVideos(){
        return videos;
    }
    static {
       videos.add( new Video(0,
               0,
               "微信打不开怎么回事",
               "https://ksv-video-picture.cdn.bcebos.com/b22ea43da0e757f6c55d34cdc69fbd7e6a56924f.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
               "https://ksv-video-publish.cdn.bcebos.com/2ab5d4db0609b7b688a08da8616af141162f82ab.mp4?auth_key=1635864051-0-0-6898c3044dce287652bdb0036ba24a45",
               "https://ksv-video-picture.cdn.bcebos.com/8bd4e5705b3ce67496d899b2843959a88069f058.jpg",
               "每日科技fans"
       ));
        videos.add( new Video(1,
                0,
                "微信删除的聊天记录如何恢复",
                "https://ksv-video-picture.cdn.bcebos.com/6b121e3501d5b258501c814735de03ada0204623.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/c02788cd5f06015277c473d259119a080ea39a4a.mp4?auth_key=1635864051-0-0-7f2d2534577748757140d2b222438fad",
                "https://ksv-video-picture.cdn.bcebos.com/8bd4e5705b3ce67496d899b2843959a88069f058.jpg",
                "办公资源"
        ));
        videos.add( new Video(2,
                0,
                "微信忘记密码怎么办",
                "https://ksv-video-picture.cdn.bcebos.com/55134af7eb53337e6cce26b831a960d661a6f9c2.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/828808444116f0ac8710f6b0ce33d22561d55514.mp4?auth_key=1635922416-0-0-0bc9d90a010aa693d446d9f2d4c8d8b3",
                "https://ksv-video-picture.cdn.bcebos.com/616a10eaa806775fb5ccbdb7f7365237f6494ee4.jpg",
                "脑栋大开"
        ));
        videos.add( new Video(3,
                0,
                "微信怎么切换账号登录",
                "https://ksv-video-picture.cdn.bcebos.com/b05958a8776735a81efa5565db9dd5fb793e7a3b.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/a92809bd377e16c363ff6d41eb0418cf5cd0ac5f.mp4?auth_key=1635922416-0-0-e58feeeb16ca9564398cfd031710fc7b",
                "https://ksv-video-picture.cdn.bcebos.com/c9fc03db5bf02662d85aa93ef9c871e52af4ca54.jpg",
                "零分猫"
        ));
        videos.add( new Video(4,
                0,
                "发朋友圈的步骤",
                "https://ksv-video-picture.cdn.bcebos.com/e2e0e94e218008bc05481ac4bedd5fa5462fe411.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/9b5b6e7e74d194adc1b4cc657cbbbbc82520ff4c.mp4?auth_key=1635922932-0-0-03adfc5ad425630ee0093334f430d94f",
                "https://ksv-video-picture.cdn.bcebos.com/014f8ce9e987bae03c83ccfc911aead6e378c0c3.jpg",
                "小熊科技视频"
        ));
        videos.add( new Video(5,
                0,
                "微信朋友圈上对朋友仅展示三天动态操作",
                "https://ksv-video-picture.cdn.bcebos.com/11c218d952ba62967c15f6f8669634bfa28cddce.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/c17563180e581bacc5c89405c206ed307d243fac.mp4?auth_key=1635922932-0-0-fb02fb0e1e682c90d4b4e74c8225b89b",
                "https://ksv-video-picture.cdn.bcebos.com/014f8ce9e987bae03c83ccfc911aead6e378c0c3.jpg",
                "小熊科技视频"
        ));
        videos.add( new Video(6,
                0,
                "微信怎么转发",
                "https://ksv-video-picture.cdn.bcebos.com/320eaa7c5cf1046878d5349d895d20b588ae0336.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/c17563180e581bacc5c89405c206ed307d243fac.mp4?auth_key=1635922932-0-0-fb02fb0e1e682c90d4b4e74c8225b89b",
                "https://ksv-video-picture.cdn.bcebos.com/8bd4e5705b3ce67496d899b2843959a88069f058.jpg",
                "每日科技fans"
        ));
        videos.add( new Video(7,
                0,
                "微信怎么转发别人的朋友圈",
                "https://ksv-video-picture.cdn.bcebos.com/20661523833b04c1786fd8c975d1eee8d6d16137.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/69a450144af88fdb85bf79f2756732a1b28a59ec.mp4?auth_key=1635932368-0-0-87e65a9d07a7af384b56e05dfabcfcc9",
                "https://ksv-video-picture.cdn.bcebos.com/616a10eaa806775fb5ccbdb7f7365237f6494ee4.jpg",
                "脑栋大开"
        ));
        videos.add( new Video(8,
                0,
                "怎样把英语语音翻译成文字",
                "https://ksv-video-picture.cdn.bcebos.com/3cc3bd8444cdcb88f47842c41efc10e6bf6dfaff.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/fa10e58b866cbf43f54f0be6de12f7e3c3742c0f.mp4?auth_key=1635932368-0-0-dfbdf41cab71205a858c149a638b0562",
                "https://hiphotos.baidu.com/baike/pic/item/f3d3572c11dfa9ec839472106dd0f703918fc17a.jpg",
                "玩转手机小..."
        ));
        videos.add( new Video(9,
                0,
                "微信收藏的视频和图片怎样...",
                "https://ksv-video-picture.cdn.bcebos.com/9e4549909e77d8e7164299986fb62a38321aff32.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/a56a2347a2067d21f6c08b67eb0b30ad3cbd7124.mp4?auth_key=1635932368-0-0-c6e6f8d6bb4b096883e4f7ccaf57917a",
                "https://ksv-video-picture.cdn.bcebos.com/e7d40ad85b69dff4ab9d072530cd158aa051bfd8.jpg",
                "太平洋电脑"

        ));
        videos.add( new Video(10,
                0,
                "朋友圈怎么@好友名字",
                "https://ksv-video-picture.cdn.bcebos.com/eb184f4e3a32ae5f8e3d2dc7e15fe9be66a77396.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/104e7da4afc28a9fbfb640a13502ff5841cb52d6.mp4?auth_key=1635932368-0-0-5ba6baf1ebc1f5394c1402d5d1128a6f",
                "https://ksv-video-picture.cdn.bcebos.com/616a10eaa806775fb5ccbdb7f7365237f6494ee4.jpg",
                "脑栋大开"
        ));
        videos.add( new Video(11,
                0,
                "微信复制粘贴怎么用",
                "https://ksv-video-picture.cdn.bcebos.com/f7e0d6e6c9529dcf869884fa0513cffe4fb8d456.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/f306aacc42cf6e35e011b21ebcee01ee39ad4360.mp4?auth_key=1635932368-0-0-86fc430c700b8f301c59daaf73117a48",
                "https://ksv-video-picture.cdn.bcebos.com/616a10eaa806775fb5ccbdb7f7365237f6494ee4.jpg",
                "脑栋大开"
        ));
        videos.add( new Video(12,
                0,
                "朋友圈怎样发送语音",
                "https://ksv-video-picture.cdn.bcebos.com/f588e7f54b481f90fc511caac3137fc4acab0129.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/4ec43f19433240a298da3b3f86f81b2024b5ed21.mp4?auth_key=1635922772-0-0-57f67fb13164c8449f5aedc050056b64",
                 "https://ksv-video-picture.cdn.bcebos.com/c9fc03db5bf02662d85aa93ef9c871e52af4ca54.jpg",
                "零分猫"
        ));
        videos.add( new Video(13,
                0,
                "微信怎么发红包",
                "https://ksv-video-picture.cdn.bcebos.com/c690e4e0e6279f635f62a6e43b5d479bb2a4e630.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_640%2Ch_360",
                "https://ksv-video-publish.cdn.bcebos.com/cea3dbec7cc37eaaaaa0d3e518f810a27330d30a.mp4?auth_key=1635922547-0-0-66fdc7e7a4665f22e0c0be100caf4d7a",
                "https://ksv-video-picture.cdn.bcebos.com/616a10eaa806775fb5ccbdb7f7365237f6494ee4.jpg",
                "脑栋大开"
        ));
    }

}

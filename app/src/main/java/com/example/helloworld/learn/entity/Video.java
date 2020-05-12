package com.example.helloworld.learn.entity;

/**
 * @ClassName Video
 * @Description TODO
 * @Author Mr.wang
 * @Date 2020-05-12 14:40
 * @Version 1.0
 */
public class Video {
    private int id;//id
    private int courseId; //课程id
    private String title; //标题
    private String cover; //封面
    private String video; //视频
    private String icon; //作者icon
    private String author; //作者

    public Video(int id, int courseId, String title, String cover, String video, String icon, String author) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.cover = cover;
        this.video = video;
        this.icon = icon;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public String getVideo() {
        return video;
    }

    public String getIcon() {
        return icon;
    }

    public String getAuthor() {
        return author;
    }
}

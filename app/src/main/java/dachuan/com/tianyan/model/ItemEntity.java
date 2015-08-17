package dachuan.com.tianyan.model;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by xizi@linsj on 2015/7/21.
 *
 */
public class ItemEntity extends SugarRecord<ItemEntity> implements Serializable {

    private String title;

    private String sort;

    private int time;

    private int duration;

    private boolean cached;

    private String imageURL;

    private String videoUrl;

    private String describe;

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getVideoUrl() {

        return videoUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public ItemEntity() {
    }

    public String getTitle() {
        return title;
    }

    public String getSort() {
        return sort;
    }

    public int getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isCached() {
        return cached;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

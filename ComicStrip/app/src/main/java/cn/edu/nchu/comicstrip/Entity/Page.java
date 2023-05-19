package cn.edu.nchu.comicstrip.Entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.sql.Blob;

public class Page extends LitePalSupport  implements Serializable {

    //页面Id
    private int id;
    //页面图片
    private int imageId;
    //页面内容
    private String content;
    //是否收藏页面
    private boolean isCollect;

    public Page() {
    }

    public Page(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public Page(int imageId, String content, boolean isCollect) {
        this.imageId = imageId;
        this.content = content;
        this.isCollect = isCollect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", imageId=" + imageId +
                ", content='" + content + '\'' +
                ", isCollect=" + isCollect +
                '}';
    }
}

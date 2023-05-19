package cn.edu.nchu.comicstrip.Entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Bookmark extends LitePalSupport implements Serializable {
    /**
     *   书签实体类，存放了对应连环画页面的内容，
     *   可以直接跳转到对应的页面查看，并且可以接着往下看
     * */

    private int id;
    private String name;
    private int pageId;

    public Bookmark(int id, String name, int pageId) {
        this.id = id;
        this.name = name;
        this.pageId = pageId;
    }

    public Bookmark(String name, int pageId) {
        this.name = name;
        this.pageId = pageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pageId=" + pageId +
                '}';
    }
}

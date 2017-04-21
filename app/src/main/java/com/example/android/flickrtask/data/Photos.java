package com.example.android.flickrtask.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuel on 4/21/2017.
 */

public class Photos {
    private int page ;
    private int pages ;
    private int perpage ;
    private int total ;
    private List<PhotoInfo> photo = new ArrayList<>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PhotoInfo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoInfo> photo) {
        this.photo = photo;
    }
}

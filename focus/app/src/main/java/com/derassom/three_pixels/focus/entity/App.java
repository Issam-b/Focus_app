package com.derassom.three_pixels.focus.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "app")
public class App {

    @PrimaryKey(autoGenerate = true)
    private int appid;

    @ColumnInfo(name = "app_name")
    private String appName;

    @ColumnInfo(name = "pkg_name")
    private String pkgName;

    @ColumnInfo(name = "num_blocks")
    private int numBlocks;


    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }


    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }


    public int getNumBlocks() {
        return numBlocks;
    }

    public void setNumBlocks(int numBlocks) {
        this.numBlocks = numBlocks;
    }

}

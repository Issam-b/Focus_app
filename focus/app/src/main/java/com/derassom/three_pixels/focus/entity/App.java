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

    @ColumnInfo(name = "app_duration")
    private int appDuration;

    @ColumnInfo(name = "app_bonus")
    private int appBonus;

    @ColumnInfo(name = "num_blocks")
    private int numBlocks;

    @ColumnInfo(name = "num_open")
    private int numOpen;

    @ColumnInfo(name = "num_allowed")
    private int numAllowed;

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

    public int getAppDuration() {
        return appDuration;
    }
    public void setAppDuration(int appDuration) {
        this.appDuration = appDuration;
    }

    public int getAppBonus() {
        return appBonus;
    }
    public void setAppBonus(int appBonus) {
        this.appBonus = appBonus;
    }

    public int getNumBlocks() {
        return numBlocks;
    }
    public void setNumBlocks(int numBlocks) {
        this.numBlocks = numBlocks;
    }

    public int getNumOpen() {
        return numOpen;
    }
    public void setNumOpen(int numOpen) {
        this.numOpen = numOpen;
    }

    public int getNumAllowed() {
        return numAllowed;
    }
    public void setNumAllowed(int numAllowed) {
        this.numAllowed = numAllowed;
    }
}

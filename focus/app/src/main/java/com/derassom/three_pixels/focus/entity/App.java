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
    private long appDuration;

    @ColumnInfo(name = "app_bonus")
    private int appBonus;

    @ColumnInfo(name = "num_blocks")
    private int numBlocks;

    @ColumnInfo(name = "num_open")
    private int numOpen;

    @ColumnInfo(name = "time_allowed")
    private long timeAllowed;
    @ColumnInfo(name = "app_enabled")
    private boolean enabled;


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

    public long getAppDuration() {
        return appDuration;
    }
    public void setAppDuration(long appDuration) {
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

    public long getTimeAllowed() {
        return timeAllowed;
    }
    public void setTimeAllowed(long timeAllowed) {
        this.timeAllowed = timeAllowed;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString()
    {
        StringBuffer buffer= new StringBuffer();
        buffer.append(this.appid);
        buffer.append(" ");
        buffer.append(this.appName);
        buffer.append(" ");
        buffer.append(this.pkgName);
        buffer.append(" ");
        buffer.append(this.appDuration);
        buffer.append(" ");
        buffer.append(this.appBonus);
        buffer.append(" ");
        buffer.append(this.numBlocks);
        buffer.append(" ");
        buffer.append(this.numOpen);
        buffer.append(" ");
        buffer.append(this.timeAllowed);
        buffer.append(" ");
        buffer.append(this.enabled);
        buffer.append(" ");
        return buffer.toString();
    }
}

package com.example.administrator.mysuperplayer.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrator.mysuperplayer.AppManager;

/**
 * Created by admin on 2017/8/21.
 */

public  class Album implements Parcelable {
    private String AlubmIdl; //专辑ID
    private int videototal;//集数
    private String title; //专辑名称
    private String SubTitle; //子标题
    private String dirctor; //导演
    private String mainActor; //主演
    private String verImgUrl; // 专辑竖图
    private String horImgUrl; //专辑横图
    private String alubmDesc; //专辑描述
    private Site site; //网站
    private String tip; //提示
    private boolean isCompleted;//是否已经播完
    private String letvStyle; //乐视特殊字段
    private Context mContext;


    public static final  Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>(){

        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int i) {
            return new Album[i];
        }
    };

    public Album(int siteId, Context context) {
     site = new Site(siteId,mContext);
        mContext= context;
    }

    public String getAlubmIdl() {
        return AlubmIdl;
    }

    public void setAlubmIdl(String alubmIdl) {
        AlubmIdl = alubmIdl;
    }

    public int getVideototal() {
        return videototal;
    }

    public void setVideototal(int videototal) {
        this.videototal = videototal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return SubTitle;
    }

    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    public String getDirctor() {
        return dirctor;
    }

    public void setDirctor(String dirctor) {
        this.dirctor = dirctor;
    }

    public String getMainActor() {
        return mainActor;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public String getVerImgUrl() {
        return verImgUrl;
    }

    public void setVerImgUrl(String verImgUrl) {
        this.verImgUrl = verImgUrl;
    }

    public String getHorImgUrl() {
        return horImgUrl;
    }

    public void setHorImgUrl(String horImgUrl) {
        this.horImgUrl = horImgUrl;
    }

    public String getAlubmDesc() {
        return alubmDesc;
    }

    public void setAlubmDesc(String alubmDesc) {
        this.alubmDesc = alubmDesc;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site  site) {
        this.site = site;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getLetvStyle() {
        return letvStyle;
    }

    public void setLetvStyle(String letvStyle) {
        this.letvStyle = letvStyle;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    private Album(Parcel in) {
        this.AlubmIdl = in.readString();
        this.videototal = in.readInt();
        this.title = in.readString();
        this.dirctor = in.readString();
        this.mainActor = in.readString();
        this.horImgUrl = in.readString();
        this.verImgUrl = in.readString();
        this.tip = in.readString();
        this.letvStyle = in.readString();
        this.alubmDesc = in.readString();
        this.SubTitle = in.readString();
        this.site = new Site(in.readInt(),mContext);
        this.isCompleted = in.readByte()!=0 ;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(AlubmIdl);
        parcel.writeInt(videototal);
        parcel.writeString(title);
        parcel.writeString(SubTitle);
        parcel.writeString(dirctor);
        parcel.writeString(mainActor);
        parcel.writeString(verImgUrl);
        parcel.writeString(horImgUrl);
        parcel.writeString(alubmDesc);
        parcel.writeString(tip);
        parcel.writeString(letvStyle);
        parcel.writeInt(site.getSiteId());
        parcel.writeByte((byte) (isCompleted() ?0:1) );


    }

    @Override
    public String toString() {
        return "Album{" +
                "AlubmIdl='" + AlubmIdl + '\'' +
                ", videototal=" + videototal +
                ", title='" + title + '\'' +
                ", SubTitle='" + SubTitle + '\'' +
                ", dirctor='" + dirctor + '\'' +
                ", mainActor='" + mainActor + '\'' +
                ", verImgUrl='" + verImgUrl + '\'' +
                ", horImgUrl='" + horImgUrl + '\'' +
                ", alubmDesc='" + alubmDesc + '\'' +
                ", site=" + site +
                ", tip='" + tip + '\'' +
                ", isCompleted=" + isCompleted +
                ", letvStyle='" + letvStyle + '\'' +
                ", mContext=" + mContext +
                '}';
    }

    public  String toJson(){
        String ret = AppManager.getGson().toJson(this);
        return  ret;
    }
    public  Album fromJson(String json){
        Album alubm = AppManager.getGson().fromJson(json,Album.class);
        return alubm;
    }
}

package com.lime.mypol.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2015-06-09.
 */
public class MemberInfo implements Serializable {
    private boolean logOn;
    private String memberId;
    private int logonTypeId;
    private String memberNickname;
    private String address;
    private Date birthDate;
    private String gender;
    private String urlThumbnail;

    public MemberInfo(){
        logOn = false;
    }

    public MemberInfo(String memberId, int logonTypeId, String memberNickname) {
        this.logOn = true;
        this.memberId = memberId;
        this.logonTypeId = logonTypeId;
        this.memberNickname = memberNickname;
        this.address = null;
        this.birthDate = null;
        this.gender = null;
        this.urlThumbnail = null;
    }

    public boolean isLogOn() {
        return logOn;
    }

    public void setLogOn(boolean logOn) {
        this.logOn = logOn;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getLogonTypeId() {
        return logonTypeId;
    }

    public void setLogonTypeId(int logonTypeId) {
        this.logonTypeId = logonTypeId;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

}

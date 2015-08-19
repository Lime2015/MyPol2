package com.lime.mypol.models;

/**
 * Created by seongsan on 2015-08-18.
 */
public class Comment {
    private long id;
    private String memberName;
    private String memberPhotoUrl;
    private int commentType;
    private String targetId;
    private String comment;
    private int like;
    private int dislike;

    public Comment(){}

    public Comment(long id, String memberName, String memberPhotoUrl, int commentType, String targetId, String comment, int like, int dislike) {
        this.id = id;
        this.memberName = memberName;
        this.memberPhotoUrl = memberPhotoUrl;
        this.commentType = commentType;
        this.targetId = targetId;
        this.comment = comment;
        this.like = like;
        this.dislike = dislike;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhotoUrl() {
        return memberPhotoUrl;
    }

    public void setMemberPhotoUrl(String memberPhotoUrl) {
        this.memberPhotoUrl = memberPhotoUrl;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }
}

package com.lime.mypol.models;

/**
 * Created by seongsan on 2015-08-18.
 */
public class Comment {
    private long id;
    private String memberId;
    private String memberIconUrl;
    private int commentType;
    private String targetId;
    private String comment;
    private int like;
    private int dislike;

    public Comment(){}

    public Comment(long id, String memberId, String memberIconUrl, int commentType, String targetId, String comment, int like, int dislike) {
        this.id = id;
        this.memberId = memberId;
        this.memberIconUrl = memberIconUrl;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberIconUrl() {
        return memberIconUrl;
    }

    public void setMemberIconUrl(String memberIconUrl) {
        this.memberIconUrl = memberIconUrl;
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

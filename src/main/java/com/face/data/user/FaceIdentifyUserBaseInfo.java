package com.face.data.user;

/**
 * Created by andy on 12/8/15.
 */
public class FaceIdentifyUserBaseInfo {

    private Long id;
    private String userName;
    private String nickName;
    private String headImageUrl;
    private Double confidence;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((FaceIdentifyUserBaseInfo)obj).getId());
    }
}

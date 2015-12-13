package com.face.data.user;

/**
 * Created by andy on 12/8/15.
 */
public class UserBaseInfo {

    private Long id;
    private String userName;
    private String nickName;
    private String headImageUrl;
    private String certificationImageUrl;
    private int secret;
    private int certification;
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

    public String getCertificationImageUrl() {
        return certificationImageUrl;
    }

    public void setCertificationImageUrl(String certificationImageUrl) {
        this.certificationImageUrl = certificationImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }
    public int getSecret() {
        return secret;
    }
    public void setSecret(int secret) {
        this.secret = secret;
    }

    public int getCertification() {
        return certification;
    }

    public void setCertification(int certification) {
        this.certification = certification;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((UserBaseInfo)obj).getId());
    }
}

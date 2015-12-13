package com.face.data.user;

/**
 * Created by andy on 12/11/15.
 */
public class UserSecretRequest {
    private Long id;
    private Integer secret;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getSecret() {
        return secret;
    }

    public void setSecret(Integer secret) {
        this.secret = secret;
    }
}

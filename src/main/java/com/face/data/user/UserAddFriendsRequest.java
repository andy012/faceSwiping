package com.face.data.user;

/**
 * Created by andy on 12/13/15.
 */
public class UserAddFriendsRequest {

    private String userId;
    private String imageUrl;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

package com.face.data.youtu;

import java.io.Serializable;

/**
 * Created by andy on 12/13/15.
 */
public class NewFriendBean implements Serializable {

    private String name;

    private String headImageUrl;

    private String content;

    private String fromResource;

    private boolean isAddedFriend;

    private String groupImageUrl;

    public String getGroupImageUrl() {
        return groupImageUrl;
    }

    public void setGroupImageUrl(String groupImageUrl) {
        this.groupImageUrl = groupImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromResource() {
        return fromResource;
    }

    public void setFromResource(String fromResource) {
        this.fromResource = fromResource;
    }

    public boolean isAddedFriend() {
        return isAddedFriend;
    }

    public void setIsAddedFriend(boolean isAddedFriend) {
        this.isAddedFriend = isAddedFriend;
    }
}
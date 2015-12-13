package com.face.data.user;

import java.io.Serializable;

/**
 * Created by andy on 12/13/15.
 */
public class FriendBean implements Serializable {

    private String name;
    private String headerImageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }
}
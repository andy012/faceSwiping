package com.face.data.user;

import com.face.data.base.DataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/13/15.
 */
public class UserAddFriendsRecordResponse extends DataBase{

    //UserAddFriendsRequest userAddFriendsRequest=new UserAddFriendsRequest();

    List<UserAddFriendsRequest> userAddFriendsRequests=new ArrayList<>();

    List<FriendBean> friends=new ArrayList<>();

    public List<UserAddFriendsRequest> getUserAddFriendsRequests() {
        return userAddFriendsRequests;
    }

    public void setUserAddFriendsRequests(List<UserAddFriendsRequest> userAddFriendsRequests) {
        this.userAddFriendsRequests = userAddFriendsRequests;
    }

    public List<FriendBean> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendBean> friends) {
        this.friends = friends;
    }
}

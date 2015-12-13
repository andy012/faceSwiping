package com.face.data.user;

import com.face.data.base.DataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/13/15.
 */
public class UserAddFriendsResponse extends DataBase{

    List<String> data=new ArrayList<>();

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}

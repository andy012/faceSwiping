package com.face.data.youtu;

import com.face.data.base.DataBase;
import com.face.data.user.FaceIdentifyUserBaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/10/15.
 */
public class UserFaceIdentifyResponse extends DataBase{

    private int x;
    private int y;


    private int width;
    private int height;

    private List<FaceIdentifyUserBaseInfo> data =new ArrayList<>();

    public List<FaceIdentifyUserBaseInfo> getData() {
        return data;
    }

    public void setData(List<FaceIdentifyUserBaseInfo> data) {
        this.data = data;
    }

    public void add(FaceIdentifyUserBaseInfo faceIdentifyUserBaseInfo){
        if(!data.contains(faceIdentifyUserBaseInfo)){
            data.add(faceIdentifyUserBaseInfo);
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

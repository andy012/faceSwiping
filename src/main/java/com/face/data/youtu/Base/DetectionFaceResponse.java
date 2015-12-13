package com.face.data.youtu.Base;

import java.util.List;

/**
 * Created by andy on 12/12/15.
 */
public class DetectionFaceResponse {


    /**
     * face : [{"glass":true,"expression":25,"gender":22,"beauty":82,"roll":-4,"yaw":3,"x":152,"width":98,"face_id":"1297089116784099327","y":73,"pitch":10,"age":26,"height":98},{"glass":false,"expression":41,"gender":97,"beauty":73,"roll":6,"yaw":0,"x":308,"width":94,"face_id":"1297089118060216319","y":78,"pitch":-7,"age":44,"height":94},{"glass":false,"expression":65,"gender":99,"beauty":74,"roll":8,"yaw":-4,"x":466,"width":111,"face_id":"1297089119353110527","y":121,"pitch":5,"age":47,"height":111}]
     * image_height : 517
     * session_id :
     * image_width : 690
     * errorcode : 0
     * errormsg : OK
     */

    private int image_height;
    private String session_id;
    private int image_width;
    private int errorcode;
    private String errormsg;
    /**
     * glass : true
     * expression : 25
     * gender : 22
     * beauty : 82
     * roll : -4
     * yaw : 3
     * x : 152
     * width : 98
     * face_id : 1297089116784099327
     * y : 73
     * pitch : 10
     * age : 26
     * height : 98
     */

    private List<FaceEntity> face;

    public void setImage_height(int image_height) {
        this.image_height = image_height;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setImage_width(int image_width) {
        this.image_width = image_width;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public void setFace(List<FaceEntity> face) {
        this.face = face;
    }

    public int getImage_height() {
        return image_height;
    }

    public String getSession_id() {
        return session_id;
    }

    public int getImage_width() {
        return image_width;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public List<FaceEntity> getFace() {
        return face;
    }

    public static class FaceEntity {
        private boolean glass;
        private int expression;
        private int gender;
        private int beauty;
        private int roll;
        private int yaw;
        private int x;
        private int width;
        private String face_id;
        private int y;
        private int pitch;
        private int age;
        private int height;
        private String base64;
        public String getBase64() {
            return base64;
        }

        public void setBase64(String base64) {
            this.base64 = base64;
        }

        public void setGlass(boolean glass) {
            this.glass = glass;
        }

        public void setExpression(int expression) {
            this.expression = expression;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public void setBeauty(int beauty) {
            this.beauty = beauty;
        }

        public void setRoll(int roll) {
            this.roll = roll;
        }

        public void setYaw(int yaw) {
            this.yaw = yaw;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setFace_id(String face_id) {
            this.face_id = face_id;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void setPitch(int pitch) {
            this.pitch = pitch;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public boolean isGlass() {
            return glass;
        }

        public int getExpression() {
            return expression;
        }

        public int getGender() {
            return gender;
        }

        public int getBeauty() {
            return beauty;
        }

        public int getRoll() {
            return roll;
        }

        public int getYaw() {
            return yaw;
        }

        public int getX() {
            return x;
        }

        public int getWidth() {
            return width;
        }

        public String getFace_id() {
            return face_id;
        }

        public int getY() {
            return y;
        }

        public int getPitch() {
            return pitch;
        }

        public int getAge() {
            return age;
        }

        public int getHeight() {
            return height;
        }
    }
}

package com.face.data.youtu;

import com.face.data.base.DataBase;
import com.face.data.util.ResponseCode;
import javafx.scene.chart.PieChart;

import java.util.LinkedHashMap;

/**
 * Created by andy on 12/8/15.
 */
public class UserFacesResponse extends DataBase{

    private LinkedHashMap<String ,String> facesUrlMap=new LinkedHashMap<>();

    public UserFacesResponse(){}
    public UserFacesResponse(LinkedHashMap<String ,String> facesUrlMap){
        this.facesUrlMap=facesUrlMap;
        setResponseCode();

    }
    public LinkedHashMap<String, String> getFacesUrlMap() {
        return facesUrlMap;
    }

    public void setFacesUrlMap(LinkedHashMap<String, String> facesUrlMap) {
        this.facesUrlMap = facesUrlMap;
    }

    public void setResponseCode(){
        if(facesUrlMap.size()==0){
            this.errorcode=ResponseCode.DATA_ZERO.getCode();
            this.errormsg=ResponseCode.DATA_ZERO.getDescription();
        }else if(facesUrlMap.size()>0){
            this.errorcode=ResponseCode.SUCCESS.getCode();
            this.errormsg=ResponseCode.SUCCESS.getDescription();
        }
    }

}

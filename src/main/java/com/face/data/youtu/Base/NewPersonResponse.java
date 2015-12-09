package com.face.data.youtu.Base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/9/15.
 */
public class NewPersonResponse {
    @JsonProperty("person_id")
    private String personId;
    @JsonProperty("suc_group")
    private Integer sucGroup;
    @JsonProperty("suc_face")
    private Integer sucFace;
    @JsonProperty("errorcode")
    private Integer errorcode;
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("face_id")
    private String faceId;
    @JsonProperty("group_ids")
    private List<String> groupIds = new ArrayList<String>();
    @JsonProperty("errormsg")
    private String errormsg;

    /**
     *
     * @return
     * The personId
     */
    @JsonProperty("person_id")
    public String getPersonId() {
        return personId;
    }

    /**
     *
     * @param personId
     * The person_id
     */
    @JsonProperty("person_id")
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     *
     * @return
     * The sucGroup
     */
    @JsonProperty("suc_group")
    public Integer getSucGroup() {
        return sucGroup;
    }

    /**
     *
     * @param sucGroup
     * The suc_group
     */
    @JsonProperty("suc_group")
    public void setSucGroup(Integer sucGroup) {
        this.sucGroup = sucGroup;
    }

    /**
     *
     * @return
     * The sucFace
     */
    @JsonProperty("suc_face")
    public Integer getSucFace() {
        return sucFace;
    }

    /**
     *
     * @param sucFace
     * The suc_face
     */
    @JsonProperty("suc_face")
    public void setSucFace(Integer sucFace) {
        this.sucFace = sucFace;
    }

    /**
     *
     * @return
     * The errorcode
     */
    @JsonProperty("errorcode")
    public Integer getErrorcode() {
        return errorcode;
    }

    /**
     *
     * @param errorcode
     * The errorcode
     */
    @JsonProperty("errorcode")
    public void setErrorcode(Integer errorcode) {
        this.errorcode = errorcode;
    }

    /**
     *
     * @return
     * The sessionId
     */
    @JsonProperty("session_id")
    public String getSessionId() {
        return sessionId;
    }

    /**
     *
     * @param sessionId
     * The session_id
     */
    @JsonProperty("session_id")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     *
     * @return
     * The faceId
     */
    @JsonProperty("face_id")
    public String getFaceId() {
        return faceId;
    }

    /**
     *
     * @param faceId
     * The face_id
     */
    @JsonProperty("face_id")
    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    /**
     *
     * @return
     * The groupIds
     */
    @JsonProperty("group_ids")
    public List<String> getGroupIds() {
        return groupIds;
    }

    /**
     *
     * @param groupIds
     * The group_ids
     */
    @JsonProperty("group_ids")
    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    /**
     *
     * @return
     * The errormsg
     */
    @JsonProperty("errormsg")
    public String getErrormsg() {
        return errormsg;
    }

    /**
     *
     * @param errormsg
     * The errormsg
     */
    @JsonProperty("errormsg")
    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    @Override
    public String toString() {
        return "NewPersonResponse{" +
                "personId='" + personId + '\'' +
                ", sucGroup=" + sucGroup +
                ", sucFace=" + sucFace +
                ", errorcode=" + errorcode +
                ", sessionId='" + sessionId + '\'' +
                ", faceId='" + faceId + '\'' +
                ", groupIds=" + groupIds +
                ", errormsg='" + errormsg + '\'' +
                '}';
    }
}
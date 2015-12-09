package com.face.data.youtu.Base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/9/15.
 */
public class AddFacesResponse {
    @JsonProperty("added")
    private Integer added;
    @JsonProperty("face_ids")
    private List<String> faceIds = new ArrayList<String>();
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("ret_codes")
    private List<Integer> retCodes = new ArrayList<Integer>();
    @JsonProperty("errorcode")
    private Integer errorcode;
    @JsonProperty("errormsg")
    private String errormsg;

    /**
     *
     * @return
     * The added
     */
    @JsonProperty("added")
    public Integer getAdded() {
        return added;
    }

    /**
     *
     * @param added
     * The added
     */
    @JsonProperty("added")
    public void setAdded(Integer added) {
        this.added = added;
    }

    /**
     *
     * @return
     * The faceIds
     */
    @JsonProperty("face_ids")
    public List<String> getFaceIds() {
        return faceIds;
    }

    /**
     *
     * @param faceIds
     * The face_ids
     */
    @JsonProperty("face_ids")
    public void setFaceIds(List<String> faceIds) {
        this.faceIds = faceIds;
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
     * The retCodes
     */
    @JsonProperty("ret_codes")
    public List<Integer> getRetCodes() {
        return retCodes;
    }

    /**
     *
     * @param retCodes
     * The ret_codes
     */
    @JsonProperty("ret_codes")
    public void setRetCodes(List<Integer> retCodes) {
        this.retCodes = retCodes;
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
}

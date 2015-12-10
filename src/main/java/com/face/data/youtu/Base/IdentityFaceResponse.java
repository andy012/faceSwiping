package com.face.data.youtu.Base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/10/15.
 */
public class IdentityFaceResponse {

    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("candidates")
    private List<Candidate> candidates = new ArrayList<Candidate>();
    @JsonProperty("errorcode")
    private Integer errorcode;
    @JsonProperty("errormsg")
    private String errormsg;



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
     * The candidates
     */
    @JsonProperty("candidates")
    public List<Candidate> getCandidates() {
        return candidates;
    }

    /**
     *
     * @param candidates
     * The candidates
     */
    @JsonProperty("candidates")
    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
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

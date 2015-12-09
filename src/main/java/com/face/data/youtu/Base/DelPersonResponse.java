package com.face.data.youtu.Base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by andy on 12/9/15.
 */
public class DelPersonResponse {
    @JsonProperty("deleted")
    private Integer deleted;
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("errorcode")
    private Integer errorcode;
    @JsonProperty("errormsg")
    private String errormsg;
    @JsonProperty("person_id")
    private String personId;

    /**
     *
     * @return
     * The deleted
     */
    @JsonProperty("deleted")
    public Integer getDeleted() {
        return deleted;
    }

    /**
     *
     * @param deleted
     * The deleted
     */
    @JsonProperty("deleted")
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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
}

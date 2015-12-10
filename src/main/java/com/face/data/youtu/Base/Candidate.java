package com.face.data.youtu.Base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by andy on 12/10/15.
 */
public class Candidate {
    @JsonProperty("person_id")
    private String personId;
    @JsonProperty("face_id")
    private String faceId;
    @JsonProperty("confidence")
    private Double confidence;
    @JsonProperty("tag")
    private String tag;

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
     * The confidence
     */
    @JsonProperty("confidence")
    public Double getConfidence() {
        return confidence;
    }

    /**
     *
     * @param confidence
     * The confidence
     */
    @JsonProperty("confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    /**
     *
     * @return
     * The tag
     */
    @JsonProperty("tag")
    public String getTag() {
        return tag;
    }

    /**
     *
     * @param tag
     * The tag
     */
    @JsonProperty("tag")
    public void setTag(String tag) {
        this.tag = tag;
    }
}

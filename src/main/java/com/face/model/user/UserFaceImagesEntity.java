package com.face.model.user;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by andy on 12/8/15.
 */
@Entity
@Table(name = "USER_FACE_IMAGES", schema = "", catalog = "faceSwiping")
public class UserFaceImagesEntity {
    private long id;
    private long userId;
    private String imageKey;
    private Timestamp createTime;
    private String faceId;

    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_ID", nullable = false, insertable = true, updatable = true)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "IMAGE_KEY", nullable = false, insertable = true, updatable = true, length = 256)
    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = false, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFaceImagesEntity that = (UserFaceImagesEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (imageKey != null ? !imageKey.equals(that.imageKey) : that.imageKey != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (imageKey != null ? imageKey.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "FACE_ID", nullable = false, insertable = true, updatable = true, length = 50)
    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }
}

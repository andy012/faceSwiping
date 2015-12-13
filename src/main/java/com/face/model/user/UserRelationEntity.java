package com.face.model.user;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by andy on 12/11/15.
 */
@Entity
@Table(name = "USER_RELATION", schema = "", catalog = "faceSwiping")
public class UserRelationEntity {
    private long id;
    private long userId;
    private long targetId;
    private byte accepted;
    private Timestamp updateTime;

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
    @Column(name = "TARGET_ID", nullable = false, insertable = true, updatable = true)
    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    @Basic
    @Column(name = "ACCEPTED", nullable = false, insertable = true, updatable = true)
    public byte getAccepted() {
        return accepted;
    }

    public void setAccepted(byte accepted) {
        this.accepted = accepted;
    }

    @Basic
    @Column(name = "UPDATE_TIME", nullable = false, insertable = true, updatable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRelationEntity that = (UserRelationEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (targetId != that.targetId) return false;
        if (accepted != that.accepted) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (targetId ^ (targetId >>> 32));
        result = 31 * result + (int) accepted;
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}

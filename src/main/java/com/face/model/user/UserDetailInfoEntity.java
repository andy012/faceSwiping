package com.face.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by andy on 12/8/15.
 */
@Entity
@Table(name = "USER_DETAIL_INFO", schema = "", catalog = "faceSwiping")
public class UserDetailInfoEntity implements Serializable {


    @Id
    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;
    @Basic
    @Column(name = "NICK_NAME", nullable = true, insertable = true, updatable = true, length = 50)
    private String nickName;
    @Basic
    @Column(name = "HEAD_IMAGE_KEY", nullable = true, insertable = true, updatable = true, length = 256)
    private String headImageKey;
    @Basic
    @Column(name = "GE_TUI_CLIENT_ID", nullable = false, insertable = true, updatable = true, length = 50)
    private String geTuiClientId;

    public UserDetailInfoEntity(){}

    public UserDetailInfoEntity(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "NICK_NAME", nullable = true, insertable = true, updatable = true, length = 50)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

       @Basic
       @Column(name = "HEAD_IMAGE_KEY", nullable = true, insertable = true, updatable = true, length = 256)
       public String getHeadImageKey() {
        return headImageKey;
    }

    public void setHeadImageKey(String headImageKey) {
        this.headImageKey = headImageKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetailInfoEntity that = (UserDetailInfoEntity) o;
        if (this.user.getId()!=that.user.getId()) return false;
        if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) return false;
        if (headImageKey != null ? !headImageKey.equals(that.headImageKey) : that.headImageKey != null) return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = (int) (user.getId() ^ (user.getId() >>> 32));
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (headImageKey != null ? headImageKey.hashCode() : 0);
        return result;
    }
       public String getGeTuiClientId() {
        return geTuiClientId;
    }

    public void setGeTuiClientId(String geTuiClientId) {
        this.geTuiClientId = geTuiClientId;
    }
}

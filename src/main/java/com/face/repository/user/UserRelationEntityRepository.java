package com.face.repository.user;


import com.face.data.user.UserFriendsResponse;
import com.face.model.user.User;
import com.face.model.user.UserRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRelationEntityRepository extends JpaRepository<UserRelationEntity, Long> {
    @Query(value = "from UserRelationEntity userRelationEntity where userRelationEntity.userId=?1 and userRelationEntity.targetId= ?2 order by userRelationEntity.updateTime desc")
    public UserRelationEntity getFriend(Long userId,Long friendId);
    @Query(value = "from UserRelationEntity userRelationEntity where userRelationEntity.targetId= ?1 and userRelationEntity.accepted=0 order by userRelationEntity.updateTime desc")
    public List<UserRelationEntity> getFriendWithOutAccepted(Long targetId);
    @Query(value = "from UserRelationEntity userRelationEntity where userRelationEntity.userId= ?1 and userRelationEntity.accepted= 1 order by userRelationEntity.updateTime desc")
    public List<UserRelationEntity> getFriendHaveAccepted(Long targetId);
}

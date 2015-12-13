package com.face.repository.user;


import com.face.data.user.UserFriendsResponse;
import com.face.model.user.User;
import com.face.model.user.UserRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UserRelationEntityRepository extends JpaRepository<UserRelationEntity, Long> {
    @Query(value = "from UserRelationEntity userRelationEntity where userRelationEntity.userId=?1 and userRelationEntity.targetId= ?2")
    public UserRelationEntity getFriend(Long userId,Long friendId);
}

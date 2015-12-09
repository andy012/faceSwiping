package com.face.repository.user;


import com.face.model.user.User;
import com.face.model.user.UserDetailInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UserDetailInfoEntityRepository extends JpaRepository<UserDetailInfoEntity, Long> {



}

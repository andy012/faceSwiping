package com.face.repository.user;


import com.face.model.user.User;
import com.face.model.user.UserFaceImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UserFaceImagesEntityRepository extends JpaRepository<UserFaceImagesEntity, Long> {

	public List<UserFaceImagesEntity> findAllByUserId(final Long userId);
	public UserFaceImagesEntity findOneByImageKey(final String imageKey);
	@Modifying
	@Transactional
	@Query("delete from UserFaceImagesEntity userFaceImagesEntity where userFaceImagesEntity.userId = ?1")
	public void deleteAllByUserId(final Long userId);
	@Modifying
	@Transactional
	@Query("delete from UserFaceImagesEntity userFaceImagesEntity where userFaceImagesEntity.imageKey = ?1")
	public void deleteOneByImageKey(final String imageKey);
}

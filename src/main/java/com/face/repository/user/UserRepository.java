package com.face.repository.user;


import com.face.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {


	public User findByUsername(String username);

	@Modifying
	@Query(value = "update `user` set COMPANY_ID = ?1 where id = ?2 ", nativeQuery = true)
	public int updateCompanyIdById(final Long companyId, final Long id);
	@Query(value = "from User user where user.id in (select userRelationEntity.targetId from UserRelationEntity userRelationEntity where userRelationEntity.userId=?1 and userRelationEntity.accepted= 1 order by userRelationEntity.updateTime desc ) ")
	public List<User> findFriends(Long userId);

}

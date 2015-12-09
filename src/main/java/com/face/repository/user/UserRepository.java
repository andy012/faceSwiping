package com.face.repository.user;


import com.face.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Long> {


	public User findByUsername(String username);

	@Modifying
	@Query(value = "update `user` set COMPANY_ID = ?1 where id = ?2 ", nativeQuery = true)
	public int updateCompanyIdById(final Long companyId, final Long id);
}

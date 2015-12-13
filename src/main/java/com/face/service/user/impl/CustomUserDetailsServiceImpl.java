package com.face.service.user.impl;

import com.face.data.user.UserFriendsResponse;
import com.face.data.util.ResponseCode;
import com.face.model.user.User;
import com.face.model.user.UserDetailInfoEntity;
import com.face.model.user.UserRelationEntity;
import com.face.repository.user.UserDetailInfoEntityRepository;
import com.face.repository.user.UserRelationEntityRepository;
import com.face.repository.user.UserRepository;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserRelationEntityRepository userRelationEntityRepository;
	@Autowired
	private UserDetailInfoEntityRepository userDetailInfoEntityRepository;
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();


	@Override
	public final User loadUserByUsername(String username) throws UsernameNotFoundException {

		final User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		detailsChecker.check(user);
		return user;
	}



	public User save(User user){
		return userRepo.save(user);
	}

	@Override
	public User findOne(Long userId) {
		return userRepo.findOne(userId);
	}

	@Override
	public List<User> findFriends(Long userId) {
		return userRepo.findFriends(userId);
	}

	@Override
	public UserDetailInfoEntity save(UserDetailInfoEntity userDetailInfoEntity) {
		return userDetailInfoEntityRepository.save(userDetailInfoEntity);
	}

	@Override
	public UserFriendsResponse getUserFriendsResponse(Long userId,QiniuService qiniuService) {
		List<User> users=findFriends(userId);
		UserFriendsResponse userFriendsResponse=new UserFriendsResponse();

		for(User user:users){
			userFriendsResponse.addFriend(user,qiniuService);
		}
		userFriendsResponse.setResponseCode(ResponseCode.SUCCESS);
		return userFriendsResponse;
	}

	@Override
	public UserFriendsResponse addFriends(Long userId, Long friendId,QiniuService qiniuService) {

		UserRelationEntity userRelationEntity=new UserRelationEntity();
		userRelationEntity.setUserId(userId);
		userRelationEntity.setAccepted((byte) 1);
		userRelationEntity.setTargetId(friendId);

		userRelationEntityRepository.save(userRelationEntity);


		return getUserFriendsResponse(userId,qiniuService);
	}

	@Override
	public UserRelationEntity getFriend(Long userId, Long friendId) {
		return userRelationEntityRepository.getFriend(userId,friendId);
	}

}

package com.face.service.user.impl;

import com.face.data.user.FriendBean;
import com.face.data.user.UserAddFriendsRecordResponse;
import com.face.data.user.UserAddFriendsRequest;
import com.face.data.user.UserFriendsResponse;
import com.face.data.util.ResponseCode;
import com.face.model.user.User;
import com.face.model.user.UserDetailInfoEntity;
import com.face.model.user.UserFaceImagesEntity;
import com.face.model.user.UserRelationEntity;
import com.face.repository.user.UserDetailInfoEntityRepository;
import com.face.repository.user.UserRelationEntityRepository;
import com.face.repository.user.UserRepository;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.CustomUserDetailsService;
import com.face.service.user.UserFaceImagesService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
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


		UserRelationEntity userRelationEntity=userRelationEntityRepository.getFriend(userId, friendId);
		if(userRelationEntity==null) {
			userRelationEntity = new UserRelationEntity();
		}
		userRelationEntity.setUserId(userId);
		userRelationEntity.setAccepted((byte) 1);
		userRelationEntity.setTargetId(friendId);

		userRelationEntity.setRequestData("");
		userRelationEntityRepository.save(userRelationEntity);



		return getUserFriendsResponse(userId, qiniuService);
	}

	@Override
	public UserRelationEntity save(UserRelationEntity userRelationEntity) {
		return userRelationEntityRepository.save(userRelationEntity);
	}

	@Override
	public UserRelationEntity getFriend(Long userId, Long friendId) {
		return userRelationEntityRepository.getFriend(userId, friendId);
	}

	@Override
	public List<UserRelationEntity> getFriendWithOutAccepted(Long targetId) {
		return userRelationEntityRepository.getFriendWithOutAccepted(targetId);
	}

	@Override
	public List<UserRelationEntity> getFriendHaveAccepted(Long targetId) {
		return userRelationEntityRepository.getFriendHaveAccepted(targetId);
	}

	@Override
	public UserAddFriendsRecordResponse getUserAddFriendsRecordResponse(Long userId,QiniuService qiniuService,UserFaceImagesService userFaceImagesService) throws IOException {
		List<UserRelationEntity> userRelationEntities=getFriendWithOutAccepted(userId);
		List<User> users=userRepo.findFriends(userId);
		UserAddFriendsRecordResponse userAddFriendsRecordResponse=new UserAddFriendsRecordResponse();
		List<UserAddFriendsRequest> userAddFriendsRequests=userAddFriendsRecordResponse.getUserAddFriendsRequests();
		for(UserRelationEntity userRelationEntity:userRelationEntities){
			if(userRelationEntity.getRequestData()!=null && userRelationEntity.getRequestData().length()!=0) {
				UserAddFriendsRequest userAddFriendsRequest = new ObjectMapper().readValue(userRelationEntity.getRequestData(), UserAddFriendsRequest.class);
				userAddFriendsRequests.add(userAddFriendsRequest);
			}
		}


		List<FriendBean> friendBeans= userAddFriendsRecordResponse.getFriends();


		for(User user:users){

			FriendBean friendBean=new FriendBean();
			friendBean.setName(user.getUserDetailInfoEntity().getNickName());

			List<UserFaceImagesEntity>  userFaceImagesEntities=userFaceImagesService.findAllByUserId(user.getId());

			if(userFaceImagesEntities.size()>0){
				System.out.println("======="+userFaceImagesEntities.size());
				System.out.println(qiniuService.createPrivateUrl(userFaceImagesEntities.get(0).getImageKey()));
				friendBean.setHeaderImageUrl(qiniuService.createPrivateUrl(userFaceImagesEntities.get(0).getImageKey()));
			}else {
				friendBean.setHeaderImageUrl(qiniuService.createPrivateUrl(user.getUserDetailInfoEntity().getHeadImageKey()));;
			}
			friendBeans.add(friendBean);
		}






		return userAddFriendsRecordResponse;
	}




}

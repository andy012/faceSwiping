package com.face.service.user;

import com.face.data.user.UserAddFriendsRecordResponse;
import com.face.data.user.UserFriendsResponse;
import com.face.model.user.User;
import com.face.model.user.UserDetailInfoEntity;
import com.face.model.user.UserRelationEntity;
import com.face.service.qiniu.QiniuService;
import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.data.jpa.repository.Query;

import java.io.IOException;
import java.util.List;

public interface CustomUserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {
	public  User loadUserByUsername(String username);
	public User save(User user);
	public  User findOne(Long userId);
	public List<User> findFriends(Long userId);
	public UserDetailInfoEntity save(UserDetailInfoEntity userDetailInfoEntity);
	public UserFriendsResponse getUserFriendsResponse(Long userId,QiniuService qiniuService);
	public UserFriendsResponse addFriends(Long userId,Long friendId,QiniuService qiniuService);
	public UserRelationEntity save(UserRelationEntity userRelationEntity);
	public UserRelationEntity getFriend(Long userId,Long friendId);

	public List<UserRelationEntity> getFriendWithOutAccepted(Long targetId);

	public List<UserRelationEntity> getFriendHaveAccepted(Long userId);
	public UserAddFriendsRecordResponse getUserAddFriendsRecordResponse(Long userId,QiniuService qiniuService,UserFaceImagesService userFaceImagesService) throws IOException;
}

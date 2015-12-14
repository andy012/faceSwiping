package com.face.web.user;

import com.alibaba.fastjson.JSON;
import com.face.data.mapper.UrlMapper;
import com.face.data.user.*;
import com.face.data.util.ResponseCode;
import com.face.data.youtu.UserAddFacesResponse;
import com.face.data.youtu.UserFaceIdentifyRequest;
import com.face.model.common.ResultInfo;
import com.face.model.user.*;
import com.face.repository.user.UserRepository;
import com.face.service.getui.Push2Single;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.CustomUserDetailsService;

import com.face.service.user.UserFaceImagesService;
import com.face.web.common.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gexin.rp.sdk.base.IPushResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController extends BaseController {

	@Autowired
	private QiniuService qiniuService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	Push2Single push2Single;
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	UserFaceImagesService userFaceImagesService;
	@RequestMapping(value = "/api/user/current", method = RequestMethod.GET)
	public UserBaseInfo getCurrent() {
		return getUserBase();
	}
	@RequestMapping(value = "/api/user/currentUser", method = RequestMethod.GET)
	public User getCurrentUser() {
		return getUser();
	}
	/**
	 * 用户登录失败后的处理逻辑
	 * @return
	 */
	@RequestMapping(value = "/loginFailure",method = RequestMethod.GET)
	public ResultInfo loginFailure(){

		ResultInfo resultInfo=new ResultInfo();
		return resultInfo;

	}

	@RequestMapping(value = "/api/users/current", method = RequestMethod.PATCH)
	public ResponseEntity<String> changePassword(@RequestBody final User user) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final User currentUser = userRepository.findByUsername(authentication.getName());

		if (user.getNewPassword() == null || user.getNewPassword().length() < 4) {
			return new ResponseEntity<String>("new password to short", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		final BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
		if (!pwEncoder.matches(user.getPassword(), currentUser.getPassword())) {
			return new ResponseEntity<String>("old password mismatch", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		currentUser.setPassword(pwEncoder.encode(user.getNewPassword()));
		userRepository.saveAndFlush(currentUser);
		return new ResponseEntity<String>("password changed", HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/api/users/{user}/grant/role/{role}", method = RequestMethod.POST)
	public ResponseEntity<String> grantRole(@PathVariable User user, @PathVariable UserRole role) {
		if (user == null) {
			return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		user.grantRole(role);
		userRepository.saveAndFlush(user);
		return new ResponseEntity<String>("role granted", HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/api/users/{user}/revoke/role/{role}", method = RequestMethod.POST)
	public ResponseEntity<String> revokeRole(@PathVariable User user, @PathVariable UserRole role) {
		if (user == null) {
			return new ResponseEntity<String>("invalid user id", HttpStatus.UNPROCESSABLE_ENTITY);
		}

		user.revokeRole(role);
		userRepository.saveAndFlush(user);
		return new ResponseEntity<String>("role revoked", HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/api/users", method = RequestMethod.GET)
	public List<User> list() {
		return userRepository.findAll();
	}



	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String createAccount(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("rpassword") String rpassword, HttpServletRequest request, HttpServletResponse response,ModelMap model) throws UnsupportedEncodingException {
		String phone=request.getParameter("phone");
		try {
			if (password.trim().equals(rpassword.trim()) == false) {
				model.addAttribute("errorMessage", "密码不匹配"); //signup.invalid.password.notmatching
				response.sendRedirect("/home/signup.html?errorMessage="+java.net.URLEncoder.encode("密码不匹配","utf-8"));


                return null;
			}
            if(userRepository.findByUsername(username.trim())!=null){
                response.sendRedirect("/home/signup.html?errorMessage="+java.net.URLEncoder.encode("邮箱已存在","utf-8"));
                return null;
            }
			User user = new User();
			user.grantRole(UserRole.USER);
			//设置用户名
			user.setUsername(username.trim());
			//设置密码
			BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
			user.setPassword(bCryptPasswordEncoder.encode(password.trim()));
			try{
				customUserDetailsService.save(user);
                response.sendRedirect("/home/login.html?message=" + java.net.URLEncoder.encode("感谢您的注册，请登录，初次登录后您需要填写一些基本信息", "utf-8"));
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(java.net.URLEncoder.encode("用户已经存在","utf-8"));
                response.sendRedirect("/home/signup.html?errorMessage="+java.net.URLEncoder.encode("邮箱已存在","utf-8"));
                return null;

            }

		} catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendRedirect("/home/signup.html?errorMessage="+java.net.URLEncoder.encode("不能创建新的用户,请重新输入","utf-8"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
		}
	}



	@RequestMapping(value = "/user/secret/{value}" ,method = RequestMethod.PUT)
	public UserLoginResponse changeSecret(@PathVariable int value,HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user=getUser();
		User user1=customUserDetailsService.findOne(user.getId());
		user1.setSecret(value == 1 ? true:false);
		customUserDetailsService.save(user1);
		UserLoginResponse userLoginResponse =new UserLoginResponse();
		userLoginResponse.setResponseCode(ResponseCode.SUCCESS);
		userLoginResponse.setUser(user1,qiniuService,userFaceImagesService);
		return userLoginResponse;
	}

	@RequestMapping(value = "/user/getui/{clientId}" ,method = RequestMethod.PUT)
	public UserLoginResponse setGeTuiClientId(@PathVariable String clientId ,HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user=getUser();
		User user1=customUserDetailsService.findOne(user.getId());
		System.out.println("-------"+clientId);
		user1.getUserDetailInfoEntity().setGeTuiClientId(clientId);
		customUserDetailsService.save(user1);
		UserLoginResponse userLoginResponse =new UserLoginResponse();
		userLoginResponse.setResponseCode(ResponseCode.SUCCESS);
		userLoginResponse.setUser(user1,qiniuService,userFaceImagesService);
		return userLoginResponse;
	}

	@RequestMapping(value = "/user/{userId}/friend/{key}" ,method = RequestMethod.POST)
	public UserAddFriendsResponse requestAddFriends(@PathVariable Long userId,@PathVariable String key,HttpServletRequest request, HttpServletResponse response) throws IOException {

		User user=getUser();
		UserRelationEntity userRelationEntity=customUserDetailsService.getFriend(user.getId(), userId);

		if(userRelationEntity==null) {
			userRelationEntity = new UserRelationEntity();
			userRelationEntity.setTargetId(userId);
			userRelationEntity.setUserId(user.getId());
		}

		User realUser = customUserDetailsService.findOne(user.getId());
		User otherUser = customUserDetailsService.findOne(userId);
		UserAddFriendsRequest userAddFriendsRequest=new UserAddFriendsRequest();
		userAddFriendsRequest.setImageUrl(qiniuService.createPrivateUrl(key));
		userAddFriendsRequest.setUserId(user.getId());

		List<UserFaceImagesEntity> userFaceImagesEntities=userFaceImagesService.findAllByUserId(realUser.getId());

		System.out.println("------------"+userFaceImagesEntities.size());
		if(userFaceImagesEntities.size()>0) {
			userAddFriendsRequest.setHeadImageUrl(qiniuService.createPrivateUrl(userFaceImagesEntities.get(0).getImageKey()));
		}else {
			userAddFriendsRequest.setHeadImageUrl(qiniuService.createPrivateUrl(realUser.getUserDetailInfoEntity().getHeadImageKey()));
		}
		userAddFriendsRequest.setUserName(realUser.getUsername());
		userAddFriendsRequest.setNickName(realUser.getUserDetailInfoEntity().getNickName());
		userRelationEntity.setRequestData(JSON.toJSONString(userAddFriendsRequest));
		customUserDetailsService.save(userRelationEntity);
		IPushResult ret =push2Single.pushTransmissionTemplate(otherUser.getUserDetailInfoEntity().getGeTuiClientId(),"请求添加好友");
		System.out.println("正常：" + ret.getResponse().toString());
		UserAddFriendsResponse userAddFriendsResponse=new UserAddFriendsResponse();
		userAddFriendsResponse.setResponseCode(ResponseCode.SUCCESS);
		return userAddFriendsResponse;
	}

	@RequestMapping(value = "/user/friend/add" ,method = RequestMethod.POST)
	public UserAddFriendsResponse requestAddFriendsByJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserRelationEntity userRelationEntity=new UserRelationEntity();
		UserAddFriendsRequest userAddFriendsRequest= new ObjectMapper().readValue(request.getInputStream(), UserAddFriendsRequest.class);
		User user=getUser();
		userRelationEntity.setTargetId(userAddFriendsRequest.getUserId());
		userRelationEntity.setUserId(user.getId());
		User otherUser = customUserDetailsService.findOne(userAddFriendsRequest.getUserId());
		userAddFriendsRequest.setImageUrl(qiniuService.createPrivateUrl(userAddFriendsRequest.getImageUrl()));
		userAddFriendsRequest.setUserId(user.getId());
		User realUser = customUserDetailsService.findOne(user.getId());
		userAddFriendsRequest.setHeadImageUrl(qiniuService.createPrivateUrl(realUser.getUserDetailInfoEntity().getHeadImageKey()));
		userAddFriendsRequest.setUserName(realUser.getUsername());
		userAddFriendsRequest.setNickName(realUser.getUserDetailInfoEntity().getNickName());
		userRelationEntity.setRequestData(JSON.toJSONString(userAddFriendsRequest));
		customUserDetailsService.save(userRelationEntity);
		IPushResult ret =push2Single.pushTransmissionTemplate(otherUser.getUserDetailInfoEntity().getGeTuiClientId(),userAddFriendsRequest);
		System.out.println("正常：" + ret.getResponse().toString());
		UserAddFriendsResponse userAddFriendsResponse=new UserAddFriendsResponse();
		userAddFriendsResponse.setResponseCode(ResponseCode.SUCCESS);
		return userAddFriendsResponse;
	}
	@RequestMapping(value = "/user/qiniu/token" ,method = RequestMethod.GET)
	public UserQiniuTokenResponse getQiniuToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserQiniuTokenResponse userQiniuTokenResponse=new UserQiniuTokenResponse();
		userQiniuTokenResponse.setData(qiniuService.getToken());
		userQiniuTokenResponse.setResponseCode(ResponseCode.SUCCESS);
		return userQiniuTokenResponse;
	}
	@RequestMapping(value = "/user/friendsRecordResponse" ,method = RequestMethod.GET)
	public UserAddFriendsRecordResponse get(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserAddFriendsRecordResponse userAddFriendsRecordResponse=customUserDetailsService.getUserAddFriendsRecordResponse(getUser().getId(),qiniuService,userFaceImagesService);
		return userAddFriendsRecordResponse;
	}

	@RequestMapping(value = "/user/certification" ,method = RequestMethod.GET)
	public UserCertificationResponse getCertification(HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user=getUser();
		int imageLength=userFaceImagesService.findAllByUserId(user.getId()).size();
		UserCertificationResponse userCertificationResponse=new UserCertificationResponse();
		userCertificationResponse.setData(imageLength==1?1:0);
		userCertificationResponse.setResponseCode(ResponseCode.SUCCESS);
		return userCertificationResponse;
	}

	@RequestMapping(value = "/user/friends" ,method = RequestMethod.GET)
	public UserFriendsResponse getContacts(HttpServletRequest request, HttpServletResponse response) throws IOException {

		User user = getUser();
		UserFriendsResponse userFriendsResponse=customUserDetailsService.getUserFriendsResponse(user.getId(), qiniuService);
		return userFriendsResponse;
	}
	@RequestMapping(value = "/user/friend/{userId}" ,method = RequestMethod.GET)
	public UserFriendsResponse addFriends(@PathVariable Long userId,HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user=getUser();
		UserFriendsResponse userFriendsResponse = customUserDetailsService.addFriends(user.getId(), userId, qiniuService);
		customUserDetailsService.addFriends(userId,user.getId(),  qiniuService);
		//push2Single.pushTransmissionTemplate(userId.toString(), "object");
		return userFriendsResponse;
	}

	@RequestMapping(value = "/user/friend/accept/{userId}" ,method = RequestMethod.GET)
	public UserFriendsResponse acceptAddFriend(@PathVariable Long userId,HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(userId);
		User user=getUser();
		UserFriendsResponse userFriendsResponse = new UserFriendsResponse();//customUserDetailsService.addFriends(user.getId(),userId, qiniuService);
		UserRelationEntity userRelationEntity=customUserDetailsService.getFriend(userId,user.getId());
		userRelationEntity.setAccepted((byte) 1);
		customUserDetailsService.save(userRelationEntity);



		UserRelationEntity userRelationEntity1=customUserDetailsService.getFriend(userRelationEntity.getTargetId(),userRelationEntity.getUserId());
		if(userRelationEntity1==null)
			userRelationEntity1=new UserRelationEntity();
		userRelationEntity1.setUserId(userRelationEntity.getTargetId());
		userRelationEntity1.setTargetId(userRelationEntity.getUserId());
		userRelationEntity1.setAccepted(userRelationEntity.getAccepted());
		userRelationEntity1.setRequestData("");
		customUserDetailsService.save(userRelationEntity1);
		push2Single.pushTransmissionTemplate(userId.toString(), "object");
		userFriendsResponse.setResponseCode(ResponseCode.SUCCESS);
		return userFriendsResponse;
	}

	/**
	 * 获取当前登录用户
	 * @return User 用户实体
	 */
	public UserBaseInfo getUserBase() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user= ((UserAuthentication)authentication).getDetails();

		UserBaseInfo userBaseInfo=new UserBaseInfo();
		userBaseInfo.setId(user.getId());
		userBaseInfo.setUserName(user.getUsername());
		userBaseInfo.setNickName(user.getUserDetailInfoEntity().getNickName() == null ? "" : user.getUserDetailInfoEntity().getNickName());
		userBaseInfo.setSecret(user.isSecret()?1:0);
		userBaseInfo.setHeadImageUrl(UrlMapper.map2Url(user.getUserDetailInfoEntity().getHeadImageKey(),qiniuService));
		return userBaseInfo;
	}




}

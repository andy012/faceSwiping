package com.face.web.user;

import com.face.data.mapper.UrlMapper;
import com.face.data.user.UserBaseInfo;
import com.face.model.common.ResultInfo;
import com.face.model.user.User;
import com.face.model.user.UserAuthentication;
import com.face.repository.user.UserRepository;
import com.face.model.user.UserRole;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.CustomUserDetailsService;

import com.face.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
public class UserController extends BaseController {

	@Autowired
	private QiniuService qiniuService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@RequestMapping(value = "/api/user/current", method = RequestMethod.GET)
	public UserBaseInfo getCurrent() {
		return getUserBase();
	}

	/**
	 * 用户登录成功后的处理逻辑
	 * @return
	 */
	@RequestMapping(value = "/loginSuccess",method = RequestMethod.GET)
	public @ResponseBody ResultInfo<Map<String, Object>> loginSuccess(){

		ResultInfo<Map<String, Object>> resultInfo=new ResultInfo<Map<String, Object>>();
		resultInfo.setData(getUser().getSimpleObject());
		return resultInfo;
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
		userBaseInfo.setNickName(user.getUserDetailInfoEntity().getNickName()==null ? "":user.getUserDetailInfoEntity().getNickName());
		userBaseInfo.setHeadImageUrl(UrlMapper.map2Url(user.getUserDetailInfoEntity().getHeadImageKey(),qiniuService));
		return userBaseInfo;
	}




}

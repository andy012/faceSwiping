package com.face.web.common;

import com.face.data.user.UserBaseInfo;
import com.face.model.user.User;
import com.face.model.user.UserAuthentication;
import com.face.util.Constants.IsValid;
import com.face.util.Util;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 所有controller可继承，封装controller需要调用的方法
 * @author Jason Xie 2015-11-10 12:14:29
 *
 */
public class BaseController {

	/**
	 * 获取当前登录用户
	 * @return User 用户实体
	 */
	public User getUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return ((UserAuthentication)authentication).getDetails();

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
		userBaseInfo.setNickName(user.getUserDetailInfoEntity().getNickName());

		return userBaseInfo;

	}

	/**
	 * 设置用户ID
	 * @param obj 实体对象
	 * @param request HttpServletRequest http请求对象
	 * @throws Exception 设置失败时抛出异常
	 */
	protected void setUserInfo(Object obj) throws Exception{
		if (!Util.checkLong(Util.parselong(BeanUtils.getProperty(obj, "modifyUserIdF")))) {
			BeanUtils.setProperty(obj, "modifyUserIdF", getUser().getId());
		}
		if (!Util.checkLong(Util.parselong(BeanUtils.getProperty(obj, "idF")))) {
			BeanUtils.setProperty(obj, "isValidF", IsValid.YES);
			if (!Util.checkLong(Util.parselong(BeanUtils.getProperty(obj, "createUserIdF")))) {
				BeanUtils.setProperty(obj, "createUserIdF", getUser().getId());
			}
		}
	}
}

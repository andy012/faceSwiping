package com.face.util.user;

import java.util.ArrayList;
import java.util.List;

public class RoleUtil {
	public static final Long ROLE_ADMIN = 1L;
	public static final Long ROLE_USER = 2L;
	public static final Long ROLE_SP_MANAGER = 3L;
	public static final Long ROLE_SP_EMPLOYEE=4L;

	public static final List<Long> roles() {
		List<Long> roles = new ArrayList<Long>();
		roles.add(ROLE_USER);
		roles.add(ROLE_ADMIN);
		roles.add(ROLE_SP_MANAGER);
		roles.add(ROLE_SP_EMPLOYEE);
		return roles;
	}
}

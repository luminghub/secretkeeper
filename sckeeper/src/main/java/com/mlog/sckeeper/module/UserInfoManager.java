package com.mlog.sckeeper.module;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

import com.mlog.uums.bean.User;
import com.mlog.uums.client.module.LoginUserManage;

/**
 * 获取当前登录用户信息类
 * 
 * @author lm
 *
 */
public class UserInfoManager {

  /**
   * 获取登录用户的详细信息
   * 
   * @param request
   * @return User对象
   */
  public static User getUserInfo(HttpServletRequest request) {
    return LoginUserManage.getUser(getUserName(request));
  }

  /**
   * 获取登录用户的用户名
   * 
   * @param request
   * @return
   */
  public static String getUserName(HttpServletRequest request) {
    return ((Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION))
        .getPrincipal().getName();
  }
}

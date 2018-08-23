package com.mlog.sckeeper.initialize;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.ServletContextAware;

import com.mlog.uums.client.config.ConfigInitManager;

/**
 * 初始化用户管理系统用户权限信息类
 * 
 * @author lm
 *
 */
@Repository
public class PermissionInitializer implements InitializingBean, ServletContextAware {
  private static Logger logger = LogManager.getLogger(PermissionInitializer.class.getName());

  @Override
  public void setServletContext(ServletContext servletContext) {

  }

  /**
   * 初始化用户管理系统用户权限信息
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    ConfigInitManager config = new ConfigInitManager();
    config.configInit();
    logger.info("成功初始化完成：密码守护者项目用户管理配置信息。");
  }

}

package com.mlog.sckeeper.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mlog.sckeeper.entity.Secret;

/**
 * @author lm
 *
 */
@Repository("secretMapper")
public interface SecretMapper {
  /**
   * 增加密钥
   * 
   * @param secret
   */
  public void addSecret(@Param("secret") Secret secret);

  /**
   * 获取密钥
   * 
   * @param userName
   */
  public Secret getSecret(@Param("userName") String userName);

  /**
   * 更新
   * 
   * @param secret
   */
  public void updateSecret(@Param("secret") Secret secret);
}

package com.mlog.sckeeper.dao;

import com.mlog.sckeeper.entity.Secret;

/**
 * @author lm
 *
 */
public interface ISecretDao {

  /**
   * 增加密钥
   * 
   * @param secret
   */
  public void addSecret(Secret secret);

  /**
   * 获取密钥
   * 
   * @param userName
   * @param id
   */
  public Secret getSecret(String userName);
  
  /**
   * 更新
   * @param secret
   */
  public void updateSecret(Secret secret);

}

package com.mlog.sckeeper.entity;

/**
 * @author lm
 *
 */
public class Secret {
  /** 用户名 */
  private String userName;
  /** 密钥 */
  private String secretKey;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

}

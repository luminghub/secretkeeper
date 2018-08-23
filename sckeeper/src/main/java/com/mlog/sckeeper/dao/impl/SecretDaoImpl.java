package com.mlog.sckeeper.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mlog.sckeeper.dao.ISecretDao;
import com.mlog.sckeeper.entity.Secret;
import com.mlog.sckeeper.mapper.SecretMapper;

/**
 * @author lm
 *
 */
@Repository("secretDao")
public class SecretDaoImpl implements ISecretDao {
  @Autowired
  private SecretMapper secretMapper;

  @Override
  public void addSecret(Secret secret) {
    secretMapper.addSecret(secret);
  }

  @Override
  public Secret getSecret(String userName) {
    return secretMapper.getSecret(userName);
  }

  @Override
  public void updateSecret(Secret secret) {
    secretMapper.updateSecret(secret);
  }

}

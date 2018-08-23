package com.mlog.sckeeper.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mlog.sckeeper.dao.IKeeperDao;
import com.mlog.sckeeper.dao.ISecretDao;
import com.mlog.sckeeper.entity.Keeper;
import com.mlog.sckeeper.entity.Secret;
import com.mlog.sckeeper.service.ISckeeperService;

/**
 * @author lm
 *
 */
@Service("sckeeperService")
public class SckeeperServiceImpl implements ISckeeperService {
  @Autowired
  private IKeeperDao keeperDao;
  @Autowired
  private ISecretDao secretDao;

  @Override
  public Secret getUserSecret(String userName) {
    return secretDao.getSecret(userName);
  }

  @Override
  public List<Keeper> getKeepers(String userName) {
    List<Keeper> keepers = keeperDao.getKeepers(userName);
    if (keepers == null) {
      keepers = new ArrayList<Keeper>();
    }
    return keepers;
  }

  @Transactional
  @Override
  public void addSecret(Secret secret) {
    secretDao.addSecret(secret);
  }

  @Transactional
  @Override
  public void addKeeper(Keeper keeper) {
    keeperDao.addKeeper(keeper);
  }

  @Transactional
  @Override
  public void updateKeeper(Keeper keeper) {
    Keeper baseKeeper = keeperDao.getKeeper(keeper.getUserName(), keeper.getId());
    baseKeeper.setKey(keeper.getKey());
    baseKeeper.setValue(keeper.getValue());
    baseKeeper.setNote(keeper.getNote());
    keeperDao.updateKeeper(baseKeeper);
  }

  @Transactional
  @Override
  public void updateSecret(Secret secret) {
    Secret baseSecret = secretDao.getSecret(secret.getUserName());
    baseSecret.setSecretKey(secret.getSecretKey());
    secretDao.updateSecret(baseSecret);
  }

  @Transactional
  @Override
  public void deleteKeeper(String userName, int keeperId) {
    keeperDao.deleteKeeper(userName, keeperId);
  }

}

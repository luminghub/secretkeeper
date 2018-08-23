package com.mlog.sckeeper.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mlog.sckeeper.dao.IKeeperDao;
import com.mlog.sckeeper.entity.Keeper;
import com.mlog.sckeeper.mapper.KeeperMapper;

/**
 * @author lm
 *
 */
@Repository("keeperDao")
public class KeeperDaoImpl implements IKeeperDao {
  @Autowired
  private KeeperMapper keeperMapper;

  @Override
  public List<Keeper> getKeepers(String userName) {
    return keeperMapper.getKeepers(userName);
  }

  @Override
  public void updateKeeper(Keeper keeper) {
    keeperMapper.updateKeeper(keeper);
  }

  @Override
  public void deleteKeeper(String userName, int keeperId) {
    keeperMapper.deleteKeeper(userName, keeperId);
  }

  @Override
  public void addKeeper(Keeper keeper) {
    keeperMapper.addKeeper(keeper);
  }

  @Override
  public Keeper getKeeper(String userName, int keeperId) {
    return keeperMapper.getKeeper(userName, keeperId);
  }

}

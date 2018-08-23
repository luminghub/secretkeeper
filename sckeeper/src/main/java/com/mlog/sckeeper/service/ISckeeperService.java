package com.mlog.sckeeper.service;

import java.util.List;

import com.mlog.sckeeper.entity.Keeper;
import com.mlog.sckeeper.entity.Secret;

/**
 * @author lm
 *
 */
public interface ISckeeperService {

  public Secret getUserSecret(String userName);

  public List<Keeper> getKeepers(String userName);

  public void addSecret(Secret secret);

  public void addKeeper(Keeper keeper);

  public void updateKeeper(Keeper keeper);

  public void updateSecret(Secret secret);

  public void deleteKeeper(String userName, int keeperId);
}

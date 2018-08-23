package com.mlog.sckeeper.dao;

import java.util.List;

import com.mlog.sckeeper.entity.Keeper;

/**
 * @author lm
 *
 */
public interface IKeeperDao {

  /**
   * 获取全部Keeper
   * 
   * @param userName
   */
  public List<Keeper> getKeepers(String userName);

  /**
   * 根据keeperId获取Keeper
   * 
   * @param userName
   * @param keeperId
   * @return
   */
  public Keeper getKeeper(String userName, int keeperId);

  /**
   * 更新密码
   * 
   * @param userName
   * @param keeper
   */
  public void updateKeeper(Keeper keeper);

  /**
   * 删除密码
   * 
   * @param userName
   * @param keeperId
   */
  public void deleteKeeper(String userName, int keeperId);

  /**
   * 新增Keeper
   * 
   * @param userName
   * @param keeper
   */
  public void addKeeper(Keeper keeper);

}

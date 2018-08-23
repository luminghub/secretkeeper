package com.mlog.sckeeper.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mlog.sckeeper.entity.Keeper;

/**
 * @author lm
 *
 */
@Repository("keeperMapper")
public interface KeeperMapper {

  /**
   * 获取全部Keeper
   * 
   * @param userName
   */
  public List<Keeper> getKeepers(@Param("userName") String userName);

  /**
   * 根据keeperId获取Keeper
   * 
   * @param userName
   * @param keeperId
   * @return
   */
  public Keeper getKeeper(@Param("userName") String userName, @Param("keeperId") int keeperId);

  /**
   * 更新密码
   * 
   * @param userName
   * @param keeper
   */
  public void updateKeeper(@Param("keeper") Keeper keeper);

  /**
   * 删除密码
   * 
   * @param userName
   * @param keeperId
   */
  public void deleteKeeper(@Param("userName") String userName, @Param("keeperId") int keeperId);

  /**
   * 新增Keeper
   * 
   * @param userName
   * @param keeper
   */
  public void addKeeper(@Param("keeper") Keeper keeper);


}

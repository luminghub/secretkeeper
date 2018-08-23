package com.mlog.sckeeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mlog.web.ErrorType;
import com.mlog.web.ServerException;

/**
 * @author lm
 *
 */
@Controller
public class PermissionController extends BaseController {

  @RequestMapping(value = "/lockInvalid")
  @ResponseBody
  public void lockInvalid() throws ServerException {
    throw new ServerException(ErrorType.ParamError, "0x01", "请先解锁！");
  }
}

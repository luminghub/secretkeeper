package com.mlog.sckeeper.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mlog.web.ServerExceptionHandle;

@Controller
public class BaseController extends ServerExceptionHandle {
  @Autowired
  private HttpServletResponse response;
  private static Logger logger = LogManager.getLogger(BaseController.class.getName());

  @ExceptionHandler
  @ResponseBody
  public Map<String, Object> exceptionHandle(Exception ex) {
    Map<String, Object> map = super.exceptionHandle(ex, response);
    logger.error(map.get(ERROR_CODE).toString() + ":" + map.get(ERROR_INFO));
    return map;
  }

}

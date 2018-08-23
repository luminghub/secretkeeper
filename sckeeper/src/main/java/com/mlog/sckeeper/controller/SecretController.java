package com.mlog.sckeeper.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mlog.sckeeper.bean.ResultBean;
import com.mlog.sckeeper.common.SeckPub;
import com.mlog.sckeeper.common.Secreter;
import com.mlog.sckeeper.entity.Keeper;
import com.mlog.sckeeper.entity.Secret;
import com.mlog.sckeeper.module.UserInfoManager;
import com.mlog.sckeeper.service.ISckeeperService;
import com.mlog.uums.bean.User;
import com.mlog.web.ErrorType;
import com.mlog.web.ServerException;

@Controller
public class SecretController extends BaseController {
  @Autowired
  private ISckeeperService sckeeperService;
  @Autowired
  private HttpServletRequest request;


  /**
   * 跳转到首页
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = {"/", "/index"})
  public ModelAndView mainPage() {
    User user = UserInfoManager.getUserInfo(request);
    HttpSession session = request.getSession();
    Secret secret = (Secret) session.getAttribute(SeckPub.SECRET);
    if (secret == null) {
      secret = sckeeperService.getUserSecret(user.getUserName());
      if (secret == null) {
        session.setAttribute(SeckPub.LOCK, "N");
        session.setAttribute(SeckPub.SECRET_FLAG, false);
      } else {
        session.setAttribute(SeckPub.SECRET_FLAG, true);
        session.setAttribute(SeckPub.SECRET, secret);
        session.setAttribute(SeckPub.LOCK, true);
      }
    }
    List<Keeper> keeperList = (List<Keeper>) session.getAttribute(SeckPub.KEEPER);
    if (keeperList == null) {
      keeperList = sckeeperService.getKeepers(user.getUserName());
      session.setAttribute(SeckPub.KEEPER, keeperList);
    }
    session.setAttribute(SeckPub.USER, user);
    // 校验数据的正确性：是否为空等
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("user", user);
    data.put("keepers", keeperList);
    return new ModelAndView("index", data);
  }

  @RequestMapping(value = "/keeper/delete")
  @ResponseBody
  public ResultBean deleteKeeper(
      @RequestParam(value = "keeperId", required = true) Integer keeperId) {
    ResultBean resultBean = new ResultBean();
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute(SeckPub.USER);
    String userName = user.getUserName();
    sckeeperService.deleteKeeper(userName, keeperId);
    List<Keeper> keeperList = (List<Keeper>) session.getAttribute(SeckPub.KEEPER);
    for (int i = 0; i < keeperList.size(); i++) {
      Keeper keeper = keeperList.get(i);
      if (keeper.getId() == keeperId) {
        keeperList.remove(i);
        break;
      }
    }
    session.setAttribute(SeckPub.KEEPER, keeperList);
    resultBean.setReqMsg("删除成功！");
    return resultBean;
  }

  @RequestMapping(value = "/keeper/show")
  @ResponseBody
  public ResultBean showSecret(@RequestParam(value = "keeperId", required = true) String keeperId)
      throws Exception {
    HttpSession session = request.getSession();
    ResultBean resultBean = new ResultBean();
    List<Keeper> keeperList = (List<Keeper>) session.getAttribute(SeckPub.KEEPER);
    resultBean.setReqMsg("error for your operate!");
    for (Keeper keeper : keeperList) {
      if (keeper.getId() == Integer.parseInt(keeperId)) {
        Secret secret = (Secret) session.getAttribute(SeckPub.SECRET);
        resultBean.setReqMsg(Secreter.aesDecrypt(keeper.getValue(), secret.getSecretKey()));
      }
    }
    return resultBean;
  }

  /**
   * 解锁密码
   * 
   * @param request
   * @param secretKey
   * @return
   * @throws ServerException
   */
  @RequestMapping(value = "/unlock")
  @ResponseBody
  public ResultBean unlock(@RequestParam(value = "secretKey", required = true) String secretKey)
      throws ServerException {
    HttpSession session = request.getSession();
    Secret secret = (Secret) session.getAttribute(SeckPub.SECRET);
    ResultBean resultBean = new ResultBean();
    if (secretKey.equals(secret.getSecretKey())) {
      resultBean.setReqMsg("解锁成功！");
      session.setAttribute(SeckPub.LOCK, false);
    } else {
      throw new ServerException(ErrorType.ParamError, "0x02", "密钥不匹配！");
    }
    return resultBean;
  }

  /**
   * 加密密码
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/lock")
  @ResponseBody
  public ResultBean unlock() {
    HttpSession session = request.getSession();
    session.setAttribute(SeckPub.LOCK, true);
    ResultBean resultBean = new ResultBean();
    resultBean.setReqMsg("T");
    return resultBean;
  }

  /**
   * 新增密钥
   * 
   * @param request
   * @param secretKey
   * @throws IOException
   * @throws ServerException
   */
  @RequestMapping(value = "/secret/add")
  @ResponseBody
  public ResultBean setSeckreet(HttpServletResponse response,
      @RequestParam(value = "secretKey", required = true) String secretKey)
      throws IOException, ServerException {
    HttpSession session = request.getSession();
    Secret secret = (Secret) session.getAttribute(SeckPub.SECRET);
    if (secret != null) {
      throw new ServerException(ErrorType.ParamError, "0x03", "密钥已存在！");
    }
    ResultBean resultBean = new ResultBean();
    User user = (User) session.getAttribute(SeckPub.USER);
    String userName = user.getUserName();
    secret = new Secret();
    secret.setSecretKey(secretKey);
    secret.setUserName(userName);
    sckeeperService.addSecret(secret);
    resultBean.setReqMsg("新增密钥成功！");
    return resultBean;
  }

  /**
   * 新增密码
   * 
   * @param request
   * @param keeperKey
   * @param note
   * @param keeperValue
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/keeper/add")
  @ResponseBody
  public ResultBean addKeeper(HttpServletResponse response,
      @RequestParam(value = "keeperKey", required = true) String keeperKey,
      @RequestParam(value = "note", required = true) String note,
      @RequestParam(value = "keeperValue", required = true) String keeperValue) throws Exception {
    HttpSession session = request.getSession();
    Secret secret = (Secret) session.getAttribute(SeckPub.SECRET);
    User user = (User) session.getAttribute(SeckPub.USER);
    String userName = user.getUserName();
    Keeper keeper = new Keeper();
    keeper.setKey(keeperKey);
    keeper.setNote(note);
    keeper.setUserName(userName);
    keeper.setValue(Secreter.aesEncrypt(keeperValue, secret.getSecretKey()));
    sckeeperService.addKeeper(keeper);
    ResultBean resultBean = new ResultBean();
    resultBean.setReqMsg("新增密码条目成功！");
    List<Keeper> keeperList = sckeeperService.getKeepers(user.getUserName());
    session.setAttribute(SeckPub.KEEPER, keeperList);
    return resultBean;
  }

  /**
   * 修改密码
   * 
   * @param request
   * @param keeperKey
   * @param note
   * @param keeperValue
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/keeper/update")
  @ResponseBody
  public ResultBean updateKeeper(
      @RequestParam(value = "keeperId", required = true) Integer keeperId,
      @RequestParam(value = "keeperKey", required = true) String keeperKey,
      @RequestParam(value = "note", required = true) String note,
      @RequestParam(value = "keeperValue", required = true) String keeperValue) throws Exception {
    HttpSession session = request.getSession();
    Secret secret = (Secret) session.getAttribute(SeckPub.SECRET);
    User user = (User) session.getAttribute(SeckPub.USER);
    String userName = user.getUserName();
    Keeper keeper = new Keeper();
    keeper.setId(keeperId);
    keeper.setKey(keeperKey);
    keeper.setNote(note);
    keeper.setUserName(userName);
    keeper.setValue(Secreter.aesEncrypt(keeperValue, secret.getSecretKey()));
    sckeeperService.updateKeeper(keeper);
    ResultBean resultBean = new ResultBean();
    resultBean.setReqMsg("修改密码条目成功！");
    List<Keeper> keeperList = sckeeperService.getKeepers(user.getUserName());
    session.setAttribute(SeckPub.KEEPER, keeperList);
    return resultBean;
  }

  /**
   * 退出登录
   * 
   * @param request
   * @param response
   */
  @RequestMapping(value = "/logOut")
  public void logOut(HttpServletResponse response) {
    HttpSession session = request.getSession();
    session.invalidate();
    try {
      response.sendRedirect(
          "https://cas.mlogcn.com:8883/cas/logout?service=http://123.57.41.182:8080/sckeeper/index?timestamp="
              + new Date().getTime());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

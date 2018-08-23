package com.mlog.sckeeper.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Producer;
import com.mlog.sckeeper.common.SeckPub;
import com.mlog.sckeeper.common.Secreter;
import com.mlog.sckeeper.entity.Keeper;
import com.mlog.sckeeper.entity.Secret;

/**
 * 验证码Controller
 * 
 * @author lm
 *
 */
@Controller
public class ImgCodeController {
  @Autowired
  private Producer captchaProducer;
  @Autowired
  private HttpServletRequest request;
  private static final String LOCK = "lockFlag";

  @RequestMapping(value = "/img/Code")
  public void showImgCode(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String code = "this is daddy's code";
    response.setDateHeader("Expires", 0);
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    response.setHeader("Pragma", "no-cache");
    response.setContentType("image/jpeg");
    BufferedImage bi = captchaProducer.createImage(code);
    ServletOutputStream out = response.getOutputStream();
    ImageIO.write(bi, "jpg", out);
    try {
      out.flush();
    } finally {
      out.close();
    }
  }

  @RequestMapping(value = "/img/keeper/show")
  @ResponseBody
  public void showSecret(HttpServletResponse response,
      @RequestParam(value = "keeperId", required = true) String keeperId) throws Exception {
    HttpSession session = request.getSession();
    boolean unlockFlag = (boolean) session.getAttribute(LOCK);
    String code = "请先解锁密钥！";
    if (!unlockFlag) {
      List<Keeper> keeperList = (List<Keeper>) session.getAttribute(SeckPub.KEEPER);
      for (Keeper keeper : keeperList) {
        if (keeper.getId() == Integer.parseInt(keeperId)) {
          Secret secret = (Secret) session.getAttribute(SeckPub.SECRET);
          code = Secreter.aesDecrypt(keeper.getValue(), secret.getSecretKey());
        }
      }
    }
    response.setDateHeader("Expires", 0);
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    response.setHeader("Pragma", "no-cache");
    response.setContentType("image/jpeg");
    BufferedImage bi = captchaProducer.createImage(code);
    ServletOutputStream out = response.getOutputStream();
    ImageIO.write(bi, "jpg", out);
    try {
      out.flush();
    } finally {
      out.close();
    }
  }


  @RequestMapping(value = "/img/Code1/{v1}/{v2}")
  public void showImgCode2(HttpServletRequest request, HttpServletResponse response,
      @PathVariable String v1, @PathVariable String v2) throws IOException {
    String code = new String("请先解锁密钥！".getBytes(v1), v2);
    response.setDateHeader("Expires", 0);
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    response.setHeader("Pragma", "no-cache");
    response.setContentType("image/jpeg");
    BufferedImage bi = captchaProducer.createImage(code);
    ServletOutputStream out = response.getOutputStream();
    ImageIO.write(bi, "jpg", out);
    try {
      out.flush();
    } finally {
      out.close();
    }
  }

}

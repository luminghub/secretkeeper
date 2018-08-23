package com.mlog.sckeeper.common;

import org.junit.Test;

/**
 * @author lm
 *
 */
public class SecreterTest {

  @Test
  public void test() throws Exception {


    String content = "123";
    System.out.println("加密前：" + content);

    String key = "";
    System.out.println("密钥：" + key);

    String encrypt = Secreter.aesEncrypt(content, key);
    System.out.println("加密后：" + encrypt);

    String decrypt = Secreter.aesDecrypt("FZwWZV08E11C+QHuwWzhUw==", key);
    System.out.println("解密后：" + decrypt);
    System.out.println("加密前：" + decrypt);
     encrypt = Secreter.aesEncrypt(decrypt, "890913");
    System.out.println("加密后：" + encrypt);
  }

}

package com.lr.shirodemo;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;

/**
 * <p>
 *
 * </p>
 *
 * @author KR
 * @since 2020/05/06 11:09
 */
public class EncryptionTest {
    @Test
    public void md() {
        String algorithmName = "SHA1";
        String credential = "123456";
        String salt = "admin";
        Integer hashIterations = 1024;
        SimpleHash password = new SimpleHash(algorithmName, credential, salt,hashIterations);
        System.out.println(password);
    }
}

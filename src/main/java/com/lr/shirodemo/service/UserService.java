package com.lr.shirodemo.service;

import com.lr.shirodemo.entity.User;

/**
 * <p>
 *
 * </p>
 *
 * @author KR
 * @since 2020/05/05 12:27
 */
public interface UserService {
    User findByName(String name);

    User findById(Integer id);

    String getSessionValue(String key);
}

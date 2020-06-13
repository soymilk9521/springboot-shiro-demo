package com.lr.shirodemo.service.impl;

import com.lr.shirodemo.entity.User;
import com.lr.shirodemo.mapper.UserMapper;
import com.lr.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;

/**
 * <p>
 *
 * </p>
 *
 * @author KR
 * @since 2020/05/05 12:28
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public String getSessionValue(String key) {
        Session session = SecurityUtils.getSubject().getSession();
        return (String) session.getAttribute(key);
    }

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
}

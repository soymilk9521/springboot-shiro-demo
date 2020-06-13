package com.lr.shirodemo.mapper;

import com.lr.shirodemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *
 * </p>
 *
 * @author KR
 * @since 2020/05/05 12:18
 */
@Mapper
public interface UserMapper {
    public User findByName(String name);

    public User findById(Integer id);
}

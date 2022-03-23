package com.group13.service;

import com.group13.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.group13.entity.vo.UserQueryVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author group13
 * @since 2022-03-10
 */
public interface UserService extends IService<User> {

    /**
     * pageByVo
     * @param current
     * @param limit
     * @param userQueryVo
     * @return
     */
    Map<String, Object> pageByVo(long current, long limit, UserQueryVo userQueryVo);
}

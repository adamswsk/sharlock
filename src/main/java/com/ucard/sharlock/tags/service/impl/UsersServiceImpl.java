package com.ucard.sharlock.tags.service.impl;

import com.ucard.sharlock.tags.entity.Users;
import com.ucard.sharlock.tags.mapper.UsersMapper;
import com.ucard.sharlock.tags.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wusk
 * @since 2020-03-11
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}

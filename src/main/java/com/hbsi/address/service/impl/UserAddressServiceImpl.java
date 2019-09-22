package com.hbsi.address.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbsi.address.entity.UserAddress;
import com.hbsi.address.mapper.UserAddressMapper;
import com.hbsi.address.service.IUserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-08-16
 */
@Slf4j
@CacheConfig(cacheNames = "userAddress")
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {


    @Resource
    private UserAddressMapper userAddressMapper;


    /**
     * 根据id查询用户地址
     * @param id 用户地址
     * @return address
     */
    @Cacheable
    public UserAddress getAddressById(Integer id){
        try {
            UserAddress address = userAddressMapper.selectOne(new QueryWrapper<UserAddress>().eq("id", id));
            log.debug("根据id查询用户地址成功");
            return address;
        } catch (Exception e) {
            log.debug("根据id查询用户地址失败");
            return null;
        }
    }

}

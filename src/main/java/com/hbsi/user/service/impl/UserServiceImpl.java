package com.hbsi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.user.entity.User;
import com.hbsi.user.mapper.UserMapper;
import com.hbsi.user.service.IUserService;
import com.hbsi.utils.DateUtils;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ljz
 * @since 2019-06-12
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Resource
    private UserMapper userMapper;


    /**
     * 查询全部用户分页
     * @param current 当前页
     * @param size 每页数量
     * @return 分页对象
     */
    private  IPage<User> getUsersByPage(Integer current, Integer size){
        try {
            Page<User> page = new Page<>(current, size);
            IPage<User> iPage = userMapper.selectPage(page, new QueryWrapper<>());
            log.debug("分页查询用户成功");
            return  iPage;
        } catch (Exception e) {
            log.error("分页查询用户失败");
            throw  new BaseException(ExceptionEnum.USER_GET_FILED,"查询全部用户分页失败");
        }
    }


    /**
     * 分页公共方法
     * @param current 当前页
     * @param size 每页数量
     * @param model model andview
     * @return 页面
     */
    public String getUsers(Integer current,Integer size,Model model){
        IPage<User> page = this.getUsersByPage(current, size);
        if(page.getRecords() == null){
            model.addAttribute("msgFiled","当前暂无数据");
        }else{
            model.addAttribute("pages",page);
        }
        return "user/list.html";
    }




    /**
     * 根据用户名称查询用户
     * @param userName 用户名
     * @return 用户对象
     */
    @Caching(cacheable = {@Cacheable(cacheNames = "user")},put = {@CachePut(cacheNames = "user")})
    public User getUserByName(String userName){
        try {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("userName", userName));
            log.debug("根据用户名查询用户成功");
            return user;
        } catch (Exception e) {
            log.error("根据用户名查询用户失败");
            throw  new BaseException(ExceptionEnum.USER_GET_FILED,"根据用户名查询用户失败");
        }
    }


    /**
     * 根据用户id查询用户
     * @param id 用户id
     * @return 用户对象
     */
    @Caching(cacheable = {@Cacheable(cacheNames = "user")},put = {@CachePut(cacheNames = "user")})
    public User getUserById(Integer id){
        try {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", id));
            if (user == null){
                log.debug("查询的用户不存在");
                throw new BaseException(ExceptionEnum.USER_GET_FILED,"根据用户id查询用户失败");
            }
            return user;
        } catch (Exception e) {
            log.debug(e.getMessage(),"根据用户id查询用户");
            throw new BaseException(ExceptionEnum.USER_GET_FILED,"根据用户id查询用户失败");
        }
    }


    /**
     * 根据用户id修改用户
     * @param user 用户对象
     * @return boolean
     */
    @Transactional
    @CachePut
    public boolean updateUser(User user){
        try {
            User u = new User();
            u.setCreateTime(user.getCreateTime());
            u.setEmail(user.getEmail());
            u.setPhone(user.getPhone());
            u.setEmail(user.getEmail());
            u.setRoleName(user.getRoleName());
            u.setUserName(user.getUserName());
            int flag = userMapper.update(u, new UpdateWrapper<User>().eq("id", user.getId()));
            if (flag >0){
                log.debug("修改成功");
                return true;
            }else {
                log.debug("修改失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"修改异常");
            throw new BaseException(ExceptionEnum.USER_UPDATE_FILED);
        }
    }


    /**
     * 保存用户
     * @param user 用户对象
     * @param model model and view
     * @return boolean
     */
    public boolean saveUser(User user, Model model){
        try {
            User u =  userMapper.selectOne(new QueryWrapper<User>().eq("userName",user.getUserName()));
            if (u != null){
                log.debug("该用户名已被占用");
                model.addAttribute("msgFiled","该用户名已被占用");
                return false;
            }
            user.setCreateTime(DateUtils.formatDate(new Date()));
            int flag = userMapper.insert(user);
            if (flag > 0){
                log.debug("保存用户成功");
                return true;
            }else{
                log.debug("保存用户失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"保存用户异常");
            throw new BaseException(ExceptionEnum.USER_SAVE_FILED);
        }
    }


    /**
     * 根据id删除用户
     * @param id 用户id
     * @return boolean
     */
    @Transactional
    @CacheEvict
    public boolean deleteUserById(Integer id){
        try {
            int flag = userMapper.delete(new UpdateWrapper<User>().eq("id", id));
            if (flag > 0){
                log.debug("删除用户成功");
                return true;
            }else{
                log.debug("删除用户失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"删除用户异常");
            throw new BaseException(ExceptionEnum.USER_DELETE_FILED);
        }
    }




}

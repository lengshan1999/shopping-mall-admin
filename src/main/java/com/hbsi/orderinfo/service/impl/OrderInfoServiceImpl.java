package com.hbsi.orderinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.orderinfo.entity.OrderInfo;
import com.hbsi.orderinfo.mapper.OrderInfoMapper;
import com.hbsi.orderinfo.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-08-16
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "orderInfo")
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {


    @Resource
    private OrderInfoMapper orderInfoMapper;



    /**
     * 分页查询订单信息
     * @param current 当前页
     * @param size 每页数量
     * @return 分页对象
     */
    @Caching(cacheable = {@Cacheable(cacheNames = "orderInfo")},put = {@CachePut(cacheNames = "orderInfo")})
    public IPage<OrderInfo> getAllOrdersByPage(Integer current,Integer size){
        try {
            Page<OrderInfo> page = new Page<>(current, size);
            IPage<OrderInfo> iPage = orderInfoMapper.selectPage(page, new QueryWrapper<>());
            log.debug("分页查询订单信息成功");
            return iPage;
        } catch (Exception e) {
            log.debug(e.getMessage(),"分页查询订单信息失败");
            throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED,"分页查询订单信息失败");
        }
    }



    /**
     * 根据id查询订单信息
     * @param id 订单信息id
     * @return orderInfo
     */
    @Caching(cacheable = {@Cacheable(cacheNames = "orderInfo")},put = {@CachePut(cacheNames = "orderInfo")})
    public OrderInfo getOrderInfoById(Integer id){
        try {
            OrderInfo orderInfo = orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>().eq("id", id));
            log.debug("根据id查询订单信息成功");
            return orderInfo;
        } catch (Exception e) {
            log.debug("根据id查询订单信息成失败");
            throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED,"根据id查询订单信息失败");
        }
    }


    /**
     * 根据订单编号查询订单信息
     * @param orderNum 订单编号
     * @return orderInfo
     */
    @Transactional
    @Caching(cacheable = {@Cacheable(cacheNames = "orderInfo")},put = {@CachePut(cacheNames = "orderInfo")})
    public  OrderInfo getOrderInfoByOrderNum(String orderNum){
        try {
            OrderInfo orderInfo = orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>().eq("orderNum", orderNum));
            log.debug("根据订单编号查询订单信息成功");
            return orderInfo;
        } catch (Exception e) {
            log.debug(e.getMessage(),"根据订单编号查询订单信息失败");
            throw new BaseException(ExceptionEnum.ORDER_INFO_GET_FILED,"根据订单编号查询订单信息失败");
        }
    }


    /**
     * 根据订单状态查询订单信息
     * @param status 订单状态
     * @return orderInfo
     */
    @Caching(cacheable = {@Cacheable(cacheNames = "orderInfo")},put = {@CachePut(cacheNames = "orderInfo")})
    public List<OrderInfo> getOrderInfoByStatus(Integer status){
        try {
            List<OrderInfo> orders = orderInfoMapper.selectList(new QueryWrapper<OrderInfo>().eq("status", status));
            log.debug("根据状态查询订单信息成功");
            return orders;
        } catch (Exception e) {
            log.debug("根据订单编号查询订单信息失败");
            throw new  BaseException(ExceptionEnum.ORDER_INFO_GET_FILED,"根据订单编号查询订单信息失败");
        }

    }


    /**
     * 订单发货
     * @param id 订单id
     * @return boolean
     */
    @Transactional
    @CachePut
    public boolean sendGoods(Integer id){
        try {
            int flag = orderInfoMapper.update(new OrderInfo().setOrderInfoStatus(0), new UpdateWrapper<OrderInfo>().eq("id", id));
            if (flag>0){
                log.debug("发货成功");
                return true;
            }else {
                log.debug("发货失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"发货异常");
            throw new BaseException(ExceptionEnum.ORDER_INFO_UPDATE_FILED,"发货异常");
        }
    }




}

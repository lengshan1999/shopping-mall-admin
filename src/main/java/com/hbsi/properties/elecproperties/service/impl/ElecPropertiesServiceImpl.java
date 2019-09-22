package com.hbsi.properties.elecproperties.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.productType.entity.ProductType;
import com.hbsi.productType.mapper.ProductTypeMapper;
import com.hbsi.properties.elecproperties.entity.ElecProperties;
import com.hbsi.properties.elecproperties.mapper.ElecPropertiesMapper;
import com.hbsi.properties.elecproperties.service.IElecPropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-06-30
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "electronProperties")
public class ElecPropertiesServiceImpl extends ServiceImpl<ElecPropertiesMapper, ElecProperties> implements IElecPropertiesService {


    @Resource
    private ElecPropertiesMapper ElecPropertiesMapper;

    @Resource
    private ProductTypeMapper productTypeMapper;


    /**
     * 分页查询电子数码类属性
     * @param current 当前页
     * @param size 每页数量
     * @return 分页对象
     */
    private IPage<ElecProperties> getElecPropertiesByPage(Integer current, Integer size){
        try {
            Page<ElecProperties> page = new Page<>(current, size);
            IPage<ElecProperties> iPage = ElecPropertiesMapper.selectPage(page, new QueryWrapper<>());
            log.debug("电子数码类属性分页查询成功");
            return iPage;
        } catch (Exception e) {
            log.debug(e.getMessage(),"电子数码类属性分页查询失败");
            throw new BaseException(ExceptionEnum.PROPERTIES_ELECTRON_GET_FILED,"分页查询电子数码类属性失败");
        }
    }


    /**
     * 查询全部电子数码类信息 公共方法
     * @param current 当前页
     * @param size  每页数量
     * @param model model  and view
     * @return html
     */
    public String getAllElecs(Integer current, Integer size, Model model){
        IPage<ElecProperties> iPage = getElecPropertiesByPage(current, size);
        model.addAttribute("pages",iPage);
        return "propertiesElec/list.html";
    }


    /**
     * 根据id查询电子数码类信息
     * @param id 电子数码类信息id
     * @return 电子数码类对象
     */
    @Cacheable
    public ElecProperties getElecPropertiesById(Integer id){
        try {
            ElecProperties ElecProperties = ElecPropertiesMapper.selectOne(new QueryWrapper<ElecProperties>().eq("id", id));
            log.debug("根据id查询电子数码类信息成功");
            return ElecProperties;
        } catch (Exception e) {
            log.debug(e.getMessage(),"根据id查询电子数码类信息失败");
            throw new BaseException(ExceptionEnum.PROPERTIES_ELECTRON_GET_FILED,"根据id查询电子数码类信息失败");
        }
    }


    /**
     * 保存电子数码类信息
     * @param ElecProperties 电子数码类对象
     * @return boolean
     */
    public boolean saveElecProperties(ElecProperties ElecProperties){

        try {
            ElecProperties.setProductTypeName("电子数码类");
            ElecProperties.setTypeId(3);
            int flag1 = ElecPropertiesMapper.insert(ElecProperties);
            ProductType type = productTypeMapper.selectOne(new QueryWrapper<ProductType>().eq("id", 3));
            int flag = productTypeMapper.update(new ProductType().setTypePropertiesCount(type.getTypePropertiesCount() + 1), new UpdateWrapper<ProductType>().eq("id", 3));
            if (flag>0  && flag1>0){
                log.debug("保存电子数码类信息成功");
                return true;
            }else{
                log.debug("保存电子数码类信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"保存电子数码类信息异常");
            throw  new  BaseException(ExceptionEnum.PROPERTIES_ELECTRON_SAVE_FILED);
        }

    }


    /**
     * 修改电子数码类信息
     * @param ElecProperties 电子数码类对象
     * @return boolean
     */
    @Transactional
    @CachePut
    public boolean updateElecProperties(ElecProperties ElecProperties){
        try {
            ElecProperties ElecProperties1 = new ElecProperties();
            ElecProperties1.setOptionalValue(ElecProperties.getOptionalValue());
            ElecProperties1.setElecPropertiesName(ElecProperties.getElecPropertiesName());
            ElecProperties1.setProductTypeName("电子数码类");
            ElecProperties1.setTypeId(3);
            int flag = ElecPropertiesMapper.update(ElecProperties1, new UpdateWrapper<ElecProperties>().eq("id", ElecProperties.getId()));
            if (flag > 0){
                log.debug("修改电子数码类信息成功");
                return  true;
            }else{
                log.debug("修改电子数码类信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"修改电子数码类信息异常");
            throw  new BaseException(ExceptionEnum.PROPERTIES_ELECTRON_UPDATE_FILED);
        }
    }

    
    
    /**
     * 删除电子数码类信息
     * @param id 电子数码类id
     * @return boolean
     */
    @Transactional
    @CacheEvict
    public boolean deleteElecPropertiesById(Integer id){
        try {
            int flag = ElecPropertiesMapper.delete(new QueryWrapper<ElecProperties>().eq("id", id));
            ProductType type = productTypeMapper.selectOne(new QueryWrapper<ProductType>().eq("id", 3));
            int flagType = productTypeMapper.update(new ProductType().setTypePropertiesCount(type.getTypePropertiesCount() - 1), new UpdateWrapper<ProductType>().eq("id", 3));
            if (flag > 0 && flagType>0){
                log.debug("删除电子数码类信息成功");
                return true;
            }else{
                log.debug("删除电子数码类信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"删除电子数码类信息异常");
            throw new BaseException(ExceptionEnum.PROPERTIES_ELECTRON_DELETE_FILED);
        }

    }
    
}

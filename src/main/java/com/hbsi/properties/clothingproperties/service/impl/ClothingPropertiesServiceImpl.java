package com.hbsi.properties.clothingproperties.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.productType.entity.ProductType;
import com.hbsi.productType.mapper.ProductTypeMapper;
import com.hbsi.properties.clothingproperties.entity.ClothingProperties;
import com.hbsi.properties.clothingproperties.mapper.ClothingPropertiesMapper;
import com.hbsi.properties.clothingproperties.service.IClothingPropertiesService;
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
@CacheConfig(cacheNames = "clothingProperties")
public class ClothingPropertiesServiceImpl extends ServiceImpl<ClothingPropertiesMapper, ClothingProperties> implements IClothingPropertiesService {


    @Resource
    private ClothingPropertiesMapper ClothingPropertiesMapper;

    @Resource
    private ProductTypeMapper productTypeMapper;


    /**
     * 分页查询服装类属性
     * @param current 当前页
     * @param size 每页数量 
     * @return 分页对象
     */
    private IPage<ClothingProperties> getClothingPropertiesByPage(Integer current, Integer size){
        try {
            Page<ClothingProperties> page = new Page<>(current, size);
            IPage<ClothingProperties> iPage = ClothingPropertiesMapper.selectPage(page, new QueryWrapper<>());
            log.debug("服装类属性分页查询成功");
            return iPage;
        } catch (Exception e) {
            log.debug(e.getMessage(),"服装类属性分页查询失败");
            throw new BaseException(ExceptionEnum.PROPERTIES_CLOTHING_GET_FILED,"分页查询服装类属性失败");
        }
    }


    /**
     * 查询全部服装类信息 公共方法
     * @param current 当前页
     * @param size  每页数量
     * @param model model  and view
     * @return html
     */
    public String getAllClothing(Integer current, Integer size, Model model){
        IPage<ClothingProperties> iPage = getClothingPropertiesByPage(current, size);
        model.addAttribute("pages",iPage);
        return "propertiesClothing/list.html";
    }


    /**
     * 根据id查询服装类信息
     * @param id 服装类信息id
     * @return 服装类对象
     */
    @Cacheable
    public ClothingProperties getClothingPropertiesById(Integer id){
        try {
            ClothingProperties ClothingProperties = ClothingPropertiesMapper.selectOne(new QueryWrapper<ClothingProperties>().eq("id", id));
            log.debug("根据id查询服装类信息成功");
            return ClothingProperties;
        } catch (Exception e) {
            log.debug(e.getMessage(),"根据id查询服装类信息失败");
            throw new BaseException(ExceptionEnum.PROPERTIES_CLOTHING_GET_FILED,"根据id查询服装类信息失败");
        }
    }


    /**
     * 保存服装类信息
     * @param ClothingProperties 服装类对象
     * @return boolean
     */
    public boolean saveClothingProperties(ClothingProperties ClothingProperties){
        try {
            ClothingProperties.setProductTypeName("服装类");
            ClothingProperties.setTypeId(2);
            int flag1 = ClothingPropertiesMapper.insert(ClothingProperties);
            ProductType type = productTypeMapper.selectOne(new QueryWrapper<ProductType>().eq("id", 2));
            int flag = productTypeMapper.update(new ProductType().setTypePropertiesCount(type.getTypePropertiesCount() + 1), new UpdateWrapper<ProductType>().eq("id", 2));
            if (flag>0  && flag1>0){
                log.debug("保存服装类信息成功");
                return true;
            }else{
                log.debug("保存服装类信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"保存服装类信息异常");
            throw  new  BaseException(ExceptionEnum.PROPERTIES_CLOTHING_SAVE_FILED);
        }

    }


    /**
     * 修改服装类信息
     * @param ClothingProperties 服装类对象
     * @return boolean
     */
    @Transactional
    @CachePut
    public boolean updateClothingProperties(ClothingProperties ClothingProperties){
        try {
            ClothingProperties ClothingProperties1 = new ClothingProperties();
            ClothingProperties1.setOptionalValue(ClothingProperties.getOptionalValue());
            ClothingProperties1.setClothingPropertiesName(ClothingProperties.getClothingPropertiesName());
            ClothingProperties1.setProductTypeName("服装类");
            ClothingProperties1.setTypeId(2);
            int flag = ClothingPropertiesMapper.update(ClothingProperties1, new UpdateWrapper<ClothingProperties>().eq("id", ClothingProperties.getId()));
            if (flag > 0){
                log.debug("修改服装类信息成功");
                return  true;
            }else{
                log.debug("修改服装类信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"修改服装类信息异常");
            throw  new BaseException(ExceptionEnum.PROPERTIES_CLOTHING_UPDATE_FILED);
        }
    }


    /**
     * 删除服装类信息
     * @param id 服装类id
     * @return boolean
     */
    @Transactional
    @CacheEvict
    public boolean deleteClothingPropertiesById(Integer id){
        try {
            int flag = ClothingPropertiesMapper.delete(new QueryWrapper<ClothingProperties>().eq("id", id));
            ProductType type = productTypeMapper.selectOne(new QueryWrapper<ProductType>().eq("id", 2));
            int flagType = productTypeMapper.update(new ProductType().setTypePropertiesCount(type.getTypePropertiesCount() - 1), new UpdateWrapper<ProductType>().eq("id", 2));
            if (flag > 0 && flagType>0){
                log.debug("删除服装类信息成功");
                return true;
            }else{
                log.debug("删除服装类信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"删除服装类信息异常");
            throw new BaseException(ExceptionEnum.PROPERTIES_CLOTHING_DELETE_FILED);
        }

    }
    
    
}

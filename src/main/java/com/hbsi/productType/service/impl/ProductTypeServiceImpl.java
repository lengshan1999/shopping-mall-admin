package com.hbsi.productType.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.productType.entity.ProductType;
import com.hbsi.productType.mapper.ProductTypeMapper;
import com.hbsi.productType.service.IProductTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-08-11
 */

@Slf4j
@Service
@CacheConfig(cacheNames = "productType")
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {


    @Resource
    private ProductTypeMapper productTypeMapper;


    /**
     * 不分页查询商品类型
     * @return List<商品类型>
     */
    @Cacheable
    public List<ProductType> getTypes(){
        try {
            List<ProductType> types = productTypeMapper.selectList(new QueryWrapper<>());
            if (types == null){
                log.debug("暂无商品类型");
                return null;
            }
            log.debug("查询商品类型成功");
            return types;
        } catch (Exception e) {
            log.debug(e.getMessage(),"查询商品类型失败");
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_GET_FILED,"不分页查询商品类型失败");
        }
    }

    /**
     *
     * 商品类型分页查询
     * @param current 当前页
     * @param size  每页数量
     * @return 分页对象
     */
    public IPage<ProductType> getProductTypesByPage(Integer current, Integer size){
        try {
            Page<ProductType> page = new Page<>(current, size);
            IPage<ProductType> iPage = productTypeMapper.selectPage(page, new QueryWrapper<>());
            log.debug("商品类型分页查询成功");
            return iPage;
        } catch (Exception e) {
            log.debug(e.getMessage(),"商品类型分页查询失败");
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_GET_FILED,"商品类型分页查询失败");
        }
    }


    /**
     * 查询全部商品类型 公共方法
     * @param current 当前页
     * @param size 每页数量
     * @param model model and view
     * @return html
     */
    public String getTypes(Integer current, Integer size, Model model){
        IPage<ProductType> iPage = getProductTypesByPage(current,size);
        model.addAttribute("pages",iPage);
        return "productType/list.html";
    }


    /**
     * 根据商品id查询商品类型
     * @param id 商品id
     * @return 商品类型对象
     */
    @Cacheable
    public ProductType getProductTypeById(Integer id){
        try {
            ProductType type = productTypeMapper.selectOne(new QueryWrapper<ProductType>().eq("id", id));
            log.debug("根据商品id查询商品类型成功");
            return type;
        } catch (Exception e) {
            log.debug(e.getMessage(),"根据商品id查询商品类型失败");
            throw  new BaseException(ExceptionEnum.PRODUCT_TYPE_GET_FILED,"根据商品id查询商品类型失败");
        }
    }


    /**
     * 保存商品类型信息
     * @param productType 商品类型对象
     * @return boolean
     */
    public boolean saveProductType(ProductType productType){
        try {
            productType.setTypePropertiesCount(0);
            productType.setCreateTime(DateUtils.formatDate(new Date()));
            int flag = productTypeMapper.insert(productType);
            if (flag > 0){
                log.debug("保存商品类型成功");
                return true;
            }else{
                log.debug("保存商品是失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"保存商品异常");
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_SAVE_FILED);
        }
    }


    /**
     *  修改商品类型
     * @param productType 商品类型对象
     * @return boolean
     */
    @Transactional
    @CachePut
    public  boolean updateProductType(ProductType productType){
        try {
            ProductType p = new ProductType();
            p.setTypeName(productType.getTypeName());
            p.setTypePropertiesCount(productType.getTypePropertiesCount());
            int flag =  productTypeMapper.update(p,new UpdateWrapper<ProductType>().eq("id",productType.getId()));
            if (flag > 0){
                log.debug("修改商品类型成功");
                return true;
            }else{
                log.debug("修改商品类型失败");
                return  false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"修改商品类型失败");
            throw  new BaseException(ExceptionEnum.PRODUCT_TYPE_UPDATE_FILED);
        }
    }


    /**
     * 删除商品类型
     * @param id 商品类型id
     * @return boolean
     */
    @CacheEvict
    @Transactional
    public boolean deleteProductById(Integer id){
        try {
            int flag =  productTypeMapper.delete(new UpdateWrapper<ProductType>().eq("id",id));
            if (flag > 0){
                log.debug("删除商品类型成功");
                return true;
            }else{
                log.debug("删除商品类型失败");
                return false;
            }
        } catch (Exception e) {
            log.debug("删除商品类型失败");
            throw new BaseException(ExceptionEnum.PRODUCT_TYPE_DELETE_FILED);
        }

    }




}

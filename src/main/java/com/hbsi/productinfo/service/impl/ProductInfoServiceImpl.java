package com.hbsi.productinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.orderproduct.entity.OrderProduct;
import com.hbsi.orderproduct.mapper.OrderProductMapper;
import com.hbsi.productType.entity.ProductType;
import com.hbsi.productType.mapper.ProductTypeMapper;
import com.hbsi.productinfo.entity.ProductInfo;
import com.hbsi.productinfo.mapper.ProductInfoMapper;
import com.hbsi.productinfo.service.ProductInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.utils.DateUtils;
import com.hbsi.utils.ImageUtils;
import com.hbsi.utils.NumUtils;
import com.hbsi.utils.QRCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

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
@CacheConfig(cacheNames = "productInfo")
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Resource
    private ProductInfoMapper productInfoMapper;

    @Resource
    private ProductTypeMapper productTypeMapper;

    @Resource
    private OrderProductMapper orderProductMapper;


    /**
     * 分页查询商品信息
     *
     * @param current 当前页
     * @param size    每页数量
     * @return 分页对象
     */
    private IPage<ProductInfo> getProductInfoByPage(Integer current, Integer size) {
        try {
            Page<ProductInfo> page = new Page<>(current, size);
            IPage<ProductInfo> iPage = productInfoMapper.selectPage(page, new QueryWrapper<>());
            if (iPage.getTotal() != 0){
                log.debug("分页查询商品信息成功");
            }else{
                log.debug("商品信息为空");
            }
            return iPage;
        } catch (Exception e) {
            log.debug(e.getMessage(), "分页查询商品信息失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "分页查询商品信息失败");
        }
    }


    /**
     * 获取全部商品信息  公共方法
     *
     * @param current 当前页
     * @param size    每页数量
     * @param model   model and view
     * @return HTML
     */
    public String getGoods(Integer current, Integer size, Model model) {
        IPage<ProductInfo> pages = this.getProductInfoByPage(current, size);
        model.addAttribute("pages", pages);
        return "product/list.html";
    }


    /**
     * 根据id查询商品
     *
     * @param id 商品id
     * @return 商品信息对象
     */
    @Caching(cacheable = {@Cacheable(cacheNames = "productInfo")},put = {@CachePut(cacheNames = "productInfo")})
    public ProductInfo getProductById(Integer id) {
        try {
            ProductInfo productInfo = productInfoMapper.selectOne(new QueryWrapper<ProductInfo>().eq("id", id));
            log.debug("根据id查询商品成功");
            return productInfo;
        } catch (Exception e) {
            log.debug(e.getMessage(), "根据id查询商品失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "根据id查询商品失败");
        }
    }


    /**
     * 根据商品名称查询商品
     *
     * @param productName 商品名称
     * @return 商品信息对象
     */
    @Caching(cacheable = {@Cacheable(cacheNames = "productInfo")},put = {@CachePut(cacheNames = "productInfo")})
    public ProductInfo getProductByName(String productName) {
        try {
            ProductInfo productInfo = productInfoMapper.selectOne(new QueryWrapper<ProductInfo>().eq("productName", productName));
            log.debug("根据商品名称查询商品成功");
            return productInfo;
        } catch (Exception e) {
            log.debug(e.getMessage(), "根据商品名称查询商品失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED, "根据商品名称查询商品失败");
        }
    }


    /**
     * 保存商品信息
     * @param file 商品图片
     * @param productInfo 商品对象信息
     * @param model model and view
     * @return  HTML
     */
    public String saveProductInfo(MultipartFile file, ProductInfo productInfo, Model model) {
        try {
            ProductInfo p = productInfoMapper.selectOne(new QueryWrapper<ProductInfo>().eq("productName", productInfo.getProductName()));
            if (p != null) {
                log.debug("已经添加过该商品");
                model.addAttribute("msgFiled", "该商品已存在,请勿重复添加");
                return this.getGoods(1, 4, model);
            }
            ProductType type = productTypeMapper.selectOne(new QueryWrapper<ProductType>().eq("typeName", productInfo.getProductType()));
            productInfo.setProductTypeId(type.getId());
            productInfo.setCreateDate(DateUtils.formatDate(new Date()));
            String productNum = NumUtils.createProductNum();
            productInfo.setProductNum(productNum);
            productInfo.setProductImg(ImageUtils.upLoadFile(file, productNum));
            if (productInfoMapper.insert(productInfo) > 0) {
                ProductInfo product = productInfoMapper.selectOne(new QueryWrapper<ProductInfo>().eq("productNum", productNum));
                Integer productId = product.getId();
                String code = QRCode.createProductCode("https://www.baidu.com", productId + productNum + "");
                int updateFlag = productInfoMapper.update(new ProductInfo().setQRCode(code), new UpdateWrapper<>(new ProductInfo().setId(productId)));
                if (!(updateFlag > 0)) {
                    log.debug("添加二维码失败");
                    throw new BaseException(ExceptionEnum.PRODUCT_INFO_CREATE_CODE_FILED);
                }
                log.debug("添加商品信息成功");
                model.addAttribute("msg", "添加商品信息成功");
                return getGoods(1, 4, model);
            } else {
                log.debug("查询失败");
                model.addAttribute("msgFiled", "暂无该商品信息");
                return getGoods(1, 4, model);
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"添加失败");
            model.addAttribute("msgFiled", "添加商品失败");
            return "redirect:/product/getAllProducts";
        }
    }


    /**
     * 修改商品信息
     * @param productInfo 商品信息对象
     * @return boolean
     */
    @Transactional
    @CachePut
    public boolean updateProduct(ProductInfo productInfo){
        try {
            ProductInfo p = new ProductInfo();
            ProductType type = productTypeMapper.selectOne(new QueryWrapper<ProductType>().eq("typeName", productInfo.getProductType()));
            p.setProductType(type.getTypeName());
            p.setProductTypeId(type.getId());
            p.setProductName(productInfo.getProductName());
            p.setProductPrice(productInfo.getProductPrice());
            p.setDescription(productInfo.getDescription());
            p.setProductInfoStatus(productInfo.getProductInfoStatus());
            int flag = productInfoMapper.update(p, new UpdateWrapper<ProductInfo>().eq("id", productInfo.getId()));
            if (flag>0){
                log.debug("修改商品信息成功");
                return true;
            }else{
                log.debug("修改商品信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"修改商品信息失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_UPDATE_FILED);
        }
    }


    /**
     * 根据商品id删除商品信息
     * @param id 商品id
     * @return boolean
     */
    @Transactional
    @CacheEvict
    public boolean deleteProductInfoById(Integer id){
        try {
            int flag = productInfoMapper.delete(new UpdateWrapper<ProductInfo>().eq("id", id));
            if (flag>0){
                log.debug("删除商品信息成功");
                return true;
            }else{
                log.debug("删除商品信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug("删除商品信息失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_DELETE_FILED);
        }
    }


    /**
     * 根据商品订单id查询多个商品
     * @param id  商品id
     * @return 商品集合
     */
    @Caching(cacheable = {@Cacheable(cacheNames = "productInfo")},put = {@CachePut(cacheNames = "productInfo")})
    public List<ProductInfo> getProductListById(Integer id){
        try {
            List<ProductInfo> products = new ArrayList<>();
            List<OrderProduct> productList = orderProductMapper.selectList(new QueryWrapper<OrderProduct>().eq("orderInfoId", id));
            IntStream.range(0, productList.size()).forEachOrdered(i -> {
                Integer productId = productList.get(i).getProductId();
                ProductInfo productInfo = this.getProductById(productId);
                productInfo.setProductCountInOrder(productList.get(i).getProductCount());
                products.add(productInfo);
            });
            log.debug("根据商品订单id查询多个商品成功");
            return products;
        } catch (Exception e) {
            log.debug("根据商品订单id查询多个商品失败");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_GET_FILED,"根据商品订单id查询多个商品失败");
        }
    }




}



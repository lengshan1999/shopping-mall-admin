package com.hbsi.productinfo.controller;


import com.hbsi.productType.service.impl.ProductTypeServiceImpl;
import com.hbsi.productinfo.entity.ProductInfo;
import com.hbsi.productType.entity.ProductType;
import com.hbsi.productinfo.service.impl.ProductInfoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author white
 * @since 2019-08-11
 */
@Slf4j
@Controller
@RequestMapping("/product/")
@Api(tags = "商品信息模块")
public class ProductInfoController {

    private final ProductInfoServiceImpl productInfoService;


    private final ProductTypeServiceImpl productTypeService;

    public ProductInfoController(ProductInfoServiceImpl productInfoService, ProductTypeServiceImpl productTypeService) {
        this.productInfoService = productInfoService;
        this.productTypeService = productTypeService;
    }


    /**
     * 分页查询商品信息
     * @param model model and view
     * @return html
     */
    @GetMapping("getAllProducts")
    public String getAllProducts(Integer current, Integer size,Model model) {
        model.addAttribute("msg","查询商品成功");
        return productInfoService.getGoods( current, size,model);
    }





    /**
     * 保存功能
     * @return HTML
     */
    @PostMapping("saveProduct")
    @ApiOperation("添加商品信息")
    public String saveProduct(@ApiParam("商品图片") MultipartFile file ,ProductInfo productInfo,Model model) {
        //上传图片大小限制
        return productInfoService.saveProductInfo(file, productInfo, model);
    }



    /**
     * 修改回显
     * @param id 商品id
     * @param model model and view
     * @return HTML
     */
    @GetMapping("lookProduct")
    public String lookProduct(@RequestParam("id") Integer id , Model model) {
        if (ObjectUtils.isEmpty(id)){
            log.debug("id参数为空");
            model.addAttribute("msgFiled","参数错误");
            return productInfoService.getGoods(1,4,model);
        }
        ProductInfo good = productInfoService.getProductById(id);
        model.addAttribute("good",good);
        List<ProductType> types = productTypeService.getTypes();
        model.addAttribute("types",types);
        return "product/update.html";
    }


    /**
     * 映射到添加商品页面
     * @param model model and view
     * @return HTML
     */
    @GetMapping("add")
    public String addPage(Model model) {
        List<ProductType> types = productTypeService.getTypes();
        model.addAttribute("types",types);
        return "product/add.html";
    }

    /**
     *修改功能
     * @return HTML
     */
    @PostMapping("updateProduct")
    public String updateProduct(ProductInfo productInfo,Model model) {
        boolean flag = productInfoService.updateProduct(productInfo);
        if (flag){
            model.addAttribute("msg","修改商品成功");
        }else{
            model.addAttribute("msgFiled","修改商品失败");
        }
        return productInfoService.getGoods(1,4,model);

    }


    /**
     * 删除功能
     * @param id 商品id
     * @return HTML
     */
    @GetMapping("productDelete")
    public String productDelete(@RequestParam("id")Integer id,Model model) {
        if (ObjectUtils.isEmpty(id)){
            model.addAttribute("msgFiled","参数为空");
            return productInfoService.getGoods(1,4,model);
        }
        boolean flag = productInfoService.deleteProductInfoById(id);
        if (flag){
            model.addAttribute("msg","删除商品成功");
        }else{
            model.addAttribute("msgFiled","删除商品失败");
        }
        return productInfoService.getGoods(1,4,model);
    }


    /**
     * 根据商品名称查询商品
     * @param productName 商品名称
     * @param model model and view
     * @return HTML
     */
    @GetMapping("getProductByName")
    public String getLikeName(String productName, Model model){
        if (ObjectUtils.isEmpty(productName)){
            model.addAttribute("msgFiled","请输入查询的内容");
            return "product/productByName.html";
        }
        ProductInfo product = productInfoService.getProductByName(productName);
        if (ObjectUtils.isEmpty(product)){
            model.addAttribute("msgFiled","暂无您搜索的结果");
        }else{
            model.addAttribute("msg","查询商品信息成功");
            model.addAttribute("product",product);
        }
        return "product/productByName.html";
    }



}

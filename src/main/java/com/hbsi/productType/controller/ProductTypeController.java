package com.hbsi.productType.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbsi.productType.entity.ProductType;
import com.hbsi.productType.service.impl.ProductTypeServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;


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
@RequestMapping("/productType/")
@Api(tags = "商品类型模块")
public class ProductTypeController {

    private final ProductTypeServiceImpl productTypeService;

    public ProductTypeController(ProductTypeServiceImpl productTypeService) {
        this.productTypeService = productTypeService;
    }


    /**
     * 分页查询商品类型
     * @param model model and view
     * @return html
     */
    @GetMapping("getAllTypes")
    public String getAllTypes(Integer current,Integer size,Model model) {
        model.addAttribute("msg","查询商品类型成功");
       return productTypeService.getTypes(current,size,model);
    }




    /**
     * 保存功能
     * @return html
     */
    @PostMapping("saveType")
    public String saveUser(ProductType type,Model model) {
        boolean flag =productTypeService.saveProductType(type);
        if (flag){
            model.addAttribute("msg","添加商品类型成功");
        }else{
            model.addAttribute("msgFiled","添加商品类型失败");
        }
        return productTypeService.getTypes(1,8,model);
    }





    /**
     * 修改回显
     * @param id 商品类型id
     * @param model model and view
     * @return HTML
     */
    @GetMapping("lookType")
    public String lookType(@RequestParam("id") Integer id , Model model) {
        if (ObjectUtils.isEmpty(id)){
            log.debug("参数为空");
            model.addAttribute("msgFiled","参数为空");
            return productTypeService.getTypes(1,8,model);
        }
        ProductType type = productTypeService.getOne(new QueryWrapper<ProductType>().eq("id",id));
        model.addAttribute("type",type);
        return "productType/seeDetailed.html";
    }





    /**
     * 映射到添加商品类型页面
     * @return HTML
     */
    @GetMapping("add")
    public String addPage() {
        return "productType/add.html";
    }




    /**
     *修改功能
     * @return HTML
     */
    @PostMapping("updateType")
    public String updateType(ProductType type,Model model) {
        boolean flag = productTypeService.updateProductType(type);
        if (flag){
            model.addAttribute("msg","修改商品类型成功");
        }else{
            model.addAttribute("msgFiled","修改商品类型失败");
        }
        return productTypeService.getTypes(1,8,model);
    }


    /**
     * 删除商品类型
     * @param id 商品类型id
     * @param model model and view
     * @return HTML
     */
    @GetMapping("typeDelete")
    public String typeDelete(@RequestParam("id")Integer id,Model model) {
        if (ObjectUtils.isEmpty(id)){
            log.debug("参数为空");
            return "redirect:/productType/getAllTypes";
        }
        boolean flag = productTypeService.deleteProductById(id);
        if (flag){
            model.addAttribute("msg","删除商品类型成功");
        }else{
            model.addAttribute("msgFiled","删除商品类型失败");
        }
        return "redirect:/productType/getAllTypes";
    }

}

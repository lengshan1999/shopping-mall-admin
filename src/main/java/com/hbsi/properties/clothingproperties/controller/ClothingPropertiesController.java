package com.hbsi.properties.clothingproperties.controller;


import com.hbsi.properties.clothingproperties.entity.ClothingProperties;
import com.hbsi.properties.clothingproperties.service.impl.ClothingPropertiesServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;



/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author white
 * @since 2019-06-30
 */
@Slf4j
@Controller
@RequestMapping("/clothingProperties/")
@Api(tags = "服装类属性信息模块")
public class ClothingPropertiesController {

    private final ClothingPropertiesServiceImpl clothingPropertiesService;

    public ClothingPropertiesController(ClothingPropertiesServiceImpl clothingPropertiesService) {
        this.clothingPropertiesService = clothingPropertiesService;
    }


    /**
     * 分页查询服装类信息
     * @param model model and view
     * @return HTML
     */
    @GetMapping("getClothing")
    public String getClothing(Integer current, Integer size,Model model) {
        if (ObjectUtils.isEmpty(current)){
            model.addAttribute("msgFiled","当前页参数为空");
            return  clothingPropertiesService.getAllClothing(current,size,model);
        }
        if (ObjectUtils.isEmpty(size)){
            model.addAttribute("msgFiled","每页数量参数为空");
            return  clothingPropertiesService.getAllClothing(current,size,model);
        }
        model.addAttribute("msg","查询服装类属性成功");
        return  clothingPropertiesService.getAllClothing(current,size,model);
    }


    /**
     * 映射到添加服装页面
     * @return HTML
     */
    @GetMapping("add")
    public String addPage() {
        return "propertiesClothing/add.html";
    }

    /**
     * 保存服装类属性
     * @return html
     */
    @PostMapping("saveClothing")
    public String saveClothing(ClothingProperties clothingProperties,Model model) {
        boolean flag = clothingPropertiesService.saveClothingProperties(clothingProperties);
        if (!flag){
            model.addAttribute("msgFiled","添加属性失败");

        }else{
            model.addAttribute("msg","添加属性成功");
        }
        return  clothingPropertiesService.getAllClothing(1,8,model);
    }

    /**
     * 根据的查询服装类属性
     * @param id 服装类id
     * @param model model and view
     * @return html
     */
    @GetMapping("lookPropertiesClothing")
    public String lookPropertiesClothing(@RequestParam Integer id,Model model) {
        if (ObjectUtils.isEmpty(id)){
            model.addAttribute("msgFiled","当前页参数为空");
            return  clothingPropertiesService.getAllClothing(1,8,model);
        }
        ClothingProperties clothingProperties = clothingPropertiesService.getClothingPropertiesById(id);
        model.addAttribute("clothingProperties",clothingProperties);
        return "propertiesClothing/update.html";
    }

    /**
     *修改功能
     * @return html
     */
    @PostMapping("updateClothing")
    public String updateClothing(ClothingProperties clothingProperties, Model model) {
        boolean flag = clothingPropertiesService.updateClothingProperties(clothingProperties);
        if (flag){
            model.addAttribute("msg","修改服装类属性成功");
        }else{
            model.addAttribute("msgFiled","修改服装类属性失败");
        }
        return  clothingPropertiesService.getAllClothing(1,8,model);
    }


    /**
     * 删除服装类属性
     * @param id  删除服装类属性id
     * @param model model and view
     * @return HTML
     */
    @GetMapping("deleteClothing")
    public String deleteClothing(@RequestParam Integer id,Model model) {
        boolean flag = clothingPropertiesService.deleteClothingPropertiesById(id);
        if (!flag){
            model.addAttribute("msgFiled","删除服装类属性失败");

        }else{
            model.addAttribute("msg","删除服装类属性成功");
        }
        return  clothingPropertiesService.getAllClothing(1,8,model);
    }





}

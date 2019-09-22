package com.hbsi.properties.elecproperties.controller;


import com.hbsi.properties.elecproperties.entity.ElecProperties;
import com.hbsi.properties.elecproperties.service.impl.ElecPropertiesServiceImpl;
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
@RequestMapping("/elecProperties/")
@Api(tags = "电子商品属性类信息模块")
public class ElecPropertiesController {
    
    private final ElecPropertiesServiceImpl elecPropertiesService;

    public ElecPropertiesController(ElecPropertiesServiceImpl elecPropertiesService) {
        this.elecPropertiesService = elecPropertiesService;
    }


    /**
     * 分页查询电子数码类信息
     * @param model model and view
     * @return HTML
     */
    @GetMapping("getElec")
    public String getElec(Integer current, Integer size, Model model) {
        if (ObjectUtils.isEmpty(current)){
            model.addAttribute("msgFiled","当前页参数为空");
            return  elecPropertiesService.getAllElecs(current,size,model);
        }
        if (ObjectUtils.isEmpty(size)){
            model.addAttribute("msgFiled","每页数量参数为空");
            return  elecPropertiesService.getAllElecs(current,size,model);
        }
        model.addAttribute("msg","查询电子数码类属性成功");
        return  elecPropertiesService.getAllElecs(current,size,model);
    }


    /**
     * 映射到添加电子数码页面
     * @return HTML
     */
    @GetMapping("add")
    public String addPage() {
        return "propertiesElec/add.html";
    }

    /**
     * 保存电子数码类属性
     * @return html
     */
    @PostMapping("saveElec")
    public String saveElec(ElecProperties ElecProperties, Model model) {
        boolean flag = elecPropertiesService.saveElecProperties(ElecProperties);
        if (!flag){
            model.addAttribute("msgFiled","添加属性失败");

        }else{
            model.addAttribute("msg","添加属性成功");
        }
        return  elecPropertiesService.getAllElecs(1,8,model);
    }

    /**
     * 根据的查询电子数码类属性
     * @param id 电子数码类id
     * @param model model and view
     * @return html
     */
    @GetMapping("lookPropertiesElec")
    public String lookPropertiesElec(@RequestParam Integer id,Model model) {
        if (ObjectUtils.isEmpty(id)){
            model.addAttribute("msgFiled","当前页参数为空");
            return  elecPropertiesService.getAllElecs(1,8,model);
        }
        ElecProperties ElecProperties = elecPropertiesService.getElecPropertiesById(id);
        model.addAttribute("elecProperties",ElecProperties);
        return "propertiesElec/update.html";
    }

    /**
     *修改功能
     * @return html
     */
    @PostMapping("updateElec")
    public String updateElec(ElecProperties ElecProperties, Model model) {
        boolean flag = elecPropertiesService.updateElecProperties(ElecProperties);
        if (flag){
            model.addAttribute("msg","修改电子数码类属性成功");
        }else{
            model.addAttribute("msgFiled","修改电子数码类属性失败");
        }
        return  elecPropertiesService.getAllElecs(1,8,model);
    }


    /**
     * 删除电子数码类属性
     * @param id  删除电子数码类属性id
     * @param model model and view
     * @return HTML
     */
    @GetMapping("deleteElec")
    public String deleteElec(@RequestParam Integer id,Model model) {
        boolean flag = elecPropertiesService.deleteElecPropertiesById(id);
        if (!flag){
            model.addAttribute("msgFiled","删除电子数码类属性失败");

        }else{
            model.addAttribute("msg","删除电子数码类属性成功");
        }
        return  elecPropertiesService.getAllElecs(1,8,model);
    }


    
}

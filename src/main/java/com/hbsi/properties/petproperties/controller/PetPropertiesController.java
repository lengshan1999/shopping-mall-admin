package com.hbsi.properties.petproperties.controller;


import com.hbsi.properties.petproperties.service.IPetPropertiesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/petProperties/")
@Api(tags = "宠物属性信息模块")
public class PetPropertiesController {

    @Autowired
    private IPetPropertiesService petPropertiesService;


}

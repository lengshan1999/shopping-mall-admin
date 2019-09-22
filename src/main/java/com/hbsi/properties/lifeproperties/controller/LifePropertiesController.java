package com.hbsi.properties.lifeproperties.controller;


import com.hbsi.properties.elecproperties.service.IElecPropertiesService;
import com.hbsi.properties.lifeproperties.service.LifePropertiesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
@RequestMapping("/lifeProperties/")
@Api(tags = "生活用品类信息模块")
public class LifePropertiesController {

    @Autowired
    private LifePropertiesService lifePropertiesService;



    
}

package com.hbsi.properties.petproperties.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.properties.petproperties.entity.PetProperties;
import com.hbsi.properties.petproperties.mapper.PetPropertiesMapper;
import com.hbsi.properties.petproperties.service.IPetPropertiesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2019-06-30
 */
@Service
public class PetPropertiesServiceImpl extends ServiceImpl<PetPropertiesMapper, PetProperties> implements IPetPropertiesService {

}

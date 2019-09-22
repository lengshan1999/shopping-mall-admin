package com.hbsi.properties.bookproperties.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.productType.entity.ProductType;
import com.hbsi.productType.mapper.ProductTypeMapper;
import com.hbsi.properties.bookproperties.entity.BookProperties;
import com.hbsi.properties.bookproperties.mapper.BookPropertiesMapper;
import com.hbsi.properties.bookproperties.service.IBookPropertiesService;
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
@CacheConfig(cacheNames = "bookProperties")
public class BookPropertiesServiceImpl extends ServiceImpl<BookPropertiesMapper, BookProperties> implements IBookPropertiesService {


    @Resource
    private BookPropertiesMapper bookPropertiesMapper;

    @Resource
    private ProductTypeMapper productTypeMapper;


    /**
     * 分页查询书籍类属性
     * @param current 当前页
     * @param size 每页数量
     * @return 分页对象
     */
    private IPage<BookProperties> getBookPropertiesByPage(Integer current, Integer size){
        try {
            Page<BookProperties> page = new Page<>(current, size);
            IPage<BookProperties> iPage = bookPropertiesMapper.selectPage(page, new QueryWrapper<>());
            log.debug("书籍类属性分页查询成功");
            return iPage;
        } catch (Exception e) {
            log.debug(e.getMessage(),"书籍类属性分页查询失败");
            throw new BaseException(ExceptionEnum.PROPERTIES_BOOK_GET_FILED,"分页查询书籍类属性失败");
        }
    }


    /**
     * 查询全部书籍类信息 公共方法
     * @param current 当前页
     * @param size  每页数量
     * @param model model  and view
     * @return html
     */
    public String getAllBooks(Integer current, Integer size, Model model){
        IPage<BookProperties> iPage = getBookPropertiesByPage(current, size);
        model.addAttribute("pages",iPage);
        return "propertiesBook/list.html";
    }


    /**
     * 根据id查询书籍类信息
     * @param id 书籍类信息id
     * @return 书籍类对象
     */
    @Cacheable
    public BookProperties getBookPropertiesById(Integer id){
        try {
            BookProperties bookProperties = bookPropertiesMapper.selectOne(new QueryWrapper<BookProperties>().eq("id", id));
            log.debug("根据id查询书籍类信息成功");
            return bookProperties;
        } catch (Exception e) {
            log.debug(e.getMessage(),"根据id查询书籍类信息失败");
            throw new BaseException(ExceptionEnum.PROPERTIES_BOOK_GET_FILED,"根据id查询书籍类信息失败");
        }
    }


    /**
     * 保存书籍类信息
     * @param bookProperties 书籍类对象
     * @return boolean
     */
    public boolean saveBookProperties(BookProperties bookProperties){

        try {
            bookProperties.setProductTypeName("书籍类");
            bookProperties.setTypeId(1);
            int flag1 = bookPropertiesMapper.insert(bookProperties);
            ProductType type = productTypeMapper.selectOne(new QueryWrapper<ProductType>().eq("id", 1));
            int flag = productTypeMapper.update(new ProductType().setTypePropertiesCount(type.getTypePropertiesCount() + 1), new UpdateWrapper<ProductType>().eq("id", 1));
            if (flag>0  && flag1>0){
                log.debug("保存书籍类信息成功");
                return true;
            }else{
                log.debug("保存书籍类信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"保存书籍类信息异常");
            throw  new  BaseException(ExceptionEnum.PROPERTIES_BOOK_SAVE_FILED);
        }

    }


    /**
     * 修改书籍类信息
     * @param bookProperties 书籍类对象
     * @return boolean
     */
    @Transactional
    @CachePut
    public boolean updateBookProperties(BookProperties bookProperties){
        try {
            BookProperties bookProperties1 = new BookProperties();
            bookProperties1.setOptionalValue(bookProperties.getOptionalValue());
            bookProperties1.setBookPropertiesName(bookProperties.getBookPropertiesName());
            bookProperties1.setProductTypeName("书籍类");
            bookProperties1.setTypeId(1);
            int flag = bookPropertiesMapper.update(bookProperties1, new UpdateWrapper<BookProperties>().eq("id", bookProperties.getId()));
            if (flag > 0){
                log.debug("修改书籍类信息成功");
                return  true;
            }else{
                log.debug("修改书籍类信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"修改书籍类信息异常");
            throw  new BaseException(ExceptionEnum.PROPERTIES_BOOK_UPDATE_FILED);
        }
    }


    /**
     * 删除书籍类信息
     * @param id 书籍类id
     * @return boolean
     */
    @Transactional
    @CacheEvict
    public boolean deleteBookPropertiesById(Integer id){
        try {
            int flag = bookPropertiesMapper.delete(new QueryWrapper<BookProperties>().eq("id", id));
            ProductType type = productTypeMapper.selectOne(new QueryWrapper<ProductType>().eq("id", 1));
            int flagType = productTypeMapper.update(new ProductType().setTypePropertiesCount(type.getTypePropertiesCount() - 1), new UpdateWrapper<ProductType>().eq("id", 1));
            if (flag > 0 && flagType>0){
                log.debug("删除书籍类信息成功");
                return true;
            }else{
                log.debug("删除书籍类信息失败");
                return false;
            }
        } catch (Exception e) {
            log.debug(e.getMessage(),"删除书籍类信息异常");
            throw new BaseException(ExceptionEnum.PROPERTIES_BOOK_DELETE_FILED);
        }

    }




}

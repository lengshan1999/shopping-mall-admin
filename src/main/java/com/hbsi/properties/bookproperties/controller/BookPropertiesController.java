package com.hbsi.properties.bookproperties.controller;



import com.hbsi.properties.bookproperties.entity.BookProperties;
import com.hbsi.properties.bookproperties.service.impl.BookPropertiesServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


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
@RequestMapping("/bookProperties/")
@Api(tags="书籍属性类信息模块")
public class BookPropertiesController {
	


	private final BookPropertiesServiceImpl bookPropertiesService;

	public BookPropertiesController(BookPropertiesServiceImpl bookPropertiesService) {
		this.bookPropertiesService = bookPropertiesService;
	}


	/**
	 * 分页查询书籍类信息
	 * @param model model and view
	 * @return HTML
	 */
	@GetMapping("getBooks")
	public String getClothing(Integer current, Integer size,Model model) {
		if (ObjectUtils.isEmpty(current)){
			model.addAttribute("msgFiled","当前页参数为空");
			return  bookPropertiesService.getAllBooks(current,size,model);
		}
		if (ObjectUtils.isEmpty(size)){
			model.addAttribute("msgFiled","每页数量参数为空");
			return  bookPropertiesService.getAllBooks(current,size,model);
		}
		model.addAttribute("msg","查询书籍类属性成功");
		return  bookPropertiesService.getAllBooks(current,size,model);
	}



	/**
	 * 映射到添加页面
	 * @return HTML
	 */
	@GetMapping("add")
	public String addPage() {
		return "propertiesBook/add.html";
	}




	/**
	 * 保存书籍类信息
	 * @return HTML
	 */
	@PostMapping("saveBook")
	public String saveBook(BookProperties bookProperties,Model model) {
		boolean flag = bookPropertiesService.saveBookProperties(bookProperties);
		if (!flag){
			model.addAttribute("msgFiled","添加属性失败");

		}else{
			model.addAttribute("msg","添加属性成功");
		}
		return  bookPropertiesService.getAllBooks(1,8,model);
	}



	/**
	 * 修改回显 根据id查询书籍类信息
	 * @param id 书籍类id
	 * @param model model and view
	 * @return HTML
	 */
	@GetMapping("lookPropertiesBook")
	public String lookPropertiesBook(@RequestParam("id") Integer id,Model model) {
		if (ObjectUtils.isEmpty(id)){
			model.addAttribute("msgFiled","参数为空");
			return  bookPropertiesService.getAllBooks(1,8,model);
		}
		BookProperties bookProperties = bookPropertiesService.getBookPropertiesById(id);
		model.addAttribute("bookProperties",bookProperties);
		return "propertiesBook/update.html";
	}




	/**
	 *修改书籍类信息功能
	 * @return HTML
	 */
	@PostMapping("updateBook")
	public String updateBook(BookProperties bookProperties, Model model) {
		boolean flag = bookPropertiesService.updateBookProperties(bookProperties);
		if (flag){
			model.addAttribute("msg","修改书籍类属性成功");
		}else{
			model.addAttribute("msgFiled","修改书籍类属性失败");
		}
		return  bookPropertiesService.getAllBooks(1,8,model);
	}


	/**
	 * 删除书籍类信息
	 * @param id 书籍类id
	 * @param model model and view
	 * @return HTML
	 */
	@GetMapping("deleteBook")
	public String deleteBook(@RequestParam("id") Integer id,Model model) {
		if (ObjectUtils.isEmpty(id)){
			model.addAttribute("msgFiled","参数为空");
			return  bookPropertiesService.getAllBooks(1,8,model);
		}
		boolean flag = bookPropertiesService.deleteBookPropertiesById(id);
		if (!flag){
			model.addAttribute("msgFiled","删除书籍类属性失败");

		}else{
			model.addAttribute("msg","删除书籍类属性成功");
		}
		return  bookPropertiesService.getAllBooks(1,8,model);
	}
	
	
	
}

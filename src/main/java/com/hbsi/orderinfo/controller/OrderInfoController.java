package com.hbsi.orderinfo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hbsi.address.entity.UserAddress;
import com.hbsi.address.service.impl.UserAddressServiceImpl;
import com.hbsi.orderinfo.entity.OrderInfo;
import com.hbsi.orderinfo.service.impl.OrderInfoServiceImpl;
import com.hbsi.productinfo.entity.ProductInfo;
import com.hbsi.productinfo.service.impl.ProductInfoServiceImpl;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author white
 * @since 2019-08-16
 */
@Controller
@RequestMapping("/orderInfo/")
public class OrderInfoController {


    private final UserAddressServiceImpl userAddressService;

    private final OrderInfoServiceImpl orderInfoService;

    private final ProductInfoServiceImpl productInfoService;

    public OrderInfoController(UserAddressServiceImpl userAddressService, OrderInfoServiceImpl orderInfoService, ProductInfoServiceImpl productInfoService) {
        this.userAddressService = userAddressService;
        this.orderInfoService = orderInfoService;
        this.productInfoService = productInfoService;
    }

    @GetMapping("getAllOrder")
    public String getAllOrder(Integer current,Integer size, Model model){
        if (ObjectUtils.isEmpty(current)){
            model.addAttribute("msgFiled","current参数为空");
            return  "orderInfo/list.html";
        }
        if (ObjectUtils.isEmpty(size)){
            model.addAttribute("msgFiled","size参数为空");
            return  "orderInfo/list.html";
        }
        IPage<OrderInfo> iPage = orderInfoService.getAllOrdersByPage(current, size);
        if (iPage == null){
            model.addAttribute("msgFiled","当前暂无数据");
        }else{
            model.addAttribute("msg","查询商品订单成功");
            model.addAttribute("pages",iPage);
        }
        return  "orderInfo/list.html";
    }


    /**
     * 订单发货
     * @param model model and view
     * @param id 订单id
     * @return HTML
     */
    @PostMapping("sendGoods")
    public String sendGoods(Model model,Integer id){
        if (ObjectUtils.isEmpty(id)){
            model.addAttribute("msgFiled","发货失败");
            return  "orderInfo/list.html";
        }
        boolean flag = orderInfoService.sendGoods(id);
        if (flag){
            model.addAttribute("msg","发货成功");
        }else{
            model.addAttribute("msgFiled","发货失败");
        }
        return  "orderInfo/list.html";
    }


    /**
     * 查看详情
     * @param model model and view
     * @param id 订单id
     * @return HTML
     */
    @GetMapping("seeDetailed")
    public String seeDetailed(Model model , Integer id){
        if (ObjectUtils.isEmpty(id)){
            model.addAttribute("msgFiled","id参数为空");
            return  "orderInfo/seeDetailed.html";
        }
        List<ProductInfo> products = productInfoService.getProductListById(id);
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(id);
        UserAddress address = userAddressService.getAddressById(orderInfo.getAddressId());
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("products",products);
        model.addAttribute("address",address);
        return  "orderInfo/seeDetailed.html";
    }


    /**
     * 根据订单状态查询订单
     * @param orderInfoStatus 订单状态
     * @param model model and view
     * @return HTML
     */
    @GetMapping("getOrderByStatus")
    public String getOrderByStatus(Integer orderInfoStatus,Model model){
        if (orderInfoStatus == 99999){
            return  getAllOrder(1,4,model);
        }
        List<OrderInfo> infoList = orderInfoService.getOrderInfoByStatus(orderInfoStatus);
        if (infoList != null){
            model.addAttribute("orderList",infoList);
            model.addAttribute("msg","查询订单状态成功");
        }else{
            model.addAttribute("msgFiled","查询订单状态失败");
        }
        return "orderInfo/list.html";
    }


    /**
     * 根据订单编号查询订单
     * @param orderNumber 订单编号
     * @param model model and view
     * @return HTML
     */
    @GetMapping("getOrderByOrderNum")
    public String getOrderByOrderNum(String orderNumber , Model model){
        if (ObjectUtils.isEmpty(orderNumber)){
            model.addAttribute("msgFiled","orderNumber参数为空");
            return "orderInfo/orderByName.html";
        }
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderNum(orderNumber);
        if (orderInfo == null){
            model.addAttribute("msgFiled","暂无该订单信息");
        }else{
            model.addAttribute("msg","查询订单信息成功");
            model.addAttribute("orderInfo",orderInfo);
        }
        return "orderInfo/orderByName.html";
    }


}
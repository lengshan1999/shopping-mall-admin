package com.hbsi.properties;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Properties {


    @GetMapping("/properties")
    public String isProperties(Integer typeId){
        if (typeId == 1){
            //书籍类
            return "redirect:/bookProperties/getBooks?current=1&size=8";
        }else  if (typeId == 2){
            //服装类
            return "redirect:/clothingProperties/getClothing?current=1&size=8";
        }else  if (typeId == 3){
            //电子数码类
            return "redirect:/elecProperties/getElec?current=1&size=8";
        }else  if (typeId == 4){
            //生活用品类
            //return "redirect:/lifeProperties/getLife?current=1&size=8";
        }else  if (typeId == 5){
            //宠物类
            //return "redirect:/petProperties/getPets?current=1&size=8";
        }else  if (typeId == 6){
            //测试类
            //return "redirect:/testProperties/getTest?current=1&size=8";
        }
        return "redirect:/productType/getAllTypes?current=1&size=8";
    }
}

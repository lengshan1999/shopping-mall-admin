package com.hbsi.utils;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

public class NumUtils {
	
	//商品编号
	public static String createProductNum() {
		return "PNo"+Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))*1000;
	}
	
	
	//店铺编号
	public  static String createShopNum() {
		return "SNo"+(System.currentTimeMillis()+"").substring(1)+  (System.nanoTime()+"").substring(7,10);  
	}
	
	
	//订单编号
	public static String createOrderNum() {
        Date date=new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return "RNo"+time + String.format("%011d", hashCodeV);
    }
	
	
	public static void main(String[] args) {
//		System.out.println(createProductNum());
		System.out.println(createOrderNum());
//		System.out.println(createShopNum());
	}
	
}

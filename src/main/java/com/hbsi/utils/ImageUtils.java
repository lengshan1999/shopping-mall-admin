package com.hbsi.utils;


import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
public class ImageUtils {

    static String currentPath = null;

    static String path = null;

    static String productImgPath = null;

    static {
        //获取当前路径
        currentPath = System.getProperty("user.dir");
        //路径拼接
        path = "\\src\\main\\resources\\static\\image\\productImage\\";
        //存放商品图片的路径
        productImgPath = currentPath + path;
    }


    public static String upLoadFile(MultipartFile upload, String ImgName) {

        //判断文件夹是否存在,不存在则创建
        File file = new File(productImgPath);

        if (!file.exists()) {
            file.mkdirs();
        }
        String originalFileName = upload.getOriginalFilename();//获取原始图片的扩展名
        String newFileName = ImgName + originalFileName;
        String newFilePath = productImgPath + newFileName; //新文件的路径

        try {
            upload.transferTo(new File(newFilePath));//将传来的文件写入新建的文件

            return "/static/image/productImage/"+newFileName;
        } catch (Exception e) {
            //处理异常
            log.debug("上传用户图片异常");
            throw new BaseException(ExceptionEnum.PRODUCT_INFO_UPLOAD_IMG_FILED,"上传图片失败,请重新上传");
        }

    }


    public static void main(String[] args) {

        System.out.println(productImgPath);

    }

}

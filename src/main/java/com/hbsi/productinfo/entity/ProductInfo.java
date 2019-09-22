package com.hbsi.productinfo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author white
 * @since 2019-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="商品信息类",description="保存商品信息")
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 商品id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="商品id",name="id",required=true,dataType="int",example="1")
    private Integer id;

    /**
     * 商品编号
     */
    @TableField("productNum")
    @ApiModelProperty(value="商品编号",name="productNum",hidden=true)
    private String productNum;

    /**
     * 商品名称
     */
    @TableField("productName")
    @ApiModelProperty(value="商品名称",name="productName",example="iPhone X",required=true)
    private String productName;

    /**
     * 商品库存
     */
    @TableField("productStock")
    @ApiModelProperty(value="商品库存",name="productStock",example="999",required=true)
    private String productStock;

    /**
     * 商品价格
     */
    @TableField("productPrice")
    @ApiModelProperty(value="商品价格",name="productPrice",example="999.99",required=true)
    private BigDecimal productPrice;

    /**
     * 商品重量
     */
    @TableField("productWeight")
    @ApiModelProperty(value="商品重量",name="productWeight",example="9.99")
    private Double productWeight;

    /**
     * 商品类型id
     */
    @TableField("productTypeId")
    @ApiModelProperty(value="商品类型id",name="productTypeId",required=true,dataType="int",example="1")
    private Integer productTypeId;

    /**
     * 商品类型
     */
    @TableField("productType")
    @ApiModelProperty(value="商品类型名称",name="productType",example="电子商品类")
    private String productType;

    /**
     * 商品描述
     */
    @TableField("description")
    @ApiModelProperty(value="商品描述",name="description",example="iPhone X你值得拥有")
    private String description;

    /**
     * 商品图片
     */
    @TableField("productImg")
    @ApiModelProperty(value="商品图片",name="productImg",hidden = true)
    private String productImg;

    /**
     * -1:已下架 0:缺货,1:正常
     * 默认为1
     */
    @TableField("productInfoStatus")
    @ApiModelProperty(value="商品状态",name="productInfoStatus",hidden=true)
    private Integer productInfoStatus;

    /**
     * 所属店铺id
     */
    @TableField("shopId")
    @ApiModelProperty(value="所属店铺id",name="shopId",example="1",required=true)
    private Integer shopId;


    /**
     * 商品二维码,商品的唯一标识
     */
    @TableField("QRCode")
    @ApiModelProperty(value="商品二维码",name="QRCode",hidden = true)
    private String QRCode;

    /**
     * 添加商品时间
     */
    @TableField("createDate")
    @ApiModelProperty(value="添加商品时间",name="createDate",hidden=true)
    private String createDate;

    @TableField("productCountInOrder")
    @ApiModelProperty(value="商品在订单中的数量",name="productCountInOrder",hidden=true)
    private Integer productCountInOrder;


}

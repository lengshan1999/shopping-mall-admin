package com.hbsi.exception;

public enum ExceptionEnum {

	SUCCESS("200", "成功"),

	PRODUCT_INFO_CREATE_CODE_FILED("000300","商品二维码创建失败"),
	PRODUCT_INFO_UPLOAD_IMG_FILED("000301","图片上传失败"),

	USER_GET_FILED("000400","查询用户失败"),
	USER_SAVE_FILED("000401","保存用户失败"),
	USER_UPDATE_FILED("000402","修改用户失败"),
	USER_DELETE_FILED("000403","删除用户失败"),

	PRODUCT_INFO_GET_FILED("000500","查询商品信息失败"),
	PRODUCT_INFO_SAVE_FILED("000501","保存商品信息失败"),
	PRODUCT_INFO_UPDATE_FILED("000502","修改商品信息失败"),
	PRODUCT_INFO_DELETE_FILED("000503","删除商品信息失败"),

	PRODUCT_TYPE_GET_FILED("000600","查询商品类型失败"),
	PRODUCT_TYPE_SAVE_FILED("000601","保存商品类型失败"),
	PRODUCT_TYPE_UPDATE_FILED("000602","修改商品类型失败"),
	PRODUCT_TYPE_DELETE_FILED("000603","删除商品类型失败"),


	PROPERTIES_CLOTHING_GET_FILED("000700","查询服装类属性失败"),
	PROPERTIES_CLOTHING_SAVE_FILED("000701","保存服装类属性失败"),
	PROPERTIES_CLOTHING_UPDATE_FILED("000702","修改服装类属性失败"),
	PROPERTIES_CLOTHING_DELETE_FILED("000703","删除服装类属性失败"),


	PROPERTIES_BOOK_GET_FILED("000800","查询书籍类属性失败"),
	PROPERTIES_BOOK_SAVE_FILED("000801","保存书籍类属性失败"),
	PROPERTIES_BOOK_UPDATE_FILED("000802","修改书籍类属性失败"),
	PROPERTIES_BOOK_DELETE_FILED("000803","删除书籍类属性失败"),


	PROPERTIES_ELECTRON_GET_FILED("000900","查询电子数码类属性失败"),
	PROPERTIES_ELECTRON_SAVE_FILED("000901","保存电子数码类属性失败"),
	PROPERTIES_ELECTRON_UPDATE_FILED("000902","修改电子数码类属性失败"),
	PROPERTIES_ELECTRON_DELETE_FILED("000903","删除电子数码类属性失败"),


	ORDER_INFO_GET_FILED("001000","查询订单信息失败"),
	ORDER_INFO_SAVE_FILED("001001","保存订单信息失败"),
	ORDER_INFO_UPDATE_FILED("001002","修改订单信息失败"),
	ORDER_INFO_DELETE_FILED("001003","删除订单信息失败"),

	;
	private String status;
	private String message;

	ExceptionEnum(String status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}

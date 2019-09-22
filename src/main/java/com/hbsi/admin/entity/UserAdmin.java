package com.hbsi.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2019-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type= IdType.AUTO)
    private Integer id;

    @TableField("userName")
    private String userName;

    @TableField("password")
    private String password;


}

package com.example.test.demo.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user_info")
public class UserInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("avatar")
    private String avatar;

    @TableField("description")
    private String description;

    @TableField("roles")
    private String roles;

    @TableField("token")
    private String token;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("extension")
    private String extension;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField(exist = false)
    private String ex;
}

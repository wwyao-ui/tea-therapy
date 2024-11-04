package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.hadoop.record.meta.TypeID;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author he
 * @since 2024-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 名字
     */
    private String name;
    /**
     * 密码
     */
    private String pwd;

    /**
     * 角色，1管理员 2用户/改为标签
     */
    private String sortname;


}

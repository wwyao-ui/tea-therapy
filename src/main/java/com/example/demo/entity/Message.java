package com.example.demo.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 昵称
     */
    private String name;
//    private String message;
    /**
     * 留言
     */
   private String liuyan;
}
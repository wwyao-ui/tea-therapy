package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author he
 * @since 2024-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Calculation implements Serializable {

    private static final long serialVersionUID = 1L;
  @TableId(type= IdType.AUTO)
    private Integer id;

    private String questionnaireid;

    private Integer lscore;

    private Integer uid;


}

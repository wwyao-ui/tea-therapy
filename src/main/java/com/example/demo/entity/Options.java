package com.example.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author he
 * @since 2024-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 选项id
     */
    private String id;
    /**
     * 问题id
     */
    private String questionId;

    private String content;


}

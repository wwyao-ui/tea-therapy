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
 * @since 2024-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Userbrowsinghistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer uid;

    private Integer tid;

    private String content;


}

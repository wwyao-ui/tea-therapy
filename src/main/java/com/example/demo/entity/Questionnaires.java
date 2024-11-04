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
public class Questionnaires implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;


}

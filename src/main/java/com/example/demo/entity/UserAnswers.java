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
public class UserAnswers implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String questionId;

    private String optionId;

    private String questionnaireId;


}

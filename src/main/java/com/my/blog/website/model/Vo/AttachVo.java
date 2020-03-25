package com.my.blog.website.model.Vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data
public class AttachVo implements Serializable {
    private Integer id;

    private String fname;

    private String ftype;

    private String fkey;

    private Integer authorId;

    private Integer created;

    private static final long serialVersionUID = 1L;

}
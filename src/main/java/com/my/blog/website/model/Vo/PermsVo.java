package com.my.blog.website.model.Vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限表
 */
@Data
public class PermsVo implements Serializable {
    /**
     * 权限id
     */
    private int id;

    /**
     * 权限
     */
    private String perm;

    private static final long serialVersionUID = 1L;
}

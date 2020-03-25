package com.my.blog.website.model.Bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统备份
 */
@Data
public class BackResponseBo implements Serializable {

    private String attachPath;

    private String themePath;

    private String sqlPath;

}

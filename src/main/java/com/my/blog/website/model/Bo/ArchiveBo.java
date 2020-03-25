package com.my.blog.website.model.Bo;

import com.my.blog.website.model.Vo.ContentVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 文章
 */
@Data
public class ArchiveBo implements Serializable {

    /**
     * 日期
     */
    private String date;

    /**
     * 点击率
     */
    private String count;

    /**
     * 文章评论
     */
    private List<ContentVo> articles;

}

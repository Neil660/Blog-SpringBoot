package com.my.blog.website;

import com.my.blog.website.model.Vo.OptionVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@SpringBootTest
class MyBlogApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        //System.out.println(stringRedisTemplate.opsForValue().get("a"));
        OptionVo optionVo = new OptionVo("name","value","description");
        /*optionRedisTemplate.opsForValue().set("optionvo",optionVo);
        System.out.println(optionRedisTemplate.opsForValue().get("optionvo"));*/
    }

}

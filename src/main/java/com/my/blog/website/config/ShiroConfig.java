package com.my.blog.website.config;

import com.my.blog.website.constant.WebConst;
import com.my.blog.website.dto.Types;
import com.my.blog.website.model.Vo.OptionVo;
import com.my.blog.website.model.Vo.UserVo;
import com.my.blog.website.service.IOptionService;
import com.my.blog.website.service.IUserService;
import com.my.blog.website.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

//@Configuration
public class ShiroConfig {

    @Autowired
    private IOptionService optionService;

    @Autowired
    private IUserService userService;

    @Autowired
    private Commons commons;

    @Autowired
    private AdminCommons adminCommons;

    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        /**
         * 添加shiro内置过滤器
         *  anon：无需认证就可以访问
         *  authc：必须认证才能访问
         *  user：必须拥有remember me功能才能使用
         *  perms：拥有对某个资源的权限才能访问
         *  role：拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        //授权
        filterMap.put("/admin/*","perms[admin:perms]");
        // 对路径设置认证访问
        filterMap.put("/admin/login","anon");
        filterMap.put("/admin/*","authc");
        filterMap.put("/login","anon");
        //当访问需要登录的资源时，可以跳转到登录页面
        bean.setLoginUrl("/admin/login");
        //设置未授权跳转页面
        //bean.setUnauthorizedUrl("/noauth");
        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //filterMap.put("/logout", "logout");
        bean.setFilterChainDefinitionMap(filterMap);

        return bean;
    }

    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建Realm对象，需要自定义类
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}

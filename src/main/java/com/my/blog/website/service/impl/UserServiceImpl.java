package com.my.blog.website.service.impl;

import com.my.blog.website.dao.UserVoMapper;
import com.my.blog.website.exception.TipException;
import com.my.blog.website.model.Vo.UserVo;
import com.my.blog.website.service.IUserService;
import com.my.blog.website.utils.TaleUtils;
import com.my.blog.website.model.Vo.UserVoExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by BlueT on 2017/3/3.
 */
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserVoMapper userDao;

    @Override
    @Transactional
    public Integer insertUser(UserVo userVo) {
        Integer uid = null;
        if (StringUtils.isNotBlank(userVo.getUsername()) && StringUtils.isNotBlank(userVo.getEmail())) {
            //用户密码加密
            //String encodePwd = TaleUtils.MD5encode(userVo.getUsername() + userVo.getPassword());
            String encodePwd = DigestUtils.md5DigestAsHex(userVo.getPassword().getBytes());
            userVo.setPassword(encodePwd);
            userDao.insertSelective(userVo);
        }
        return userVo.getUid();
    }

    /**
     * @Cacheable 将方法的运行结果进行缓存；第二次再要相同的数据，直接从缓存中获取，不再调用方法；
        1、cacheNames/value:指定缓存组件的名字， 数组的方式，可以指定多个缓存组件名称。
        2、key: 缓存数据使用的key，默认使用方法参数的值作为key。也可以自己指定，通过编写
        SpEL指定key的值；如：#root.methodName 、#id等
        3、keyGenerator: key的生成器，可以自己编写key的生成器组件。
        注意：在使用时key和keyGenerator二选一。
        4、cacheManager: 指定缓存管理器。
        5、condition： 指定符合条件的情况下才缓存；如： condition = "#id>0" "#a0>1"才进行缓存
        6、unless: 否定缓存； 当unless指定的条件为true,方法的返回值就不会缓存；
        如：可以获取到结果进行判断unless = "#result == null "  当方法结果为null时，不缓存。
        7、sync: 是否使用异步模式
     * @CacheEvict 移除缓存
        1、allEntries = true 每次删除，将指定缓存中的所有数据全都删除
        2、beforeInvocation=false ,缓存的清除是否是在方法之前执行，默认false, 即在方法之后清除，当方法执
        行出现异常时，缓存不会清除。
        beforeInvocation=true ,方法之前清除，无论方法执行是否出现异常，缓存都会清除。
     * @CachePut 修改了数据库的某个数据，同时更新缓存
         运行时机：先调用目标方法，将目标方法的结果缓存起来
         属性与@Cacheable相同
     */
    @Cacheable(cacheNames = "user") //可缓存
    @Override
    public UserVo queryUserById(Integer uid) {
        System.out.println("查询用户" + uid);
        UserVo userVo = null;
        if (uid != null) {
            userVo = userDao.selectByPrimaryKey(uid);
        }
        return userVo;
    }

    @Override
    public UserVo queryUserByName(String username) {
        UserVo userVo = null;
        if (!username.equals("") && username == null) {
            userVo = userDao.selectByName(username);
        }
        return userVo;
    }

    @Override
    public UserVo login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new TipException("用户名和密码不能为空");
        }
        UserVoExample example = new UserVoExample();
        UserVoExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        long count = userDao.countByExample(example);
        if (count < 1) {
            throw new TipException("不存在该用户");
        }
        //String pwd = TaleUtils.MD5encode(username + password);//加密
        String pwd = DigestUtils.md5DigestAsHex(password.getBytes());//springboot自带MD5加密
        criteria.andPasswordEqualTo(pwd);
        List<UserVo> userVos = userDao.selectByExample(example);
        if (userVos.size() != 1) {
            throw new TipException("用户名或密码错误");
        }
        return userVos.get(0);
    }

    @Override
    public Integer sign(UserVo userVo) {
        if (StringUtils.isBlank(userVo.getUsername()) || StringUtils.isBlank(userVo.getPassword())) {
            throw new TipException("用户名和密码不能为空");
        }
        userVo.setGroupName(userVo.getPassword());//将真正密码放到groupname中以便查看
        //String encodePwd = TaleUtils.MD5encode(userVo.getUsername() + userVo.getPassword());
        String encodePwd = DigestUtils.md5DigestAsHex(userVo.getPassword().getBytes());
        userVo.setPassword(encodePwd);
        userVo.setScreenName(userVo.getUsername());
        userVo.setCreated((int) (System.currentTimeMillis()/1000L));
        userDao.insertSelective(userVo);
        return userVo.getUid();
    }

    @Override
    @Transactional
    public void updateByUid(UserVo userVo) {
        if (null == userVo || null == userVo.getUid()) {
            throw new TipException("userVo is null");
        }
        int i = userDao.updateByPrimaryKeySelective(userVo);
        if (i != 1) {
            throw new TipException("update user by uid and retrun is not one");
        }
    }
}

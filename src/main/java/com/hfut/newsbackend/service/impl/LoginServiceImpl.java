package com.hfut.newsbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hfut.newsbackend.mapper.UserMapper;
import com.hfut.newsbackend.pojo.base.User;
import com.hfut.newsbackend.pojo.show.LoginUser;
import com.hfut.newsbackend.response.ResponseResult;
import com.hfut.newsbackend.service.inter.LoginService;
import com.hfut.newsbackend.utils.JwtUtil;
import com.hfut.newsbackend.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author Lucky
 * @description: TODO
 * @date 2022/3/21 11:32
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper ;

    @Override
    public ResponseResult login(User user) {
        // 通过AuthenticationManager的authenticate方法来进行用户认证 账号和密码
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //authenticate存入redis   用户信息存入redis 键值对
        redisCache.setCacheObject("login:"+userId,loginUser);

        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);

        return new ResponseResult(200,"登陆成功",map);
    }

    /**
     * 退出
     */
    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        //删除redis中的用户token
        redisCache.deleteObject("login:"+userid);

        //返回结果
        return new ResponseResult(200,"退出成功");
    }

    /**
     * 根据authentication来获取当前用户的信息，包括基本信息和权限
     * @return
     */
    @Override
    public ResponseResult getUserInfo() {
        //获取SecurityContextHolder用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        HashMap<String,Object> map = new HashMap<>();
        //将密码置为空返回给前端
        loginUser.getUser().setPassword("");
        map.put("user" , loginUser.getUser()) ;
        map.put("authorities" , loginUser.getAuthorities()) ;
        //去掉password这一属性
        return new ResponseResult(200 , "获取用户信息成功",map);

    }

    /**
     * 用户注册
     * @param user
     * @return boolean
     */
    @Override
    public ResponseResult register(User user) {
        // 在数据库查找是否存在该用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account" ,user.getAccount()) ;
        User queryUser = userMapper.selectOne(wrapper);

        // 如果用户已存在，则反馈已存在该用户
        if( !Objects.isNull(queryUser)){
            return new ResponseResult(200,"该账号已被注册",false);
        }

        //为用户建立数据库账号
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        int line = userMapper.insert(user);
        if (line != 0) {
            //插入成功 注册成功
            return new ResponseResult(200 , "注册成功" , true) ;
        }
        return null ;
    }

    /**
     * 是否已注册
     * @param account
     * @return
     */
    @Override
    public ResponseResult isRegister(String account) {
        QueryWrapper<User> wrapper = new QueryWrapper<>() ;
        wrapper.eq("account" , account) ;
        User user = userMapper.selectOne(wrapper);

        if (Objects.isNull(user)) return new ResponseResult(200,"该账号尚未注册",false);
        return new ResponseResult(200,"该账号已注册",true);

    }
}
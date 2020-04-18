package org.feather.user.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.feather.common.constants.Constants;
import org.feather.common.exception.FeatherBuyException;
import org.feather.user.dao.UserMapper;
import org.feather.user.entity.User;
import org.feather.user.entity.UserElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-03-10 07:44
 **/
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService  {


    @Autowired
    private UserMapper userMapper;
    @Autowired
     private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CuratorFramework  curatorFramework;



    @Override
    public UserElement login(User user) {
        UserElement ue=null;
        User existUser=userMapper.selectByEmail(user.getEmail());
        if (existUser==null){
            throw new FeatherBuyException("用户不存在");
        }else {
            boolean result=passwordEncoder.matches(user.getPassword(),existUser.getPassword());
            if (!result){
                //密码错误
                throw new FeatherBuyException("密码错误");

            }else {
                //验证通过 赋值ue
                ue =new UserElement();
                ue.setEmail(existUser.getEmail());
                ue.setUserId(existUser.getId());
                ue.setUuid(existUser.getUuid());
                ue.setNickname(existUser.getNickname());
            }
        }
        return ue;
    }
    @Override
    public void registerUser(User user) throws Exception {
        InterProcessMutex lock=null;
        try {
            lock=new InterProcessMutex(curatorFramework, Constants.USER_REGISTER_DISTRIBUTE_LOCK_PATH);
            boolean retry=true;
            do{
                if( lock.acquire(3000, TimeUnit.MILLISECONDS)){
                    //查询重复的用户
                    User repeatedUser=userMapper.selectByEmail(user.getEmail());
                    if (repeatedUser!=null){
                        throw new FeatherBuyException("用户邮箱重复");
                    }
                    //检查两次密码是否一致
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setNickname("买买网用户"+user.getEmail());
                    userMapper.insertSelective(user);
                    retry=false;
                }
            }while (retry);


        }catch (Exception e){
            log.error("用户注册异常",e);
            throw  e;

        }finally {
            if (null!=lock){
                try {
                    lock.release();
                    log.info(user.getEmail()+Thread.currentThread().getName()+"释放锁");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }

}

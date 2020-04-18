package org.feather.user.controller;

import org.feather.common.constants.Constants;
import org.feather.common.resp.ApiResult;
import org.feather.user.entity.User;
import org.feather.user.entity.UserElement;
import org.feather.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-03 08:27
 **/
@RestController
@RequestMapping("user")
public class UserController {

        @Autowired
        @Qualifier("userService")
      private UserService userService;

        @RequestMapping("/login")
    public ApiResult login(@RequestBody @Valid  User user, HttpSession session){
            ApiResult<UserElement> result=new ApiResult<>(Constants.RESP_STATUS_OK,"登陆成功");
            UserElement ue=  userService.login(user);
          if (ue!=null){
              if (session.getAttribute(Constants.REQUEST_USER_SESSION)==null){
                  session.setAttribute(Constants.REQUEST_USER_SESSION,ue);
              }
              result.setData(ue);
          }
          return result;

        }
        @RequestMapping("/register")
    public  ApiResult Register(@RequestBody @Valid User user) throws Exception {
            userService.registerUser(user);
            return  new ApiResult(Constants.RESP_STATUS_OK,"注册成功");
        }


}

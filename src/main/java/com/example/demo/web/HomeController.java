package com.example.demo.web;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by BFD-593 on 2017/8/8.
 */
@Controller
public class HomeController {
    @RequestMapping({"/","/index"})
    public String index() {
        return "index";
    }

    /**
     * logout由shiro实现，我们只需提供入口即可
     * filterChainDefinitionMap.put("/logout", "logout");
     * @return
     */
    @RequestMapping({"/logout"})
    public String logout(){
        return "login";
    }

    /**
     * 因为设置了setLoginUrl("/login");登录url如果没有登录，
     * 所有的请求都发送到这里。shiro会自动调用securityManager
     * 此方法不处理登录成功,由shiro进行处理。
     * @param request
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        map.put("msg", error);
        return "login";
    }
}

package com.example.demo.config.ExceptionResolver;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**同时将该实现类以bean的方式，放到启动类中
 * Created by BFD-593 on 2017/8/9.
 */
public class MyExceptionResolver implements HandlerExceptionResolver{
    /**
     * 统一异常处理，当出现runtimeException时，跳转到500页面。
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if(e instanceof RuntimeException){
            ModelAndView mv = new ModelAndView("/500");
            return mv;
        }
        return null;
    }
}

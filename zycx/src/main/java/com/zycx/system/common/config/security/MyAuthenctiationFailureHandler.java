package com.zycx.system.common.config.security;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义登录失败处理
 *
 * @author gly
 * @date 2019/04/16
 */
@Component
public class MyAuthenctiationFailureHandler implements AuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException,UsernameNotFoundException {

        String mst=request.getParameter("type");
        if(mst!=null && mst.equals("move")){
            //移动端登录成功后跳转的地址
            JSONObject json =new JSONObject();
            json.put("resultMsg","fail");
            PrintWriter out = response.getWriter();
            out.write(json.toJSONString());
            out.flush();
            out.close();
        }else{
        HttpSession session = request.getSession();
        session.setAttribute("errorMsg",e.getMessage());
        session.setAttribute("error",true);
        redirectStrategy.sendRedirect(request, response, "/login");
        }
    }
}

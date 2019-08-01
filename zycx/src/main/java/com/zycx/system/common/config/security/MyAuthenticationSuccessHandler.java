package com.zycx.system.common.config.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zycx.system.sys.controller.ExcelManageController;
import com.zycx.system.sys.dao.UserDao;
import com.zycx.system.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义登录成功后跳转到主页
 *
 * @author gly
 * @date 2019/04/16
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserDao userDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功");
        logger.info("username=>" + request.getParameter("username"));
        logger.info("移动端 or PC端访问标示 type=>" + request.getParameter("type"));
        String flag=request.getParameter("type");
        try{
            User user = (User)authentication.getPrincipal();

            if(user != null){

                Date lastLoginDate = user.getLastLoginDate();
                Date beforeLoginDate = new Date();
                //如果没有登录过，当前时间登录为NULL，那么就默认设置最后一次登录时间为当前时间
                if(lastLoginDate != null){

                    //如果登录过，就把上一次的登录时间设置到最后一次登录时间
                    beforeLoginDate = lastLoginDate;
                }
                //每次登录都更新登录时间
                user.setLastLoginDate(new Date());

                userDao.updateLogin(user);

                HttpSession session = request.getSession();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                session.setAttribute("lastLoginTime",sdf.format(beforeLoginDate));
            }
        }catch(Exception e){
            logger.error( "MyAuthenticationSuccessHandler.onAuthenticationSuccess Exception:" + e);
        }
        if(flag!=null && flag.equals("move")){
            //移动端登录成功后跳转的地址
            JSONObject json =new JSONObject();
            json.put("resultMsg"," success");
            PrintWriter out = response.getWriter();
            out.write(json.toJSONString());
            out.flush();
            out.close();
        }else{
            //网页端登录成功后跳转的地址
            redirectStrategy.sendRedirect(request, response, "/main");
        }
    }
}

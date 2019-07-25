package com.zycx.system.common.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * 实现Security的配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    /**
     * 重写PasswordEncoder  接口中的方法，实例化加密策略
     * @return 返回 加密策略
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new CustomPasswordEncoder();
    }

    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;


    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    /**
     * 构建自定义的DaoAuthenticationProvider
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider bean = new DaoAuthenticationProvider();
        bean.setHideUserNotFoundExceptions(false);
        bean.setUserDetailsService(customUserService());
        bean.setPasswordEncoder(passwordEncoder());

        return bean;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.daoAuthenticationProvider());
    }
    /**
     * 描述：csrf().disable()为了关闭跨域访问的限制，若不关闭则websocket无法与后台进行连接
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .loginPage("/login")
                //.defaultSuccessUrl("/toMain")登录成功默认跳转页面
                .failureUrl("/login?error=true")
                //.failureHandler(authenctiationFailureHandler)自定义登录失败处理方法
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login").
                permitAll();
    }

}

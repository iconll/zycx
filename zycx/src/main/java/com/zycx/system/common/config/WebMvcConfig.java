package com.zycx.system.common.config;

import com.zycx.system.common.config.date.DateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * 类描述：springMVC的配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {


    //日期转化器配置
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter());
    }

    /**
     * 重写方法描述：实现在url中输入相应的地址的时候直接跳转到某个地址
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/updatePwd").setViewName("updatePwd");
        // 跳转到菜单管理页面
        registry.addViewController("/treeList").setViewName("sys/tree/treeList");
        // 跳转到角色管理页面
        registry.addViewController("/userRoleList").setViewName("sys/role/roleList");
        // 组织架构页面
        registry.addViewController("/groupList").setViewName("sys/orggroup/groupList");
        // 管理管理页面
        registry.addViewController("/userList").setViewName("sys/user/userList");
        // 数据字典页面
        registry.addViewController("/dictList").setViewName("sys/dict/dictList");
        // 跳转到支行管理页面
        registry.addViewController("/bankInfoList").setViewName("sys/bankInfo/bankInfoList");

        //分期通
        //跳转到分期通基础数据管理页面
        registry.addViewController("/fqtBaseInfoList").setViewName("sys/fqtBaseInfo/fqtBaseInfoList");
        //跳转到分期通额度失效预警页面
        registry.addViewController("/fqtEdsxyjList").setViewName("sys/fqtBaseInfo/fqtEdsxyjList");
        //跳转到分期通基础数据统计页面
        registry.addViewController("/fqtStatisticsList").setViewName("sys/fqtBaseInfo/fqtStatisticsList");

        //家装分期
        //跳转到家装分期基础数据管理页面
        registry.addViewController("/jzBaseInfoList").setViewName("sys/jzBaseInfo/jzBaseInfoList");
        //跳转到家装分期额度有效期预警页面
        registry.addViewController("/jzEdsxyjList").setViewName("sys/jzBaseInfo/jzEdsxyjList");
        //跳转到家装分期基础数据统计页面
        registry.addViewController("/jzStatisticsList").setViewName("sys/jzBaseInfo/jzStatisticsList");
    }
}

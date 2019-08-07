package com.zycx.system.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.zycx.system.common.base.controller.GenericController;
import com.zycx.system.common.base.service.GenericService;
import com.zycx.system.sys.entity.FqtBaseInfo;
import com.zycx.system.sys.entity.QueryFqtBaseInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 外部访问接口
 */
@Controller
@RequestMapping("/PassVisit")
public class PassVisit  {

    @RequestMapping(value = "getStr")
    @CrossOrigin("*")
    public void getStr(PrintWriter out,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String vuestr=request.getParameter("u");
        JSONObject json =new JSONObject();
        json.put("resultMsg","a");
        out = response.getWriter();
        out.write(json.toJSONString());
        out.flush();
        out.close();
    }


}

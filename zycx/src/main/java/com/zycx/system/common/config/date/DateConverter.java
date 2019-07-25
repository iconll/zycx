package com.zycx.system.common.config.date;


import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日期转换器
 * @author gly
 * @date 2019年4月22日
 */
public class DateConverter implements Converter<String, Date> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String s) {
        if(StringUtils.isBlank(s)){

            return null;
        }
        try {
            //日期格式化date
            return sdf.parse(s);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return null;
    }
}

package com.zycx.system.common.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;


/**
 * 文件工具类
 * @author zhao
 * @since 2015-1-29
 */
public class FileUtil {
	
	
	/**
	 * 下载
	 * @param fullname 带全路径的文件名
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public static void download(String fullname,HttpServletResponse response) throws IOException{
		
		File file=new File(fullname);
		String filename = file.getName();
		try {
        	filename= new String(filename.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		response.setContentType("application/octet-stream"); //MIME类型
 
        //该步是最关键的一步，使用setHeader()方法弹出"是否要保存"的对话框，打引号的部分都是固定的值，不要改变   
        response.setHeader("Content-disposition","attachment;filename="+filename);
        response.addHeader("Content-Length", "" + file.length());

        //输出流
        OutputStream out=response.getOutputStream();
        //输入流
        FileInputStream in=new FileInputStream(file);
           
        //设置缓冲区为1024个字节，即1KB   
        byte bytes[]=new byte[1024];
        int len=0;
        //读取数据。返回值为读入缓冲区的字节总数,如果到达文件末尾，则返回-1
        while((len=in.read(bytes))!=-1) 
        { 
            //读多少就写多少  
        	out.write(bytes,0,len);
        }
        out.flush();
        out.close();
        in.close();
	}

}

package com.zycx.system.common.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zycx.system.common.util.exception.ExcelFormatException;
import com.zycx.system.sys.dto.BaseDto;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang.StringUtils;

/**
 * Excel数据读取器
 * 
 * @author zhaoyanqing
 * @since 2010-08-12
 */
public class ExcelReader {
	private String metaData = null;
	private InputStream is = null;
	
	public ExcelReader(){};
	
	/**
	 * 构造函数
	 * @param pMetaData 元数据
	 * @param pIs Excel数据流
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public ExcelReader(String pMetaData, InputStream pIs){
		setIs(pIs);
		setMetaData(pMetaData);
	}
	
	/**
	 * 读取Excel数据
	 * @param pBegin 从第几行开始读数据<br>
	 * <b>注意下标索引从0开始的哦!
	 * @return 以List<BaseDTO>形式返回数据
	 * @throws BiffException
	 * @throws IOException
	 */
	public List read(int pBegin) throws BiffException, IOException{
		List list = new ArrayList();
		Workbook workbook = Workbook.getWorkbook(getIs());
		Sheet sheet = workbook.getSheet(0);
		int rows = sheet.getRows();
		for (int i = pBegin; i < rows; i++) {
			Map<String,Object> rowMap = new HashMap<String,Object>(16);
			Cell[] cells = sheet.getRow(i);
			for (int j = 0; j < cells.length; j++) {
				String key = getMetaData().trim().split(",")[j];
				if(StringUtils.isNotEmpty(key)){

					rowMap.put(key, cells[j].getContents());
				}
			}
			list.add(rowMap);
		}
		return list;
	}
	
	/**
	 * 读取Excel数据
	 * @param pBegin 从第几行开始读数据<br>
	 * <b>注意下标索引从0开始的哦!</b>
	 * @param pBack 工作表末尾减去的行数
	 * @return 以List<Map<String,Object>>形式返回数据
	 * @throws BiffException
	 * @throws IOException
	 */
	public List read(int pBegin, int pBack) throws BiffException, IOException,ExcelFormatException{
		List list = new ArrayList();
		Workbook workbook = Workbook.getWorkbook(getIs());
		Sheet sheet = workbook.getSheet(0);
		int rows = sheet.getRows();
		for (int i = pBegin; i < rows - pBack; i++) {
			Cell[] cells = sheet.getRow(i);
			//不处理多余的空行 by zhao
			if(cells.length == 0){
				continue;
			}
			BaseDto rowMap = new BaseDto();
			String[] arrMeta = getMetaData().trim().split(",");
			//判断Excel中的列数是否和预定好的metaDate列数相同 by duan
			if (cells.length!=arrMeta.length) {
				//不相同则Excel与模板列数不对应 返回list在Action中对List进行判断 by duan
				throw new ExcelFormatException("Excel格式错误，请检查上传的Excel格式。");
			}
			for (int j = 0; j < arrMeta.length; j++) {
				String key = arrMeta[j];
				if(StringUtils.isNotEmpty(key)) {
					rowMap.put(key, cells[j].getContents());
				}
			}
			list.add(rowMap);
		}
		return list;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	};
}

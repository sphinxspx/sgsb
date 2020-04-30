package com.zjhc.sgsb.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
	public static void main(String[] args) throws IOException {
		//System.out.println(dataList.get(0).get(1));	
		
	}
	
	
	
	//根据文件流和文件类型返回LinkedList对象。
	public static LinkedList<LinkedList<Object>> getExcelToList(InputStream is,String type){
		LinkedList<LinkedList<Object>> dataList=new LinkedList<LinkedList<Object>>();
		LinkedList<Object> line=null;
		Workbook wb=readExcel(is, type);
		Sheet sheet = null;
		Row row = null;
		if(wb != null){
			//获取第一个sheet
            sheet = wb.getSheetAt(0);
            row=sheet.getRow(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            //对数据进行解析
            for (int i = 0; i<rownum; i++) {
            	line=new LinkedList<Object>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                    	line.add(getCellFormatValue(row.getCell(j)));
                    }
                }else{
                    break;
                }
                dataList.add(line);
            }
		}
		
		return dataList;
	}
	
	//根据文件流字节和文件类型返回LinkedList对象。
	public static LinkedList<LinkedList<Object>> getExcelToList(byte[] buf,String type){
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);  
		LinkedList<LinkedList<Object>> dataList=new LinkedList<LinkedList<Object>>();
		LinkedList<Object> line=null;
		Workbook wb=readExcel(byteArrayInputStream, type);
		Sheet sheet = null;
		Row row = null;
		if(wb != null){
			//获取第一个sheet
            sheet = wb.getSheetAt(0);
            row=sheet.getRow(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            //对数据进行解析
            for (int i = 0; i<rownum; i++) {
            	line=new LinkedList<Object>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                    	line.add(getCellFormatValue(row.getCell(j)));
                    }
                }else{
                    break;
                }
                dataList.add(line);
            }
		}
		
		return dataList;
	}
	
	//根据文件流字节和文件类型返回LinkedList对象和单元格最大长度。
		public static Map<String,Object> getExcelListAndMaxLength(byte[] buf,String type){
			Map<String,Object> data=new HashMap<String,Object>();
			int maxLength=0;
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);  
			LinkedList<LinkedList<Object>> dataList=new LinkedList<LinkedList<Object>>();
			LinkedList<Object> line=null;
			Workbook wb=readExcel(byteArrayInputStream, type);
			Sheet sheet = null;
			Row row = null;
			if(wb != null){
				//获取第一个sheet
	            sheet = wb.getSheetAt(0);
	            row=sheet.getRow(0);
	            //获取最大行数
	            int rownum = sheet.getPhysicalNumberOfRows();
	            //获取最大列数
	            int colnum = row.getPhysicalNumberOfCells();
	            //对数据进行解析
	            for (int i = 0; i<rownum; i++) {
	            	line=new LinkedList<Object>();
	                row = sheet.getRow(i);
	                if(row !=null){
	                    for (int j=0;j<colnum;j++){
	                    	if(getCellFormatValue(row.getCell(j)).toString().length()>maxLength){
	                    		maxLength=getCellFormatValue(row.getCell(j)).toString().length();
	                    	}
	                    	line.add(getCellFormatValue(row.getCell(j)));
	                    }
	                }else{
	                    break;
	                }
	                dataList.add(line);
	            }
			}
			data.put("length", maxLength);
			data.put("dataList", dataList);
			
			return data;
		}
	
	//根据文件和文件类型返回LinkedList对象。
	public static LinkedList<LinkedList<Object>> getExcelToList(File file,String type) throws IOException{
		LinkedList<LinkedList<Object>> dataList=new LinkedList<LinkedList<Object>>();
		LinkedList<Object> line=null;
		Workbook wb=readExcel(file, type);
		Sheet sheet = null;
		Row row = null;
		if(wb != null){
			//获取第一个sheet
            sheet = wb.getSheetAt(0);
            row=sheet.getRow(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            //对数据进行解析
            for (int i = 0; i<rownum; i++) {
            	line=new LinkedList<Object>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                    	line.add(getCellFormatValue(row.getCell(j)));
                    }
                }else{
                    break;
                }
                dataList.add(line);
            }
		}
		
		return dataList;
	}
	
	//根据文件地址返回LinkedList对象。
	public static LinkedList<LinkedList<Object>> getExcelToList(String filePath) throws IOException{
		LinkedList<LinkedList<Object>> dataList=new LinkedList<LinkedList<Object>>();
		LinkedList<Object> line=null;
		Workbook wb=readExcel(filePath);
		Sheet sheet = null;
		Row row = null;
		if(wb != null){
			//获取第一个sheet
            sheet = wb.getSheetAt(0);
            row=sheet.getRow(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            //对数据进行解析
            for (int i = 0; i<rownum; i++) {
            	line=new LinkedList<Object>();
                row = sheet.getRow(i);
                if(row !=null){
                    for (int j=0;j<colnum;j++){
                    	line.add(getCellFormatValue(row.getCell(j)));
                    }
                }else{
                    break;
                }
                dataList.add(line);
            }
		}
		
		return dataList;
	}
	
	//根据文件流读取excel
	public static Workbook readExcel(InputStream is,String type){
		Workbook wb = null;
		if(is==null){
			return null;
		}
		try {
			if("xls".equals(type)){
				return wb = new HSSFWorkbook(is);
			}else if("xlsx".equals(type)){
				return wb = new XSSFWorkbook(is);
			}else{
				//OPCPackage pkg=OPCPackage.open(is);
				wb=null;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return wb;
	}
	//根据文件和后缀读取excel
	public static Workbook readExcel(File file,String type) throws IOException{
		Workbook wb = null;
        if(file==null){
            return null;
        }
        InputStream is = null;
        try {
			is = new FileInputStream(file);
			if("xls".equals(type)){
				return wb = new HSSFWorkbook(is);
			}else if("xlsx".equals(type)){
				return wb = new XSSFWorkbook(is);
			}else{
				wb = null;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			is.close();
		}
        
        return wb;
	}
	
	//根据文件地址读取excel
    public static Workbook readExcel(String filePath) throws IOException{
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
			is.close();
		}
        return wb;
    }
    
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
        	cell.setCellType(cell.CELL_TYPE_STRING);
        	cellValue = cell.getRichStringCellValue().getString();
            //判断cell类型
            /*switch(cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC:{
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            }
            case Cell.CELL_TYPE_FORMULA:{
                //判断cell是否为日期格式
                if(DateUtil.isCellDateFormatted(cell)){
                    //转换为日期格式YYYY-mm-dd
                    cellValue = cell.getDateCellValue();
                }else{
                    //数字
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            case Cell.CELL_TYPE_STRING:{
                cellValue = cell.getRichStringCellValue().getString();
                break;
            }
            default:
                cellValue = "";
            }*/
        }else{
            cellValue = "";
        }
        return cellValue;
    }
    /**
     * 根据title列表返回一个excel对象
     * @param title
     * @return
     */
    public static HSSFWorkbook createTempExcel(List<Map<String, Object>> title){    	
    	Map<String, Object> map;
    	//创建一个excel对象
    	HSSFWorkbook wb=new HSSFWorkbook();
    	//
    	CellStyle textStyle = wb.createCellStyle();
    	HSSFDataFormat  format = wb.createDataFormat();
    	textStyle.setDataFormat(format.getFormat("@"));
    	//创建一个sheet
    	HSSFSheet sheet = wb.createSheet("Sheet1");
    	sheet.setDefaultColumnWidth(20);
    	//添加表头0行
    	HSSFRow row = sheet.createRow(0);
    	row.setHeightInPoints(20);
    	//创建单元格格式
    	HSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        //声明列对象
        HSSFCell cell = null;
        //创建标题
        for(int i=0;i<title.size();i++){
        	map=title.get(i);
        	cell=row.createCell(i);
        	cell.setCellValue(map.get("col_desc").toString());
        	cell.setCellStyle(style);
        	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        }
        //填充内容
//        row = sheet.createRow(1);
//        for(int i=0;i<title.size();i++){
//        	cell=row.createCell(i);
//        	cell.setCellValue("xxx");
//        	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//        }
        return wb;
    }
    
    
  
}

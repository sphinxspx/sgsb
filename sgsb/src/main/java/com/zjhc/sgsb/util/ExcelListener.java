package com.zjhc.sgsb.util;

//import java.util.LinkedList;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ExcelListener extends AnalysisEventListener{

	private List<Object> datas=new ArrayList<Object>();
	
	private boolean checked=true;
	
	private boolean checkResult=true;
	
	private String checkFlag="1";
	
	private Set<String> cellValues=new HashSet<String>();
	
	private Integer maxLength=255;
	
	private String errorMessage = "";
	
	@Override
	public void doAfterAllAnalysed(AnalysisContext arg0) {
		// TODO Auto-generated method stub
		System.out.println(maxLength);
		
	}

	@Override
	public void invoke(Object arg0, AnalysisContext arg1) {
		// TODO Auto-generated method stub
		if(arg1.getCurrentRowNum()==0&&checked){
			if("1".equals(checkFlag)){
				//严格
				checkTemp(arg0);
			}else{
				//不严格
				makeCellValues(arg0);
			}
			throw new RuntimeException("抛出异常");
		}

		List<String> line=(List<String>) arg0;
		List<String> realLine=new ArrayList<String>();
		for(int i=0;i<cellValues.size();i++){
			if(i>=line.size()){
				realLine.add("");
			}else if(line.get(i)==null){
				realLine.add("");
			}else{
				realLine.add(line.get(i));
			}
			if(realLine.get(i).length()>maxLength){
				maxLength=realLine.get(i).length();
			}
		}
		datas.add(realLine);
	}
	
	public void makeCellValues(Object arg0){
		List<String> temp=(List<String>) arg0;
		for(int i=0;i<temp.size();i++){
			if(temp.get(i)!=null){
				cellValues.add(temp.get(i));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void checkTemp(Object arg0){
		List<String> temp=(List<String>) arg0;
		for(int i=0;i<temp.size();i++){
			System.out.println("=================");
			System.out.print(i);
			System.out.print(temp.get(i));
			System.out.println();
			if(i<cellValues.size()&&temp.get(i)==null){
				errorMessage = "存在空字段";
				System.out.println("有null");
				this.checkResult=false;
				break;
			}else if(i<cellValues.size()&&temp.get(i)!=null){
				if(!cellValues.contains(temp.get(i).toString())){
					errorMessage = "模板中不存在\""+temp.get(i).toString()+"\"字段";
					System.out.println("没有");
		 			this.checkResult=false;
						break;
				}
				System.out.println("有");
			}else if(i>=cellValues.size()&&temp.get(i)!=null){
				errorMessage = "字段超出模板范围";
				System.out.println("超出不算");
				this.checkResult=false;
				break;
			}
			System.out.println("=================");
 		}
	}

	public List<Object> getDatas() {
        return datas;
    }
	
    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

	public boolean isCheckResult() {
		return checkResult;
	}

	public void setCheckResult(boolean checkResult) {
		this.checkResult = checkResult;
	}

	public Set<String> getCellValues() {
		return cellValues;
	}

	public void setCellValues(Set<String> cellValues) {
		this.cellValues = cellValues;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
    
    
}

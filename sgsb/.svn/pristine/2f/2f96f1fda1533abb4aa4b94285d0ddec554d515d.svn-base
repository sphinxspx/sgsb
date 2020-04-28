package com.zjhc.sgsb.temp;

import java.util.LinkedList;

public class OracleSqlTemp {
	
	public static String returnSequenceSql(String tableName){
		StringBuilder str=new StringBuilder();
		str.append("CREATE SEQUENCE seq_").append(tableName)
			.append(" increment by 1 start with 1 nomaxvalue minvalue 1 NOCYCLE");
		
		return str.toString();
	}
	
	public static String returnTriggerSql(String tableName){
		StringBuilder str=new StringBuilder();
		str.append("CREATE OR REPLACE TRIGGER trig_").append(tableName)
			.append(" BEFORE INSERT ON ").append(tableName).append(" FOR EACH ROW")
			.append(" BEGIN select seq_").append(tableName).append(".nextval into :new.excel_id from dual;")
			.append(" END");
		
		return str.toString();
	}
	
	public static String returnCreateSql(String tableName,LinkedList<LinkedList<Object>> excel){
		StringBuilder str=new StringBuilder();
		str.append("CREATE TABLE ").append(tableName).append("( id NUMBER,");
		for(int i=1;i<excel.size();i++){
			LinkedList<Object> ele = excel.get(i);
			if ("bigint".equals(ele.get(2).toString()) || "decimal".equals(ele.get(2).toString())){
				str.append(ele.get(0).toString()).append(" number").append("(10, ").append(ele.get(4)).append("), ");
			}else {
				str.append(ele.get(0).toString()).append(" NVARCHAR2(").append(ele.get(2)).append(" (").append(ele.get(3)).append("), ");
			}
			str.append("), ");
		}
		str.append("insert_date TIMESTAMP,PRIMARY KEY (id) )");

		return str.toString();
	}
	
}

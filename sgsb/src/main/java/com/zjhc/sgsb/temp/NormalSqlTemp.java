package com.zjhc.sgsb.temp;

import java.util.LinkedList;

public class NormalSqlTemp {
	
	public static String dropTableSql(String tableName,String dbType){
		StringBuilder str=new StringBuilder();
		if("Oracle".equals(dbType)){
			str.append("drop table ").append(tableName);
		}else{
			str.append("drop table if exists ").append(tableName);
		}
		
		
		return str.toString();
	}
	
	public static String dropSequenceSql(String tableName){
		StringBuilder str=new StringBuilder();
		str.append("drop sequence seq_").append(tableName);
		
		return str.toString();
	}
	
	public static String dropTriggerSql(String tableName){
		StringBuilder str=new StringBuilder();
		str.append("drop trigger trig_").append(tableName);
		
		return str.toString();
	}
	
	public static String CreateTableSql(String tableName,String dbType,LinkedList<LinkedList<Object>> colEname){
		if("Oracle".equals(dbType)){
			return OracleSqlTemp.returnCreateSql(tableName, colEname);
		}else if("MySQL".equals(dbType)){
			return MysqlSqlTemp.returnCreateSql(tableName, colEname);
		}
		return "";
	}
}

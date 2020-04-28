package com.zjhc.sgsb.temp;

import java.util.LinkedList;

public class MysqlSqlTemp {
	
	public static String returnCreateSql(String tableName,LinkedList<LinkedList<Object>> excel){
		StringBuilder str=new StringBuilder();
		str.append("create table ").append(tableName).append("( id bigint(20) AUTO_INCREMENT,");
		for(int i=1;i<excel.size();i++){
			LinkedList<Object> ele = excel.get(i);
			str.append(ele.get(0).toString()).append(" ").append(ele.get(2)).append(" (").append(ele.get(3));
			if ("decimal".equals(ele.get(2).toString())){
				str.append(",").append(ele.get(4));
			}
			str.append("), ");
		}
		str.append("insert_date datetime,primary key (id) )")
			.append("ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8");

		return str.toString();
	}
}

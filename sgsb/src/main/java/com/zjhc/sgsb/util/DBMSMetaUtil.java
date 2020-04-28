package com.zjhc.sgsb.util;


import com.zjhc.sgsb.exception.ApplicationException;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库信息工具类
 * @author charlesXu
 *
 */
public class DBMSMetaUtil {
    /**==============数据库语句判别正则 start=================**/

    /**
     * 来源表解析正则表达式：SELECT
     */
    public static Pattern selectPattern = Pattern.compile("\\s+(from|join)\\s+((\\w+\\.){0,}(\\w)+)", Pattern.CASE_INSENSITIVE);

    /**
     * 目标表解析正则表达式:INSERT
     */
    public static Pattern insertPattern = Pattern.compile("(insert\\s+into)\\s+((\\w+\\.){0,}(\\w)+)", Pattern.CASE_INSENSITIVE);

    /**
     * 解析删除操作：DELETE
     */
    public static Pattern deletePattern = Pattern.compile("(insert\\s+into)\\s+((\\w+\\.){0,}(\\w)+)", Pattern.CASE_INSENSITIVE);

    /**
     * 解析更新操作：
     */
    public static Pattern updatePattern = Pattern.compile("(insert\\s+into)\\s+((\\w+\\.){0,}(\\w)+)", Pattern.CASE_INSENSITIVE);

    public static final Map<String,Pattern> sqlPattern = new HashMap(){{
        put("selectPattern",selectPattern);
        put("insertPattern",insertPattern);
        put("deletePattern",deletePattern);
        put("updatePattern",updatePattern);
    }};

    /**
     * 获取sql匹配的pattern
     * @param str
     * @return
     */
    public static Matcher matchPattern(String str){
        Iterator<Map.Entry<String,Pattern>> it = sqlPattern.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Pattern> entry = it.next();
            Matcher matcher = entry.getValue().matcher(str);
            if (matcher.matches()){
                return matcher;
            }
        }
        return null;
    }
    /**==============数据库语句判别正则 end=================**/

    /**
	   * 数据库类型 枚举类
	   * 	   oracle:1521   
//     *       mysql:3306  
//	   *	   sqlserver: 1433
       *       DB2:5000
	   *	   PostgreSQL：5432
	   * 
	   */
	 public static enum DATABASETYPE {
		 ORACLE, MYSQL, SQLSERVER, DB2, INFOMIX, SYBASE, OTHER, EMPTY
	 }
    //数据库对象类型
    public final static Map<String,String> DB_OBJECTS_TYPE = new HashMap<String,String>(){{
        put("TABLE","TABLE");
        put("VIEW","VIEW");
        put("PROC","PROC");
        put("TRIGGER","TRIG");
        }
    };

    public static enum DB_OBJ_TYPE {
        TABLE,VIEW,PROC,TRIG;
    }

    /**
     * 数据库驱动
     */
    public static final String MySQLdriver= "com.mysql.jdbc.Driver";
    public static final String Orcldriver = "oracle.jdbc.driver.OracleDriver";
	 
	 /**
	  * 根据字符串，判断数据库类型
	  * @param databasetype
	  * @return
	  */
	 public static DATABASETYPE parseDATABASETYPE(String databasetype){
		if (databasetype == null || databasetype.trim().length() < 1) {
			return DATABASETYPE.EMPTY;
		} 
		//截断首尾空格，转换为大写
		databasetype = databasetype.trim().toUpperCase();
		//ORACLE
		if (databasetype.contains("ORACLE")) {
			return DATABASETYPE.ORACLE;
		}
		//MYSQL
		if (databasetype.contains("MYSQL")) {
			return DATABASETYPE.MYSQL;
		}
		//SQLServer
		if (databasetype.contains("SQLSERVER")) {
			return DATABASETYPE.SQLSERVER;
		}
		return DATABASETYPE.OTHER;
	 }

	 /**
	  * 数据库连接url拼接
	  * @param types
	  * @param ip
	  * @param port
	  * @param insName 
	  * @return
	  */
	 public static String concatDBURL(DATABASETYPE types, String ip, String port, String insName){
			String url = "";
			//oracle数据库
			if (DATABASETYPE.ORACLE.equals(types)) {
                /*try {
                    Class.forName(Orcldriver);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }*/
                url += "jdbc:oracle:thin:@";
				url += ip.trim();
                url += ":" + port.trim();
				url += ":" + insName;

				//如果需要采用 hotbackup
				String url2 = "";  
	            url2 = url2+"jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = "  
	                    + ip.trim() +")(PORT ="+ port.trim() +")))(CONNECT_DATA = (SERVICE_NAME ="+insName+  
	                    ")(FAILOVER_MODE = (TYPE = SELECT)(METHOD = BASIC)(RETRIES = 180)(DELAY = 5))))";  
	            //  
	            // url = url2;  
	        } else if (DATABASETYPE.MYSQL.equals(types)) {  
	            //  
	            url += "jdbc:mysql://";  
	            url += ip.trim();  
	            url += ":" + port.trim();  
	            url += "/" + insName;  
	        } else if (DATABASETYPE.SQLSERVER.equals(types)) {  
	            //  
	            url += "jdbc:jtds:sqlserver://";  
	            url += ip.trim();  
	            url += ":" + port.trim();  
	            url += "/" + insName;  
	            url += ";tds=8.0;lastupdatecount=true";  
	        } else if (DATABASETYPE.SYBASE.equals(types)) {  
	            url += "jdbc:sybase:Tds:";  
	            url += ip.trim();  
	            url += ":" + port.trim();  
	            url += "/" + insName;  
	        } else {  
	            throw new RuntimeException("不认识的数据库类型!");  
	        }  
	        //  
	        return url;
		}

	 /**
		 * 获取jdbc连接
		 * @param url
		 * @param username
		 * @param password
		 * @return
		 */
		 public static Connection getConnection(String url, String username, String password){
			Connection conn = null;
			Properties prop = new Properties();
			
			prop.put("user", username);
			prop.put("password", password);
			//Oracle如果需要获取元数据，需要此数据
			prop.put("remarksReporting", "true");
			//masql标志位，获取TABLE元数据REMARKS信息
			prop.put("useInformationSchema", "true");
			if(url.contains("oracle")){
				try {
                    Class.forName(Orcldriver);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
			}else{
				url+="?characterEncoding=UTF-8";
				 try {
					Class.forName(MySQLdriver);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			try {
				conn = DriverManager.getConnection(url, prop);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (url.contains("oracle") && conn == null) {
				
				StringBuilder url_sb = new StringBuilder(url);
				url_sb.replace(17, 18, "@//");
			 	int index = url_sb.lastIndexOf(":");
				url_sb.replace(index, index + 1, "/");
				System.out.println(url_sb.toString());
				try {
					conn = DriverManager.getConnection(url_sb.toString(), prop);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
            if (null == conn){throw new ApplicationException("数据库连接失败");}
			return conn;	
		 }


		/**
		 * 将一个未处理的ResultSet解析为Map列表
		 * @param rs
		 * @return
		 */
		public static List<Map<String, Object>> parseResultSetToMapList(ResultSet rs){
			
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			
			if (rs == null) {
				return null;
			}
			try {
				while (rs.next()) {
					Map<String, Object> map = parseResultSetToMap(rs);
					if (map != null) {
						result.add(map);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		
		/**
		 * 解析ResultSet的单条记录，不进行ResultSet的next处理
		 * @param rs
		 * @return
		 */
		private static Map<String, Object> parseResultSetToMap(ResultSet rs){
			if (rs == null) {
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			
			try {
				ResultSetMetaData meta = rs.getMetaData();
				int colNum = meta.getColumnCount();
				for (int i = 1; i <= colNum; i++) {
					//列名
				    String name = meta.getColumnLabel(i);
				    Object value = rs.getObject(i);
				    //加入属性
				    map.put(name, value);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map;
			
		}
	 /**
	  * 测试连接
	  * @param type
	  * @param ip
	  * @param port
	  * @param dbname
	  * @param username
	  * @param password
	  * @return
	  */
	 public static boolean TryLink(String type, String ip, String port, String dbname, String username, String password){
			
			DATABASETYPE types = parseDATABASETYPE(type);
			String url = concatDBURL(types, ip, port, dbname);
			Connection conn = null;
			conn = getConnection(url, username, password);
			if (conn == null) {
				return false;
			}
			try {
				DatabaseMetaData meta = conn.getMetaData();
				if (meta == null) {
					return false;
				}else {
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				close(conn);
			}
			return false;
		}
	 public static String trim(String str){
		 if (str != null) {
			str = str.trim();
		}
		return str;
		 
	 }
	 
	 
	 /**
	  * 关闭资源方法
	  */
	 public static void close(ResultSet rs){
		 if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 }
	 
	 public static void close(Statement stmt){
		 if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 }
	 //关闭connection
	 public static void close(Connection conn){
		 if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 }

/*    *//**
     * 根据数据库类型、对象类型、对象名，获取DDL语句
     * @param types
     * @param objType
     * @param objName
     * @return
     *//*
    public static String fetchDDL(DATABASETYPE types,String objType,String objName){
        String sql = null;
        if ("".equals(StrUtil.formatNull(objName)) || "".equals(StrUtil.formatNull(objType))){
            return "";
        }
        if (types.equals(DATABASETYPE.ORACLE)){
            switch (objType){
                case "VIEW":
                    sql = "SELECT TEXT AS DDL FROM USER_VIEWS WHERE upper(VIEW_NAME) = '"+objName.toUpperCase()+"'";

            }
        }else if (types.equals(DATABASETYPE.MYSQL)){
            switch (objType){
                case "VIEW":
                    sql = "SELECT VIEW_DEFINITION AS DDL FROM information_schema.VIEWS " +
                            "where upper(TABLE_NAME) ='"+objName.toUpperCase()+"'";
            }
        }
        return sql;

    }*/


    /**
     * 带库名的表名去除库名,返回表名
     * @param tableName
     * @return
     */
    public static Object splitSchemaFromTablename(String tableName){
        //mysql中存在`等字符，需要剔除
        tableName = tableName.replace("`","");

        if (tableName.indexOf(".") < 0 ){
            return tableName;
        }

        //存放结果:schema\table
        Map map = new HashMap();
        String[] names = tableName.split("\\.");
        //带库名的表名去除库名
        if (names.length == 2){
            map.put("schema",names[0]);
            map.put("table",names[1]);
        }
        return map;
    }

    /**
     * 从表名中返回库名称
     * @param tableName
     * @return
     */
    public static String splitSchema(String tableName){
        //mysql中存在`等字符，需要剔除
        tableName = tableName.replace("`","");

        if (tableName.indexOf(".") < 0 ){
            return "";
        }else {
            String[] names = tableName.split("\\.");
            return names[0];
        }
    }

    /**
     * 带库名的表名去除库名,返回表名
     * @param tableName
     * @return
     */
    public static String splitTable(String tableName){
        //mysql中存在`等字符，需要剔除
        tableName = tableName.replace("`","");

        if (tableName.indexOf(".") < 0 ){
            return tableName;
        }else {
            String[] names = tableName.split("\\.");
            return names[1];
        }
    }
}

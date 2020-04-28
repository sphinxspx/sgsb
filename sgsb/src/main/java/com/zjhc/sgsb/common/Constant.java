package com.zjhc.sgsb.common;

import com.zjhc.sgsb.util.DBMSMetaUtil;

import java.sql.Connection;

public class Constant {

    public static final String SUPER_USER_DEPT_NAME = "超级管理员";

    public static final String SUPER_USER_DEPT_CODE = "administrator";

    public static final String EXCEL_FILE_TYPE = "xls";

    public static final String TEMPLATE_PATH = "http://localhost:8181/download/table.xls";

//    public static final String DOWNLOAD_EXCEl_URL = "http://172.24.4.159:6080/clouddiskM-webapp/api/clouddisk/downloadFile?type=F&infoid=";

    public interface RESOURCE_DATASOURCE{
        String URL = "jdbc:mysql://172.24.4.180:3306/sgsb_resource_data";
        String USERNAME = "root";
        String PASSWORD = "root";
    }

    public interface FIELD_MAX {//各个字段的最大长度
        String DECIMAL = "65";
        String VARCHAR = "10000";
        String BIGINT = "255";
        String TEXT = "10000000";
        String ORACLE_NUMBER = "10";
    }

    public interface TEMP_TYPE {//模板字段类型
        String DECIMAL = "DECIMAL";
        String VARCHAR = "VARCHAR";
        String BIGINT = "BIGINT";
        String TEXT = "TEXT";
    }


}

package com.zjhc.sgsb.common;

import java.io.Serializable;

public class InterfaceResult<T> implements Serializable {

    private boolean issuccess;
    private String msg;
    private T data;

    private Long totalCount;
    private Long pageCount;


    public InterfaceResult(boolean issuccess, String msg) {
        this.issuccess = issuccess;
        this.msg = msg;
    }

    public InterfaceResult(boolean issuccess, String msg, T data) {
        this.issuccess = issuccess;
        this.msg = msg;
        this.data = data;
    }

    public InterfaceResult(boolean issuccess, Long totalCount, Long pageCount, T data) {
        this.issuccess = issuccess;
        this.totalCount = totalCount;
        this.pageCount = pageCount;
        this.data = data;
    }

    public boolean issuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public static <T> InterfaceResult<T> getSuccess(String description){
        return new InterfaceResult<T>(true,description);
    }

    public static <T> InterfaceResult<T> getSuccess(String description,T data){
        return new InterfaceResult<T>(true,description,data);
    }

    public static <T> InterfaceResult<T> getSuccess(Long totalCount, Long pageCount, T data){
        return new InterfaceResult<T>(true,totalCount,pageCount,data);
    }

    public static <T> InterfaceResult<T> getError(String description){
        return new InterfaceResult<T>(false,description);
    }

    public static <T> InterfaceResult<T> hasExist(Integer hasExist,String successMsg,String errorMsg, T data){
        if (hasExist > 0){
            return InterfaceResult.getSuccess(successMsg,data);
        }
        return InterfaceResult.getError(errorMsg);
    }
}

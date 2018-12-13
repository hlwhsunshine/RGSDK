package com.annotion.ruiyi.rgsdk;

/**
 * Function:
 * Project:RGSDK
 * Date:2018/12/13
 * Created by xiaojun .
 */

public class Result {
    /* renamed from: a */
    public int code;
    /* renamed from: b */
    public String successMsg;
    /* renamed from: c */
    public String errorMsg;

    public Result(int code, String successMsg, String errorMsg) {
        this.code = code;
        this.successMsg = successMsg;
        this.errorMsg = errorMsg;
    }

    public final String toString() {
        return "CommandResult{result=" + this.code + ", successMsg='" + this.successMsg + '\'' + ", errorMsg='" + this.errorMsg + '\'' + '}';
    }
}
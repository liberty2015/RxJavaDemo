package com.behavior.liberty.rxjavademo.bean;

import java.io.Serializable;

/**
 * Created by liberty on 2019/1/8.
 */

public class BaseBean implements Serializable {

    private String resultcode;
    private String reason;

    public String getReason() {
        return reason;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }
}

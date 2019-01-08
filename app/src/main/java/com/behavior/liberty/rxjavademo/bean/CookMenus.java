package com.behavior.liberty.rxjavademo.bean;

import java.util.List;

/**
 * Created by liberty on 2019/1/8.
 */

public class CookMenus extends BaseBean {


    private Data result;
    private int error_code;

    public Data getResult() {
        return result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setResult(Data result) {
        this.result = result;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public class Data {
        private List<CookMenu> data;
        private String totalNum;
        private String pn;
        private String rn;

        public List<CookMenu> getData() {
            return data;
        }

        public String getTotalNum() {
            return totalNum;
        }

        public String getPn() {
            return pn;
        }

        public String getRn() {
            return rn;
        }

        public void setData(List<CookMenu> data) {
            this.data = data;
        }

        public void setPn(String pn) {
            this.pn = pn;
        }

        public void setRn(String rn) {
            this.rn = rn;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }
    }
}

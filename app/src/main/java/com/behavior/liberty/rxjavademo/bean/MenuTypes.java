package com.behavior.liberty.rxjavademo.bean;

import java.util.List;

/**
 * Created by liberty on 2019/1/8.
 */

public class MenuTypes extends BaseBean {

    private List<MenuType> result;

    public List<MenuType> getResult() {
        return result;
    }

    public void setResult(List<MenuType> result) {
        this.result = result;
    }

    public class MenuType implements ItemWrapper{
        private String parentId;
        private String name;
        private List<Menu> list;

        public String getParentId() {
            return parentId;
        }

        public String getName() {
            return name;
        }

        public List<Menu> getList() {
            return list;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setList(List<Menu> list) {
            this.list = list;
        }
    }
}

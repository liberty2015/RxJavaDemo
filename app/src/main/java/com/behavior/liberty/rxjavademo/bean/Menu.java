package com.behavior.liberty.rxjavademo.bean;

import java.io.Serializable;

/**
 * Created by liberty on 2019/1/8.
 */

public class Menu implements Serializable,ItemWrapper {

    private String id;
    private String name;
    private String parentId;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParentId() {
        return parentId;
    }
}

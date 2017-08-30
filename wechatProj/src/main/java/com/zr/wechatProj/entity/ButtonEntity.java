package com.zr.wechatProj.entity;

/**
 * @author zourui On 17:02 2017/8/30
 * @version 1.0
 *          菜单按钮基类
 */
public class ButtonEntity {
    //按钮名称
    private String name;
    //按钮类型
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.laowengs.vuedashboard.vo;

import java.util.ArrayList;
import java.util.List;

public class MenuInfo{
    private Long menuId;
    private String menuName;
    private List<MenuInfo> childrenMenuList;

    public MenuInfo(Long menuId, String menuName) {
        this.menuId = menuId;
        this.menuName = menuName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<MenuInfo> getChildrenMenuList() {
        return childrenMenuList;
    }

    public void setChildrenMenuList(List<MenuInfo> childrenMenuList) {
        this.childrenMenuList = childrenMenuList;
    }

    public void addChildMenu(MenuInfo menuInfo){
        if(menuInfo != null){
            if(childrenMenuList == null){
                childrenMenuList = new ArrayList<>();
            }
            childrenMenuList.add(menuInfo);
        }
    }

    @Override
    public String toString() {
        return "MenuInfo{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", childrenMenuList=" + childrenMenuList +
                '}';
    }
}
package com.laowengs.vuedashboard.controller;

import com.laowengs.vuedashboard.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @RequestMapping("/getMenuInfo")
    public Result<List<MenuInfo>> getMenuInfo(){
        List<MenuInfo> menuInfos = new ArrayList<>();
        MenuInfo rootMenuInfo = new MenuInfo(0);
        MenuInfo exampleMenuInfo = new MenuInfo(1);
        exampleMenuInfo.addChildMenu(new MenuInfo(11));
        exampleMenuInfo.addChildMenu(new MenuInfo(12));

        MenuInfo formMenuInfo = new MenuInfo(2);
        formMenuInfo.addChildMenu(new MenuInfo(21));
        MenuInfo nestedMenuInfo = new MenuInfo(3);
        MenuInfo menu1MenuInfo = new MenuInfo(31);
        MenuInfo menu1_1MenuInfo = new MenuInfo(311);
        MenuInfo menu1_2MenuInfo = new MenuInfo(312);
//        MenuInfo menu1_2_1MenuInfo = new MenuInfo(3121);
        MenuInfo menu1_2_2MenuInfo = new MenuInfo(3122);
//        menu1_2MenuInfo.addChildMenu(menu1_2_1MenuInfo);
        menu1_2MenuInfo.addChildMenu(menu1_2_2MenuInfo);

        MenuInfo menu1_3MenuInfo = new MenuInfo(313);

        menu1MenuInfo.addChildMenu(menu1_1MenuInfo);
        menu1MenuInfo.addChildMenu(menu1_2MenuInfo);
        menu1MenuInfo.addChildMenu(menu1_3MenuInfo);

        MenuInfo menu2MenuInfo = new MenuInfo(32);
        nestedMenuInfo.addChildMenu(menu1MenuInfo);
        nestedMenuInfo.addChildMenu(menu2MenuInfo);

        menuInfos.add(rootMenuInfo);
        menuInfos.add(exampleMenuInfo);
        menuInfos.add(formMenuInfo);
        menuInfos.add(nestedMenuInfo);
        MenuInfo e = new MenuInfo(4);
        e.addChildMenu(new MenuInfo(41));
        menuInfos.add(e);


        return Result.getInstance(0,"success",menuInfos);
    }
}
class MenuInfo{
    private Integer menuId;
    private List<MenuInfo> childrenMenuList = new ArrayList<>();

    public MenuInfo(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public List<MenuInfo> getChildrenMenuList() {
        return childrenMenuList;
    }

    public void setChildrenMenuList(List<MenuInfo> childrenMenuList) {
        this.childrenMenuList = childrenMenuList;
    }

    public void addChildMenu(MenuInfo menuInfo){
        childrenMenuList.add(menuInfo);
    }

    @Override
    public String toString() {
        return "MenuInfo{" +
                "menuId='" + menuId + '\'' +
                ", childrenMenuList=" + childrenMenuList +
                '}';
    }
}

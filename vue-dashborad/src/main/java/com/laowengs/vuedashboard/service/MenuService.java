package com.laowengs.vuedashboard.service;

import com.laowengs.vuedashboard.vo.MenuInfo;
import com.laowengs.vuedashboard.vueadmindb.dao.VuePermissionDao;
import com.laowengs.vuedashboard.vueadmindb.dao.VueRolePermissionDao;
import com.laowengs.vuedashboard.vueadmindb.dao.VueUserRoleDao;
import com.laowengs.vuedashboard.vueadmindb.po.VuePermission;
import com.laowengs.vuedashboard.vueadmindb.po.VueRolePermission;
import com.laowengs.vuedashboard.vueadmindb.po.VueUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MenuService {
    private VuePermissionDao permissionDao;
    private VueUserRoleDao userRoleDao;
    private VueRolePermissionDao rolePermissionDao;

    @Autowired
    public MenuService(VuePermissionDao permissionDao, VueUserRoleDao userRoleDao, VueRolePermissionDao rolePermissionDao) {
        this.permissionDao = permissionDao;
        this.userRoleDao = userRoleDao;
        this.rolePermissionDao = rolePermissionDao;
    }

    public List<MenuInfo> selectAllMenuInfoBySystemId(Long userId,Long systemId) {
        List<MenuInfo> menuInfos = new ArrayList<>();
        VuePermission vuePermission = permissionDao.selectByPrimaryKey(systemId);
        if (vuePermission != null) {
            Set<Long> permissionSet = qryUserPermission(userId);
            List<VuePermission> permissions = permissionDao.selectByParentPermissionId(vuePermission.getPermissionId());
            for (VuePermission permission : permissions) {
                MenuInfo menuInfo = buildAllMenuInfo(permission,permissionSet);
                if(menuInfo != null){
                    menuInfos.add(menuInfo);
                }
            }
        }
        return menuInfos;
    }

    private MenuInfo buildAllMenuInfo(VuePermission vuePermission, Set<Long> permissionSet) {
        if (vuePermission != null) {
            Long permissionId = vuePermission.getPermissionId();
            MenuInfo menuInfo = new MenuInfo(permissionId, vuePermission.getPermissionName(), permissionSet.contains(permissionId) ? 1 : 0);
            List<VuePermission> permissions = permissionDao.selectByParentPermissionId(permissionId);
            for (VuePermission permission : permissions) {
                MenuInfo childMenuInfo = buildAllMenuInfo(permission,permissionSet);
                if(childMenuInfo != null){
                    menuInfo.addChildMenu(childMenuInfo);
                }
            }
            return menuInfo;
        }
        return null;
    }

    public List<MenuInfo> selectMenuInfoByUserId(Long userId,Long systemId){
        List<MenuInfo> menuInfos = new ArrayList<>();
        Set<Long> permissionSet = qryUserPermission(userId);
        VuePermission vuePermission = permissionDao.selectByPrimaryKey(systemId);
        if (vuePermission != null) {
            List<VuePermission> permissions = permissionDao.selectByParentPermissionId(vuePermission.getPermissionId());
            for (VuePermission permission : permissions) {
                if(permissionSet.contains(permission.getPermissionId())){
                    MenuInfo menuInfo = buildMenuInfo(permission,permissionSet);
                    if(menuInfo != null){
                        menuInfos.add(menuInfo);
                    }
                }
            }
        }
        return menuInfos;
    }

    private Set<Long> qryUserPermission(Long userId) {
        Set<Long> permissionSet = new HashSet<>();
        List<VueUserRole> vueUserRoles = userRoleDao.selectByUserId(userId);
        if(vueUserRoles != null && !vueUserRoles.isEmpty()){
            for (VueUserRole vueUserRole : vueUserRoles) {
                List<VueRolePermission> vueRolePermissions = rolePermissionDao.selectByRoleId(vueUserRole.getRoleId());
                if(vueRolePermissions != null && !vueRolePermissions.isEmpty()){
                    for (VueRolePermission vueRolePermission : vueRolePermissions) {
                        permissionSet.add(vueRolePermission.getPermissionId());
                    }
                }
            }
        }
        return permissionSet;
    }

    private MenuInfo buildMenuInfo(VuePermission vuePermission, Set<Long> permissionSet) {
        if (vuePermission != null) {
            MenuInfo menuInfo = new MenuInfo(vuePermission.getPermissionId(),vuePermission.getPermissionName());
            List<VuePermission> permissions = permissionDao.selectByParentPermissionId(vuePermission.getPermissionId());
            for (VuePermission permission : permissions) {
                if(permissionSet.contains(permission.getPermissionId())){
                    MenuInfo childMenuInfo = buildMenuInfo(permission,permissionSet);
                    if(childMenuInfo != null){
                        menuInfo.addChildMenu(childMenuInfo);
                    }
                }
            }
            return menuInfo;
        }
        return null;
    }

}

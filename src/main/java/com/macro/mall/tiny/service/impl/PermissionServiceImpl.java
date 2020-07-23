package com.macro.mall.tiny.service.impl;

import com.macro.mall.tiny.dto.PermissionDetails;
import com.macro.mall.tiny.dto.RoleDetails;
import com.macro.mall.tiny.mbg.mapper.UmsPermissionMapper;
import com.macro.mall.tiny.mbg.mapper.UmsRoleMapper;
import com.macro.mall.tiny.mbg.mapper.UmsRolePermissionRelationMapper;
import com.macro.mall.tiny.mbg.model.*;
import com.macro.mall.tiny.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private UmsPermissionMapper mapper;
    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private UmsPermissionMapper permissionMapper;
    @Autowired
    private UmsRolePermissionRelationMapper relationMapper;


    @Override
    public List<UmsPermission> getAllPermissions() {
        List<UmsPermission> umsPermissions = mapper.selectByExample(new UmsPermissionExample());
        umsPermissions.remove(0);
        umsPermissions.remove(umsPermissions.size() - 1);
        return umsPermissions;
    }

    @Override
    public List<RoleDetails> getAllRoles() {
        List<UmsRole> umsRoles = roleMapper.selectByExample(new UmsRoleExample());
        ArrayList<Long> ids = new ArrayList<>();
        umsRoles.forEach(role -> {
            ids.add(role.getId());
        });
        List<List<PermissionDetails>> permissionLists = new ArrayList<>();
        for (Long id : ids) {
            UmsRolePermissionRelationExample relationExample = new UmsRolePermissionRelationExample();
            relationExample.createCriteria().andRoleIdEqualTo(id);
            List<UmsRolePermissionRelation> umsRolePermissionRelations = relationMapper.selectByExample(relationExample);
            ArrayList<Long> permissionIds = new ArrayList<>();
            umsRolePermissionRelations.forEach(item -> {
                permissionIds.add(item.getPermissionId());
            });
            UmsPermissionExample permissionExample = new UmsPermissionExample();
            permissionExample.createCriteria().andIdIn(permissionIds);
            List<UmsPermission> umsPermissions = permissionMapper.selectByExample(permissionExample);
            List<PermissionDetails> permission2Tree = getPermission2Tree(umsPermissions);
            permissionLists.add(permission2Tree);
        }
        ArrayList<RoleDetails> roleDetailsList = new ArrayList<>();
        for (int i = 0; i < umsRoles.size(); i++) {
            roleDetailsList.add(new RoleDetails(umsRoles.get(i), permissionLists.get(i)));
        }
        return roleDetailsList;
    }

    @Override
    public int removePermission(Long roleId, Long permissionId) {
        UmsRolePermissionRelationExample example = new UmsRolePermissionRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andPermissionIdEqualTo(permissionId);
        int i = relationMapper.deleteByExample(example);
        return i;
    }

    @Override
    public List<PermissionDetails> getPermissionTypeTree() {
        List<UmsPermission> umsPermissions = permissionMapper.selectByExample(new UmsPermissionExample());
        List<PermissionDetails> permissionDetails = getPermission2Tree(umsPermissions);
        return permissionDetails;
    }

    @Override
    public List<PermissionDetails> getPermission2Tree(List<UmsPermission> umsPermissions) {
        //        去掉pid为0的permission
        for (int i = 0; i < umsPermissions.size(); i++) {
            if (umsPermissions.get(i).getPid() == 0) {
                umsPermissions.remove(i);
                break;
            }
        }
//        先获取type=1的Permissions
        List<UmsPermission> umsPermissionsF = new ArrayList<>();
        ArrayList<PermissionDetails> permissionDetails = new ArrayList<>();
        for (int i = 0; i < umsPermissions.size(); i++) {
            if (umsPermissions.get(i).getType() == 1) {
                UmsPermission permission = umsPermissions.get(i);
                umsPermissionsF.add(permission);
                permissionDetails.add(new PermissionDetails(permission));
            }
        }
//        获取type=2的permission，并封装
        for (int i = 0; i < umsPermissionsF.size(); i++) {
            for (int j = 0; j < umsPermissions.size(); j++) {
                if (umsPermissions.get(j).getPid() == umsPermissionsF.get(i).getId()) {
                    permissionDetails.get(i).getChildren().add(new PermissionDetails(umsPermissions.get(j)));
                }
            }
        }
        return permissionDetails;
    }

    @Override
    public int addPermissionForRole(Long roleId, List<Long> permissionIds) {
        int sum = 0;
        for (Long pid : permissionIds) {
            UmsRolePermissionRelation relation = new UmsRolePermissionRelation();
            relation.setRoleId(roleId);
            relation.setPermissionId(pid);
            relationMapper.insert(relation);
            sum++;
        }
        return sum;
    }


}

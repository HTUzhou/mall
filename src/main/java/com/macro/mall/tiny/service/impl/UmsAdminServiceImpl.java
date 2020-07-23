package com.macro.mall.tiny.service.impl;

import com.macro.mall.tiny.common.utils.JwtTokenUtil;
import com.macro.mall.tiny.dao.UmsAdminRoleRelationDao;
import com.macro.mall.tiny.dto.AsideListResult;
import com.macro.mall.tiny.mbg.mapper.AsideListMapper;
import com.macro.mall.tiny.mbg.mapper.UmsAdminMapper;
import com.macro.mall.tiny.mbg.model.*;
import com.macro.mall.tiny.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AsideListMapper asideListMapper;

    /**
     * 用于密码的验证
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 用于操作UmsAdmin表
     */
    @Autowired
    private UmsAdminMapper adminMapper;

    /**
     * 用于获取用户的权限列表
     */
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;

    /**
     * 通过用户名获得对应的管理员
     * @param username
     * @return
     */
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(example);
        if (umsAdmins != null && umsAdmins.size() > 0) {
            return umsAdmins.get(0);
        }
        return null;
    }

    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        //将传递过来的参数属性copy到另一个对象上
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        return umsAdmin;
    }

    /**
     * 通过调用userDetailsService的loadUserByUsername方法，返回一个userDetail
     * 调用passwordEncoder的matches方法，来验证密码是否正确
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            //将userDetails和userDetails的权限列表封装成一个authentication对象
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //将authentication放进SecurityContextHolder的上下文中
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //根据userDetails生成token值
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        List<UmsPermission> permissionList = adminRoleRelationDao.getPermissionList(adminId);
        System.out.println(permissionList);
        return permissionList;
    }

    @Override
    public List<AsideListResult> getAsideList() {
        List<AsideList> asideLists = asideListMapper.selectByExample(new AsideListExample());
        List<AsideListResult> asideListResults = new ArrayList<>();
        for (AsideList asideList : asideLists) {
            if (asideList.getParentid() == 0) {
                AsideListResult asideListResult = new AsideListResult(asideList.getId(), asideList.getAuthname(), asideList.getParentid(), asideList.getPath(), new ArrayList<>());
                asideListResults.add(asideListResult);
            }
        }
        for (AsideListResult asideListResult : asideListResults) {
            for (AsideList asideList : asideLists) {
                if (asideList.getParentid() == asideListResult.getId()) {
                    asideListResult.getChildren().add(asideList);
                }
            }
        }
        return asideListResults;
    }

    @Override
    public List<UmsAdmin> getUsers() {
        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(new UmsAdminExample());
        return umsAdmins;
    }

    @Override
    public int modifyStatus(Long id, String status) {
        int count = 0;
        if (status.equals("true")) {
            UmsAdmin admin = adminMapper.selectByPrimaryKey(id);
            admin.setStatus(1);
            count = adminMapper.updateByPrimaryKey(admin);
        } else {
            UmsAdmin admin = adminMapper.selectByPrimaryKey(id);
            admin.setStatus(0);
            count = adminMapper.updateByPrimaryKey(admin);
        }
        return count;
    }

    @Override
    public List<UmsAdmin> getUsersLikeName(String username) {
//        UmsAdminExample example = new UmsAdminExample();
//        example.createCriteria().andUsernameLike(username);
//        List<UmsAdmin> umsAdmins = adminMapper.selectByExample(example);
        List<UmsAdmin> umsAdmins = adminMapper.selectLikeUsername(username);
        System.out.println("---------------------------------");
        System.out.println(username);
        System.out.println("模糊查询的数据为：" + umsAdmins);
        return umsAdmins;
    }

    @Override
    public UmsAdmin getUserById(Long id) {
        UmsAdmin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public int modifyUser(Long id, String email) {
        UmsAdmin admin = adminMapper.selectByPrimaryKey(id);
        admin.setEmail(email);
        int i = adminMapper.updateByPrimaryKey(admin);
        return i;
    }

    @Override
    public int deleteUser(Long id) {
        int i = adminMapper.deleteByPrimaryKey(id);
        return i;
    }
}

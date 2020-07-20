package com.macro.mall.tiny.config;

import com.macro.mall.tiny.component.JwtAuthenticationTokenFilter;
import com.macro.mall.tiny.component.RestAuthenticationEntryPoint;
import com.macro.mall.tiny.component.RestfulAccessDeniedHandler;
import com.macro.mall.tiny.dto.AdminUserDetails;
import com.macro.mall.tiny.mbg.model.UmsAdmin;
import com.macro.mall.tiny.mbg.model.UmsPermission;
import com.macro.mall.tiny.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * SpringSecurity的配置
 */
@Configuration
// 启用Web安全的注解
@EnableWebSecurity
// 开启注解模式
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.csrf()
                .disable() //禁用csrf跨站请求伪造
                .sessionManagement() //获取session管理器，然后调用sessionCreationPolicy
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //SessionCreationPolicy.STATELESS:永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ioc",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                )
                .permitAll()
                .antMatchers("/admin/login", "/admin/register")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest() // 其他请求需要进行验证
                .permitAll();
//                .authenticated();
        //禁用缓存
        http.headers().cacheControl();
        //添加JWT filter
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//        添加自定义的未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    /**
     * 设置默认的UserDetailService 和 passwordEncoder
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 密码编码器，定义对密码的比对方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 获取用户的登录信息，封装成一个userDetail对象
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                System.out.println("调用UserDetailsService");
                UmsAdmin admin = adminService.getAdminByUsername(username);
                if (admin != null) {
                    List<UmsPermission> permissionList = adminService.getPermissionList(admin.getId());
                    return new AdminUserDetails(admin,permissionList);
                }
                throw new UsernameNotFoundException("用户名或密码错误");
            }
        };
    }

    /**
     * JWT登录授权过滤器，将其注册进容器中
     * @return
     */
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    /**
     * 用户认证管理器，提供用户认证功能
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}

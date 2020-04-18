package org.feather.common.security;



import lombok.extern.slf4j.Slf4j;
import org.feather.common.constants.Constants;
import org.feather.user.entity.UserElement;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


@Slf4j
public class TokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {



    private AntPathMatcher matcher = new AntPathMatcher();

    private List<String> noneSecurityList;


    public TokenAuthenticationFilter(List<String> noneSecurityPath) {
        this.noneSecurityList = noneSecurityPath;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        GrantedAuthority[] authorities = new GrantedAuthority[1];
        //无需权限的url直接授权
        if(isNoneSecurity(request.getRequestURI().toString()) || "OPTIONS".equals(request.getMethod())){
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_SOMEONE");
            authorities[0] = authority;
            return new TokenAuthentication(Arrays.asList(authorities));
        }
        //token检查
        String token = request.getHeader(Constants.REQUEST_TOKEN_HEADER);
        //检查APP版本
//        String version = request.getHeader(Constants.REQUEST_VERSION_KEY);
//        if (version == null) {
//            request.setAttribute("header-error", 400);
//        }
        //校验token
        if (request.getAttribute("header-error") == null) {
            try {
                if (token != null && !token.trim().isEmpty()) {
                    //从springsession中获取user
                    UserElement ue = (UserElement) request.getSession().getAttribute(Constants.REQUEST_USER_SESSION);
                    if (ue instanceof UserElement) {
                        //检查到token说明用户已经登录 授权给用户 允许访问
                        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
                        authorities[0] = authority;
                        TokenAuthentication authToken = new TokenAuthentication(Arrays.asList(authorities));
                        return authToken;
                    } else {
                        //token不对
                        request.setAttribute("header-error", 401);
                    }
                } else {
                    log.warn("Got no token from request header");
                    //token不存在 告诉移动端 登录
                    request.setAttribute("header-error", 401);
                }
            } catch (Exception e) {
                log.error("Fail to authenticate user", e);
            }

        }

        if(request.getAttribute("header-error") != null){
            //请求头有错误  随便给个角色 让逻辑继续
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_NONE");
            authorities[0] = authority;
        }
        TokenAuthentication authToken = new TokenAuthentication(Arrays.asList(authorities));
        return authToken;
    }


    /**
     * 校验是否是无需权限的url
     * @param uri
     * @return
     */
    private boolean isNoneSecurity(String uri) {
        boolean result = false;
        if (this.noneSecurityList != null) {
            for (String pattern : this.noneSecurityList) {
                if (matcher.match(pattern, uri)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }


    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpServletRequest) {
        return null;
    }
}

package org.feather.common.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;


public class TokenAuthenticationManager  implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        return AuthenticationProvider.authenticate();
        TokenAuthentication tokenAuthentication = (TokenAuthentication)authentication.getPrincipal();
        if (tokenAuthentication.getAuthorities() != null && tokenAuthentication.getAuthorities().size() > 0) {
            GrantedAuthority gauth = tokenAuthentication.getAuthorities().iterator().next();
            if ("ROLE_SOMEONE".equals(gauth.getAuthority())) {
                tokenAuthentication.setAuthenticated(true);
                return tokenAuthentication;
            }else if ("ROLE_USER".equals(gauth.getAuthority())) {
                tokenAuthentication.setAuthenticated(true);
                return tokenAuthentication;
            }
        }

        throw new BadCredentialException("unknown.error");
    }
}

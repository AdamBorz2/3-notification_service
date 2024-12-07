package com.itm.space.notificationservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    public Collection<? extends GrantedAuthority> getCurrentUserRoles() {

        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    public List<String> getUserRoles() {

        Collection<? extends GrantedAuthority> authorities = getCurrentUserRoles();

        if (authorities != null && !authorities.isEmpty()) {
            return authorities.stream()
                    .map(Object::toString)
                    .toList();
        }
        return new ArrayList<>();
    }

    public UUID getUserId() {

        return UUID.fromString(((UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername());
    }
}
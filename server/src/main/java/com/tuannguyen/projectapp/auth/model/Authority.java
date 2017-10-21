package com.tuannguyen.projectapp.auth.model;

import com.tuannguyen.projectapp.auth.entity.AccessLevel;
import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
    private AccessLevel accessLevel;

    public Authority(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public String getAuthority() {
        return accessLevel.getName();
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        return accessLevel != null ? accessLevel.equals(authority.accessLevel) : authority.accessLevel == null;
    }

    @Override
    public int hashCode() {
        return accessLevel != null ? accessLevel.hashCode() : 0;
    }
}

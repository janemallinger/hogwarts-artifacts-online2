package edu.tcu.cs.hogwartsartifactsonline2.hogwartsuser;

import org.springframework.util.StringUtils;

import static com.fasterxml.jackson.databind.type.LogicalType.Array;

public class MyUserPrincipal implements UserDetails {

    private HogwartsUser hogwartsUser;

    public MyUserPrincipal(HogwartsUser hogwartsUser) {
        this.hogwartsUser = hogwartsUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Array.stream(StringUtils.tokenizeToStringArray(this.hogwartsUser.getRoles(), " "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.hogwartsUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.hogwartsUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.hogwartsUser.isEnabled();
    }

    public HogwartsUser getHogwartsUser() {
        return hogwartsUser;
    }

}

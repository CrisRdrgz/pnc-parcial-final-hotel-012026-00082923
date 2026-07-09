package com.uca.pncparcialfinalhotel.security;

import com.uca.pncparcialfinalhotel.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String nombreUsuario;
    private final String password;
    private final String rol;
    private final Long sucursalId;

    public UserPrincipal(Long id, String nombreUsuario, String password, String rol, Long sucursalId) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
        this.sucursalId = sucursalId;
    }

    public static UserPrincipal from(Usuario usuario) {
        return new UserPrincipal(
                usuario.getId(), usuario.getNombreUsuario(), usuario.getPassword(),
                usuario.getRol().name(), usuario.getSucursal() != null ? usuario.getSucursal().getId() : null
        );
    }

    public Long getId() { return id; }
    public String getRol() { return rol; }
    public Long getSucursalId() { return sucursalId; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol));
    }

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return nombreUsuario; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
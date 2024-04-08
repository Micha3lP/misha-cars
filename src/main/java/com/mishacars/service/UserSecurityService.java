package com.mishacars.service;

import com.mishacars.persistence.entity.UserEntity;
import com.mishacars.persistence.entity.UserRoleEntity;
import com.mishacars.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UserSecurityService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity usuario = this.usuarioRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));

        System.out.println(usuario);

        String[] roles = usuario.getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(this.grantedAuthorities(roles))
                .accountLocked(usuario.isLocked())
                .disabled(usuario.isDisabled())
                .build();
    }
    public UserEntity save(UserEntity usuario) {
        return usuarioRepository.save(usuario);
    }

    private String[] getAuthorities(String role) {
        if ("ADMIN".equals(role)) {
            return new String[] {"get-all"};
        }
        return new String[] {};
    }

    private List<GrantedAuthority> grantedAuthorities(String[] roles) {

        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

            for (String authority : this.getAuthorities(role)) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }

        return authorities;
    }



}

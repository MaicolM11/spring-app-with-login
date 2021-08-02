package com.uptc.models.entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullname;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")) 
    private Collection<Role> roles;

    private Boolean enabled = false;

    public User(String fullname, String email, String password){
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    public static UserDetails build(User u) {
        return new org.springframework.security.core.userdetails.User
                        (u.email, u.password, u.enabled,true, true, true, u.getAuthorities());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(x-> x.getRole().toString())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        return authorities;
    }

}

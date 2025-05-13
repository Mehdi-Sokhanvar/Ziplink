package com.ziplink.model;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;




@Table(name = "users")
@Entity
public class User extends BaseEntity<Long> implements UserDetails  {


    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Url> urls;

    private Double Credit;

    public User() {

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    private User(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
        this.urls = builder.urls;
        this.Credit = builder.credit;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String email;
        private String password;
        private Role role = Role.USER;
        private List<Url> urls = new ArrayList<>();
        private Double credit = 0.0;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder urls(List<Url> urls) {
            this.urls = urls;
            return this;
        }

        public Builder credit(Double credit) {
            this.credit = credit;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

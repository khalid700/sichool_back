package com.sichool.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Document(collection = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Account implements UserDetails
{
    @Id
    private String id;
    @Indexed
    private String email;
    @JsonIgnore
    private String password;
    private String fullName;
    private HashMap<String , Object> data;
    private UserRoles role;
    private boolean status;
    public Account(@Nullable @JsonProperty("email") String email,
                   @Nullable@JsonProperty("password") String password,
                   @Nullable@JsonProperty("full_name") String fullName,
                   @Nullable @JsonProperty("data") HashMap<String , Object> data,
                   @Nullable  @JsonProperty("role") UserRoles role)
    {
         this.id  = UUID.randomUUID().toString();
         this.email = email;
         this.role = role;
         this.data = data;
         this.password = password;
         this.fullName = fullName;
         this.status = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
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
        return status;
    }
}

package com.mobileserver.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role  implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name="role_name")
    private String roleName;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "roles")
    List<User> users = new ArrayList<>();

    public void setUsers(List<User> users){
        this.users=users;
    }
    public List<User> getUsers(){
        return this.users;
    }
    public Role(){}

    @Override
    public String getAuthority() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName(){
        return this.roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
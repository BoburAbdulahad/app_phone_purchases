package uz.bob.app_phone_purchases.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.bob.app_phone_purchases.entity.enums.Permission;
import uz.bob.app_phone_purchases.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@EntityListeners(value = AuditingEntityListener.class)
public class User extends AbstractEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Role role;

    private boolean enabled;
    private boolean credentialsNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean accountNonExpired=true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Permission> rolePermissionList = this.role.getPermissionList();
        Set<GrantedAuthority> grantedAuthorities=new HashSet<>();
        for (Permission permission : rolePermissionList) {
            grantedAuthorities.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return permission.name();
                }
            });
        }
        return grantedAuthorities;
    }

    public User(String fullName, String username, String password, Role role, boolean enabled) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }
}

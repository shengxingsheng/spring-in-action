package tacos.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author sxs
 * @Date 2023/8/4 17:34
 */
@Entity(name = "users")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class User implements UserDetails {
    public static final long serialVersionUID = 42L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private final String username;
    private final String password;
    private final String fullname;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String phoneNumber;
    private final String roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] split = roles.split(";");
        if (split.length == 0) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(split).stream().map(role -> new SimpleGrantedAuthority(role)).toList();
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
        return true;
    }
}

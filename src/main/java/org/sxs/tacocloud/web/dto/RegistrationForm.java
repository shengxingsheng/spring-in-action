package org.sxs.tacocloud.web.dto;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.sxs.tacocloud.domain.entity.User;

/**
 * @Author sxs
 * @Date 2023/8/4 20:31
 */
@Data
public class RegistrationForm {
    private final String username;
    private final String password;
    private final String fullname;
    private final String street;
    private final String city;
    private final String state;
    private final String zip;
    private final String phoneNumber;

    public User toUser(PasswordEncoder encoder) {

        return new User(username, encoder.encode(password), fullname, street, city, state, zip, phoneNumber);

    }
}

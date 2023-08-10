package org.sxs.tacocloud;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


class TacoCloudApplicationTests {


    public static void main(String[] args) {
        System.out.println(LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY));
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_5());
        encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v4_1());
        encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_2());
        encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("sha256", new StandardPasswordEncoder());

        PasswordEncoder passwordEncoder =
                new DelegatingPasswordEncoder(idForEncode, encoders);
        System.out.println(passwordEncoder.encode("password"));


        PasswordEncoder passwordEncoder1 =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.println(passwordEncoder.encode("password"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(14);
        long startTime = System.nanoTime();
        String result = encoder.encode("myPassword");
        long elapsedNanos = System.nanoTime() - startTime;
        System.out.println(TimeUnit.NANOSECONDS.toMillis(elapsedNanos) + "ms");
        System.out.println(result);
    }


    @Test
    public void testArgon2() {
//        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(
//                32, 64, 4, 1 << 16, 15);
        PasswordEncoder encoder = passwordEncoder();
        String result = encoder.encode("myPassword");
        System.out.println(result);
        long startTime = System.nanoTime();
        boolean b = encoder.matches("myPassword", result);
        long elapsedNanos = System.nanoTime() - startTime;
        System.out.println(b + ":" + TimeUnit.NANOSECONDS.toMillis(elapsedNanos) + "ms");
    }

    @Test
    public void testArgon3() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 14);

        String result = encoder.encode("myPassword");
        System.out.println(result);
        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            boolean b = encoder.matches("myPassword", result);
            long elapsedNanos = System.nanoTime() - startTime;
            System.out.println(b + ":" + TimeUnit.NANOSECONDS.toMillis(elapsedNanos) + "ms");
        }
    }

    public PasswordEncoder passwordEncoder() {
        String encodingId = "argon2@v1";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2@v1", new Argon2PasswordEncoder(32, 64, 4, 1 << 16, 15));
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    @Test
    void test1() {
        String roles = "role";
        String[] split = roles.split(";");
        if (split[0].isEmpty()) {
            System.out.println(Collections.EMPTY_LIST);
            return;
        }
        System.out.println(Arrays.asList(split).stream().map(role -> new SimpleGrantedAuthority(role)).toList());
    }
}

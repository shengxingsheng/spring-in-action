package org.sxs.tacocloud;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;

@SpringBootTest
class TacoCloudApplicationTests {


    public static void main(String[] args) {
        System.out.println(LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY));
    }
}

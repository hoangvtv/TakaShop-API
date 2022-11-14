package com.example.takaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        TakaApiApplication.class,
        Jsr310JpaConverters.class
})
public class TakaApiApplication {
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC+7"));
    }

    public static void main(String[] args) {
        SpringApplication.run(TakaApiApplication.class, args);
    }

}

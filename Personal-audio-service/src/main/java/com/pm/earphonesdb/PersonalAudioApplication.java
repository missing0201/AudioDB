package com.pm.earphonesdb;

import com.pm.audiodbsecurity.EnableAudioDBSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAudioDBSecurity
@SpringBootApplication
public class PersonalAudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalAudioApplication.class, args);
    }

}

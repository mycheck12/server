package com.micheck12.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Micheck12Application {

  public static void main(String[] args) {
    SpringApplication.run(Micheck12Application.class, args);
  }

}

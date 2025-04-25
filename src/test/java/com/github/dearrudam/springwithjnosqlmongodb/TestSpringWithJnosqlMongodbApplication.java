package com.github.dearrudam.springwithjnosqlmongodb;

import org.springframework.boot.SpringApplication;

public class TestSpringWithJnosqlMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.from(TestSpringWithJnosqlMongodbApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}

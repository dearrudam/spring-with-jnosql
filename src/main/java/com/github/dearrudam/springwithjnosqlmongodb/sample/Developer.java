package com.github.dearrudam.springwithjnosqlmongodb.sample;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.util.UUID;

@Entity
public record Developer(
        @Id
        String id,
        @Column
        String name) {

    static Developer of(String name) {
        return new Developer(UUID.randomUUID().toString(), name);
    }

    public Developer updateName(String name) {
        return new Developer(id, name);
    }
}

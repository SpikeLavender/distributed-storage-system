package com.natsumes.demo.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

import java.util.Set;

@Data
@NodeEntity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Property("cid")
    private int pid;

    @Property
    private String name;

    private String character;

    private double money;

    private int gender;

    private int age;

    private String description;

    @Relationship(type = "Friends",direction = Relationship.INCOMING)
    private Set<Person> relationPersons;

    public Person() {
    }

    public Person(int pid, String name, String character, double money, int gender, int age, String description) {
        this.pid = pid;
        this.name = name;
        this.character = character;
        this.money = money;
        this.gender = gender;
        this.age = age;
        this.description = description;
    }
}

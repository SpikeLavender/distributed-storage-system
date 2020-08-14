package com.natsumes.demo.service.impl;

import com.natsumes.demo.ApplicationTest;
import com.natsumes.demo.entity.Person;
import com.natsumes.demo.service.Neo4jPersonService;
import com.natsumes.demo.utils.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
public class Neo4jPersonServiceImplTest extends ApplicationTest {

    @Autowired
    private Neo4jPersonService neo4jPersonService;

    @Before
    public void before() {
        List<Person> peoples = new ArrayList<>();
        Person person1 = new Person();
        person1.setName("贾宝玉");
        person1.setMoney(2500);
        peoples.add(person1);

        Person person2 = new Person();
        person1.setName("柳湘莲");
        person1.setMoney(1800);
        peoples.add(person2);

        neo4jPersonService.save(peoples);
    }

    @Test
    public void personAll() {
        List<Person> people = neo4jPersonService.personAll();
        log.info(JsonFormat.printFormat(people));
    }

    @Test
    public void personList() {
        List<Person> people = neo4jPersonService.personList(1800);
        log.info(JsonFormat.printFormat(people));
    }

    @Test
    public void shortestPath() {
        List<Person> people = neo4jPersonService.shortestPath("贾宝玉", "林黛玉");
        log.info(JsonFormat.printFormat(people));
    }
}
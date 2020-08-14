package com.natsumes.demo.service;

import com.natsumes.demo.entity.Person;

import java.util.List;

public interface Neo4jPersonService {

    List<Person> personList(double money);

    List<Person> shortestPath(String startName, String endName);

    void save(List<Person> person);

    List<Person> personAll();

}

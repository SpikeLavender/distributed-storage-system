package com.natsumes.demo.service.impl;

import com.natsumes.demo.entity.Person;
import com.natsumes.demo.repository.PersonRepository;
import com.natsumes.demo.service.Neo4jPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class Neo4jPersonServiceImpl implements Neo4jPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> personList(double money) {
        return personRepository.personList(money);
    }

    @Override
    public List<Person> shortestPath(String startName, String endName) {
        return personRepository.shortestPath(startName, endName);
    }

    @Override
    public void save(List<Person> person) {
        personRepository.saveAll(person);
    }

    @Override
    public List<Person> personAll() {
        Iterable<Person> all = personRepository.findAll();
        List<Person> peoples = new ArrayList<>();
        all.forEach(peoples::add);
        return peoples;
    }
}

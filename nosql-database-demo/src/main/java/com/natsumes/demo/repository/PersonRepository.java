package com.natsumes.demo.repository;

import com.natsumes.demo.entity.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    @Query("match (p:Person) where p.money > {money} return p")
    List<Person> personList(@Param("money") double money);

    @Query("match p = shortestPath((p1:Person {name: {startName}})-[*1..4]-(p2:Person {name: {endName}})) return p")
    List<Person> shortestPath(@Param("startName") String startName, @Param("endName") String endName);
}

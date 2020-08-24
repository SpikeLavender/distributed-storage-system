package com.natsumes.demo.repository;

import com.natsumes.demo.entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

    List<UserInfo> findByName(String name);

    List<UserInfo> findByNameAndCity(String name, String city);

}

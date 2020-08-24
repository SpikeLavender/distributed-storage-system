package com.natsumes.demo.repository;

import com.natsumes.demo.MongoApplicationTest;
import com.natsumes.demo.entity.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UserInfoRepositoryTest extends MongoApplicationTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    public void testFind() {
        List<UserInfo> all = userInfoRepository.findAll();
        System.out.println(all);
    }

    @Test
    public void testInsert() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("natsumes");
        userInfo.setCity("xian");
        userInfo.setId("1234567");
        UserInfo insert = userInfoRepository.insert(userInfo);
        System.out.println(insert);
    }

    @Test
    public void testFindByName() {
        List<UserInfo> all = userInfoRepository.findByName("natsumes");
        System.out.println(all);
    }

    @Test
    public void testFindByNameAndCity() {
        List<UserInfo> all = userInfoRepository.findByNameAndCity("natsumes", "xian");
        System.out.println(all);
    }
}
package com.natsumes.demo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("user_info")
public class UserInfo {

    private String id;

    private String name;

    private String city;
}

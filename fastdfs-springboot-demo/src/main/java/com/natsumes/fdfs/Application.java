package com.natsumes.fdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;


@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
//只需要一行注解就可以拥有带有连接池的FastDFS Java客户端了
@Import(FdfsClientConfig.class)
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

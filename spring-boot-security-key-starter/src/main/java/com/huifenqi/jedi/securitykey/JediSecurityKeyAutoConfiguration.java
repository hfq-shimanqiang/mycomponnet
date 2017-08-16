package com.huifenqi.jedi.securitykey;

import com.huifenqi.jedi.securitykey.service.SecurityKeyService;
import com.huifenqi.jedi.securitykey.service.SecurityKeyServiceImpl;
import com.huifenqi.jedi.securitykey.shell.ShellClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ShellClient.class})
@EnableConfigurationProperties(MyConf.class)
public class JediSecurityKeyAutoConfiguration {

    @Autowired
    MyConf myConf;


    @Bean
    SecurityKeyService securityKeyService() {
        return new SecurityKeyServiceImpl(myConf);
    }
}

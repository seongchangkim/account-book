package com.account.accountbook.config;

import com.account.accountbook.common.CommonControllerFunc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public CommonControllerFunc commonControllerFunc(){
        return new CommonControllerFunc();
    }
}

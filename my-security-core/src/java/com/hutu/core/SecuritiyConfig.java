package com.hutu.core;

import com.hutu.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecuritiyConfig {
}

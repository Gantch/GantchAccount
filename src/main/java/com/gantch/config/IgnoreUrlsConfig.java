package com.gantch.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcw332
 * Date 2019-12-26-11:40
 * Description:  nbiot-management , com.gantch.nbiotmanagement.config
 * 用于配置不需要保护的资源路径
 **/

@Setter
@Getter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}

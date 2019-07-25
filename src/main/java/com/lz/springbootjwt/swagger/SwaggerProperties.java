package com.lz.springbootjwt.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lzj
 * @create 2019-07-24 19:12
 */
@ConfigurationProperties(prefix = "swagger")
@Data
@Component
public class SwaggerProperties {
    private String groupName;
    private String basePackage;
    private String title;
    private String version;
    private String desc;
}

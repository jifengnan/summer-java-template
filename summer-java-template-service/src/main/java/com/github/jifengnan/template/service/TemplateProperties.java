package com.github.jifengnan.template.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Provides some public dynamic properties for the project.
 *
 * @author jifengnan  2018-12-04
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "template")
public class TemplateProperties {

    private String aTemplateProperty;
}

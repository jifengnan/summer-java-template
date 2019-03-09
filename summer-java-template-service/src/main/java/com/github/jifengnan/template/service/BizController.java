package com.github.jifengnan.template.service;

import com.github.jifengnan.template.base.model.TemplateHttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jifengnan  2019-03-09
 */


@RestController
@RequestMapping("/biz")
class BizController {

    @GetMapping("method")
    public TemplateHttpResult method() {
        return TemplateHttpResult.createForSuccess(HttpStatus.OK.value(), properties.getATemplateProperty());
    }

    @Autowired
    public BizController(TemplateProperties properties) {
        Assert.notNull(properties, "properties cannot be null");
        this.properties = properties;
    }

    private TemplateProperties properties;
}
package com.example.series.series.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/end-point/")
class TestController {

    final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @GetMapping("test-point")
    public String getValue() {
        LOG.info("Got GET request");
        return "TEST HELLO";
    }
}
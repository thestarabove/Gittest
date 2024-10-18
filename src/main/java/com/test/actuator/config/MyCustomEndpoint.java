//package com.test.actuator.config;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
//import org.springframework.boot.actuate.endpoint.Endpoint;
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class MyCustomEndpoint implements Endpoint<Map<String, Object>> {
//
//    @Autowired
//    private HealthIndicator diskSpaceHealthIndicator;
//
//
//
//    @Override
//    public String getId() {
//        return "customhealth";
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//
//    @Override
//    public boolean isSensitive() {
//        return false;
//    }
//
//    @Override
//    public Map<String, Object> invoke() {
//        Health health = diskSpaceHealthIndicator.health();
//        Map<String, Object> details = new HashMap<>();
//        details.put("status", health.getStatus().toString());
//        details.put("details", health.getDetails());
//        return details;
//    }
//}

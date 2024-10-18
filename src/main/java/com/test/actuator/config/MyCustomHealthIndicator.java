//package com.test.actuator.config;
//
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MyCustomHealthIndicator implements HealthIndicator {
//
//    @Override
//    public Health health() {
//        // 这里添加自定义健康检查逻辑
//        // 比如检查数据库连接，第三方服务等
//        boolean healthy = checkSomething();
//
//        if (healthy) {
//            return Health.up().withDetail("myService", "Available").build();
//        } else {
//            return Health.down().withDetail("myService", "Not Available").build();
//        }
//    }
//
//    private boolean checkSomething() {
//        // 自定义的健康检查逻辑
//        return true;
//    }
//}

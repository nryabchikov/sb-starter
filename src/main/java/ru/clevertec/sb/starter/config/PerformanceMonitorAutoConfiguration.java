package ru.clevertec.sb.starter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.sb.starter.aop.PerformanceMonitorAspect;

@Configuration
@EnableConfigurationProperties(PerformanceMonitorProperties.class)
@ConditionalOnClass(PerformanceMonitorProperties.class)
@ConditionalOnProperty(prefix = "monitoring.performance.starter", name = "enabled", havingValue = "true")
public class PerformanceMonitorAutoConfiguration {

    @Bean
    public PerformanceMonitorProperties performanceMonitorProperties() {
        return new PerformanceMonitorProperties();
    }

    @Bean
    @ConditionalOnExpression("${monitoring.performance-enabled:false}")
    public PerformanceMonitorAspect performanceMonitorAspect() {
        return new PerformanceMonitorAspect(performanceMonitorProperties());
    }
}

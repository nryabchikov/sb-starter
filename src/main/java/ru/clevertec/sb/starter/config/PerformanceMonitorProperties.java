package ru.clevertec.sb.starter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "monitoring")
public class PerformanceMonitorProperties {
    private boolean enabled;

    private boolean performanceEnabled;
    private int minTime;
}

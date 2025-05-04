# Spring Boot Starter - Performance Monitor

## Описание

`spring-boot-starter-monitor-performance` — это Spring Boot Starter, предоставляющий аннотацию `@MonitorPerformance` для мониторинга времени выполнения методов. Стартер логирует методы, если их выполнение превышает заданный порог времени.

## Установка

Соберите стартер с помощью команды:

```bash
./gradlew clean build
```

Чтобы сделать стартер доступным для вашего проекта, выполните команду:

```bash
./gradlew publishToMavenLocal
```
Стартер будет опубликован в локальном Maven-репозитории (~/.m2/repository).

В build.gradle вашего проекта добавьте:
```
repositories {
    mavenLocal() 
}

dependencies {
    implementation 'ru.clevertec:spring-boot-starter-monitor-performance:0.0.1-SNAPSHOT'
}
```

Настройка
Добавьте следующие параметры в application.yml вашего проекта:

```yaml
monitoring:
  performance:
    starter:
      enabled: true # Включение/выключение стартера
  min-time: 200 # Минимальное время выполнения метода для логирования (в миллисекундах).
  performance-enabled: true # Включение или выключение мониторинга.
```

Чтобы мониторить метод, просто добавьте аннотацию @MonitorPerformance. Пример кода:

``` java
package ru.clevertec.sb.starter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExampleController {
    private final ExampleService exampleService;

    @GetMapping("/hello")
    public String helloWorld() throws InterruptedException {
        return exampleService.getHelloMessage();
    }
}

package ru.clevertec.sb.starter;

import org.springframework.stereotype.Service;
import ru.clevertec.sb.starter.aop.MonitorPerformance;

@Service
public class ExampleService {

    @MonitorPerformance
    public String getHelloMessage() throws InterruptedException {
        Thread.sleep(300);
        return "Hello, World!";
    }
}
```
## Проверка
Запустите приложение с настройками мониторинга. Вызовите метод, помеченный аннотацией @MonitorPerformance. Если метод выполняется дольше указанного порога (min-time), вы увидите лог:
```
2024-12-13T18:23:55.009+03:00  INFO 88165 --- [nio-8080-exec-1] r.c.s.s.aop.PerformanceMonitorAspect     : Method [String ru.clevertec.sb.starter.ExampleService.getHelloMessage()] executed in [305 ms]
```
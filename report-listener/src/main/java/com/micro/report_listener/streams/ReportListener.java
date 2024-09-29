package com.micro.report_listener.streams;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class ReportListener {
    public Consumer<String> consumer(){
        return report -> {
            log.info(report);
        };
    }
}

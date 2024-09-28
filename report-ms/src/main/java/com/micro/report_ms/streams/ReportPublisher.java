package com.micro.report_ms.streams;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReportPublisher {
    private final StreamBridge streamBridge;

    public void publishReport(String report){
        this.streamBridge.send("ConsumerReport", report);
        this.streamBridge.send("ConsumerReport-in-0", report);
        this.streamBridge.send("ConsumerReport-out-0", report);
    }
}

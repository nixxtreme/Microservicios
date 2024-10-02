package com.micro.micro_crud.configs;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Order(999)
@Slf4j
public class LogObservationHandler implements ObservationHandler<Observation.Context> {

    @Override
    public void onStart(Observation.Context context) {
        log.info("LogObservationHandler::onStart:{}", context.getName());
    }

    @Override
    public void onError(Observation.Context context) {
        ObservationHandler.super.onError(context);
    }

    @Override
    public void onStop(Observation.Context context) {
        ObservationHandler.super.onStop(context);
    }

    @Override
    public boolean supportsContext(Observation.Context context) {
        return true;
    }
}

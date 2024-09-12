package com.micro.report_ms.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadBalancerConfiguration {
    public ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context){
        log.info("Configurando balanceador de carga");
        return ServiceInstanceListSupplier.builder().withBlockingDiscoveryClient().build(context);
    }
}

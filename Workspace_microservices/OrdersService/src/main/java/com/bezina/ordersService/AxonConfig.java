package com.bezina.ordersService;

import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(basePackages = {"com.bezina"})
public class AxonConfig {

    @Bean
    @Primary
    public Serializer xStreamSerializer() {
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[] { "com.bezina.**" });

        return XStreamSerializer.builder()
                .xStream(xStream)
                .build();
    }
}

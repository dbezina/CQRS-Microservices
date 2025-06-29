package com.bezina.ProductService.command.rest;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/products/management")
public class EventsReplayController {
    @Autowired
    private EventProcessingConfiguration eventProcessingConfiguration;
    @PostMapping("/eventProcessor/{processorName}/reset")
    public ResponseEntity<String> replayEvents(@PathVariable String processorName){
        Optional<TrackingEventProcessor> trackingEventProcessor =
                eventProcessingConfiguration.eventProcessor(processorName,TrackingEventProcessor.class);
        if(trackingEventProcessor.isPresent()){
            TrackingEventProcessor eventProcessor = trackingEventProcessor.get();
            eventProcessor.shutDown();
            eventProcessor.resetTokens();//here the products will be deleted
            eventProcessor.start(); // to call replay events

            return ResponseEntity.ok()
                    .body(String.format("The event processor with name [%s] has been reset",
                    processorName));
        }else {
            return ResponseEntity.badRequest()
                    .body(String.format("The event processor with name [%s] is not tracking event processor"
                            , processorName));
        }
    }
}

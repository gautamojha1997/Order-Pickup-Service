package com.example.orderpickup.service.kafka;

import com.example.orderpickup.dtos.PickupDto;
import com.example.orderpickup.service.pickup.PickupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {
    @Autowired
    PickupService pickupService;

    @KafkaListener(containerFactory = "jsonKafkaListenerContainerFactory",
            topics = "${kafka.topic.name}",
            groupId = "${kafka.topic.groupId}")
    public void consumePickupDtoData(PickupDto pickupDto,
                                     @Header(KafkaHeaders.RECEIVED_PARTITION_ID)String partitionId,
                                     @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY)String key,
                                     @Header(KafkaHeaders.RECEIVED_TOPIC)String topicName,
                                     @Header(KafkaHeaders.OFFSET)String offsetValue
                                     ){

        log.info("Consumed Message: Order Id: {}, Customer Name: {} from Partition ID: {} with Key : {} , topic : {} , offset : {}",
                pickupDto.getPickId(), pickupDto.getEmployee().getEmail(),
                partitionId, key, topicName, offsetValue);

        if(pickupDto.getOrdersList().size() == 1)
            pickupService.createSinglePicking(pickupDto);
        else
            pickupService.createBatchPicking(pickupDto);
    }
}

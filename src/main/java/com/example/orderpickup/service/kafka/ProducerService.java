package com.example.orderpickup.service.kafka;

import com.example.orderpickup.dtos.PickupDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class ProducerService {

    private final KafkaTemplate<String, PickupDto> pickupDtoKafkaTemplate;

    @Value("${kafka.topic.name}")
    private String JSON_TOPIC;

    public ProducerService(KafkaTemplate<String,PickupDto> pickupDtoKafkaTemplate){
        this.pickupDtoKafkaTemplate = pickupDtoKafkaTemplate;
    }


    public void sendPickupDoneMessage(PickupDto pickupDto){
        log.info(String.format("$$$$ => Producing Order message: %s",pickupDto));

        pickupDtoKafkaTemplate.executeInTransaction(t -> {
            ListenableFuture<SendResult<String, PickupDto>> future = t.send(JSON_TOPIC, pickupDto.getPickId(), pickupDto);
            future.addCallback(new ListenableFutureCallback<SendResult<String, PickupDto>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.info("Unable to produce message = [ {} ] due to : {}", pickupDto, throwable.getMessage());
                }

                @Override
                public void onSuccess(SendResult<String, PickupDto> stringPickupDtoSendResult) {
                    log.info("Sent Order Created Message = [ {} ] with offset= [ {} ]", pickupDto, stringPickupDtoSendResult.getRecordMetadata().offset());
                }
            });
            return true;
        });
    }
}

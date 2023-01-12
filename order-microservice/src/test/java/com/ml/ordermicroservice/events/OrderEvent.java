package com.ml.ordermicroservice.events;

import com.ml.ordermicroservice.dto.OrderDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OrderEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private String eventType;
    private OrderDTO orderDTO;

    public OrderEvent(Object source) {
        super(source);
    }


}

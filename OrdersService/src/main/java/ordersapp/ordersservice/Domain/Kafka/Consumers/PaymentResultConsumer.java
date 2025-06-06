package ordersapp.ordersservice.Domain.Kafka.Consumers;

import ordersapp.ordersservice.Application.OrdersService;
import ordersapp.ordersservice.Domain.Kafka.Events.PaymentResultEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
//public class PaymentResultConsumer {
//    @Autowired
//    private OrdersService ordersService;
//
//    @KafkaListener(topics = "", groupId = "orders")
//    public void handleTrainingUpdate(PaymentResultEvent event) {
//        var customerOptional = customerService.findById(event.customerId());
//
//        if (customerOptional.isEmpty()) {
//            return;
//        }
//
//        var customer = customerOptional.get();
//        switch (event.updatedParameter()) {
//            case "handPower" -> customer.setHandPower(customer.getHandPower() + 1);
//            case "legPower" -> customer.setLegPower(customer.getLegPower() + 1);
//            case "iq" -> customer.setIq(customer.getIq() + 1);
//            default -> {
//                return;
//            }
//        }
//        customerService.save(customer);
//    }
//}

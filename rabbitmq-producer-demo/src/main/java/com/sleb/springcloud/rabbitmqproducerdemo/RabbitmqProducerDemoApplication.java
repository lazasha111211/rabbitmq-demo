package com.sleb.springcloud.rabbitmqproducerdemo;


import com.sleb.springcloud.rabbitmqproducerdemo.service.AcceptConfirmCallback;
import com.sleb.springcloud.rabbitmqproducerdemo.service.AccpetReturnCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqProducerDemoApplication {
    private final Logger logger = LoggerFactory.getLogger(RabbitmqProducerDemoApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqProducerDemoApplication.class, args);
    }
    @Bean
    public MessageConverter messageConvert() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        logger.info("caching factory: {}", factory.toString());
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setConfirmCallback(new AcceptConfirmCallback());

        /*
         * 当mandatory标志位设置为true时
         * 如果exchange根据自身类型和消息routingKey无法找到一个合适的queue存储消息
         * 那么broker会调用basic.return方法将消息返还给生产者
         * 当mandatory设置为false时，出现上述情况broker会直接将消息丢弃
         */
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(messageConvert());
        rabbitTemplate.setReturnCallback(new AccpetReturnCallback());
        //使用单独的发送连接，避免生产者由于各种原因阻塞而导致消费者同样阻塞
        rabbitTemplate.setUsePublisherConnection(true);

        return rabbitTemplate;
    }
}

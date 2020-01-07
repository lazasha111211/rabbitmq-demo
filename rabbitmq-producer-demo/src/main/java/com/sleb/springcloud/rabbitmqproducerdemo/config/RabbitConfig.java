package com.sleb.springcloud.rabbitmqproducerdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @description:   交换机、队列、路由键定义、创建和绑定
 *                 可以定义多个交换机、队列、路由键
 * @author: lazasha
 * @date: 2019/12/23  17:17
 **/
@Configuration
public class RabbitConfig {
    /**
     * 投保消息交换机的名字
     */
    public static final String EXCHANGE_SLEB_ACCEPT = "exchange_sleb_accept";

    /**
     * 投保消息队列
     */
    public static final String QUEUE_SLEB_ACCEPT = "queue_sleb_accept";
    /**
     * 投保消息路由键
     */
    public static final String ROUTING_KEY_ACCEPT = "routing_key_accept";
    /**
     *  投保消息死信交换机
     */
    public static final String DLX_EXCHANGE_SLEB_ACCEPT = "exchange_dlx_sleb_accept";
    /**
     * 投保消息死信队列
     */
    public static final String DLX_QUEUE_SLEB_ACCEPT = "queue_dlx_sleb_accept";
    /**
     *  常用交换器类型如下：
     *       Direct(DirectExchange)：direct 类型的行为是"先匹配, 再投送".
     *       即在绑定时设定一个 routing_key, 消息的routing_key完全匹配时, 才会被交换器投送到绑定的队列中去。
     *       Topic(TopicExchange)：按规则转发消息（最灵活）。
     *       Headers(HeadersExchange)：设置header attribute参数类型的交换机。
     *       Fanout(FanoutExchange)：转发消息到所有绑定队列。
     *
     * 下面都是采用direct， 必须严格匹配exchange和queue
     * 投保消息交换机
     */
    @Bean("slebAcceptExchange")
    DirectExchange slebAcceptExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_SLEB_ACCEPT).durable(true).build();

    }
    /**
     * 第二个参数 durable: 是否持久化，如果true，则此种队列叫持久化队列（Durable queues）。此队列会被存储在磁盘上，
     *                 当消息代理（broker）重启的时候，它依旧存在。没有被持久化的队列称作暂存队列（Transient queues）。
     * 第三个参数 execulusive: 表示此对应只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
     * 第四个参数 autoDelete: 当没有生成者/消费者使用此队列时，此队列会被自动删除。(即当最后一个消费者退订后即被删除)
     *
     * 这儿是(queue)队列持久化（durable=true）,exchange也需要持久化
     * ********************死信队列**********************************************************
     *            x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
     *            x-dead-letter-routing-key  这里声明当前队列的死信路由key
     *            采用死信队列，才会用到下面的参数
     *            Map<String, Object> args = new HashMap<>(2);
     *            args.put("x-dead-letter-exchange", DLX_EXCHANGE_SLEB_ACCEPT);
     *            args.put("x-dead-letter-routing-key", ROUTING_KEY_ACCEPT);
     *            return QueueBuilder.durable(QUEUE_SLEB_ACCEPT).withArguments(args).build();
     * ********************死信队列**********************************************************
     * 投保消息队列
     */
    @Bean("slebAcceptQueue")
    public Queue slebAcceptQueue() {
        return QueueBuilder.durable(QUEUE_SLEB_ACCEPT).build();
    }

    /**
     * 交换机、队列、绑定
     */
    @Bean("bindingSlebAcceptExchange")
    Binding bindingSlebAcceptExchange(@Qualifier("slebAcceptQueue") Queue queue,
                                      @Qualifier("slebAcceptExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY_ACCEPT);
    }
    /**
     * 投保死信交换机
     */
    @Bean("slebDlxAcceptExchange")
    DirectExchange slebDlxAcceptExchange() {
        return ExchangeBuilder.directExchange(DLX_EXCHANGE_SLEB_ACCEPT).durable(true).build();
    }
    /**
     * 投保死信队列
     */
    @Bean("slebDlxAcceptQueue")
    public Queue slebDlxAcceptQueue() {
        return QueueBuilder.durable(DLX_QUEUE_SLEB_ACCEPT).build();
    }
    /**
     * 死信交换机、队列、绑定
     */
    @Bean("bindingDlxSlebAcceptExchange")
    Binding bindingDlxSlebAcceptExchange(@Qualifier("slebDlxAcceptQueue") Queue queue,
                                         @Qualifier("slebDlxAcceptExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(ROUTING_KEY_ACCEPT);
    }

}

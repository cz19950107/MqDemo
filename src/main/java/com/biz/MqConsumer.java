package com.biz;

import com.rabbitmq.client.*;
import sun.management.ManagementFactoryHelper;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by 春子 on 2017/12/1.
 * 消息的消费者
 */
public class MqConsumer {
    //定义一个消息队列
    //private final static String QUEUE_NAME = "people";
    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException, TimeoutException {

        // 获取不同的pid,方便标识不同的消费者
//        String name= ManagementFactory.getRuntimeMXBean().getName();
//        String pid =name.split("@")[0];
        ConnectionFactory mqfactory = new ConnectionFactory();
        mqfactory.setHost("120.78.144.230");
        mqfactory.setPort(5672);
        mqfactory.setUsername("wuxin");
        mqfactory.setPassword("wuxin");
        Connection connection = mqfactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        Consumer consumer = new DefaultConsumer(channel) {

            UserMessage userMessage =new UserMessage();

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(  message );
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }



    }














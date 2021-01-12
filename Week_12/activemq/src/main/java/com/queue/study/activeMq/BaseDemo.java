package com.queue.study.activeMq;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.UUID;

/**
 * 原生DEMO
 */
@Slf4j
public class BaseDemo {

    private static final String BROKER_URL = "tcp://127.0.0.1:61616";

    private static final String USERNAME = "admin";

    private static final String PASSWORD = "admin";

    private static final String QUEUENAME = "test";

    private static final Integer SEND_SIZE = 3000;


    public static void main(String[] args) throws JMSException {
            //创建session
            ConnectionFactory connectionFactory = initConnectionFactory();
            Connection connection = createConnection(connectionFactory);
            Session session = createSession(connection);
            //创建消费者
            MqConsumer mqConsumer = new MqConsumer(session);
            //创建生产者
            MqProduct mqProduct = new MqProduct(session);
            //发送消息
            for (int i = 0; i < SEND_SIZE; i++) {
                mqProduct.sendMsg(mqProduct.createMsg());
            }
            session.close();
            connection.close();
    }


    private static class MqProduct {

        private MessageProducer messageProducer;

        private Session session;

        public MqProduct(Session session) throws JMSException {
            this.messageProducer = session.createProducer(session.createQueue(QUEUENAME));
            this.session = session;
        }

        private Message createMsg() throws JMSException {
            return session.createTextMessage(UUID.randomUUID().toString());
        }


        private void sendMsg(Message msg) throws JMSException {
            messageProducer.send(msg);
            log.info("发送的队列为{} 发送的消息为{}", QUEUENAME, msg.toString());
        }
    }

    private static class MqConsumer {
        private MessageConsumer messageConsumer;

        public MqConsumer(Session session) throws JMSException {
            this.messageConsumer = session.createConsumer(session.createQueue(QUEUENAME));
            this.messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage=(TextMessage)message;
                    try {
                        log.info("消费到的消息为 {}",textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }


    private static ConnectionFactory initConnectionFactory() {
        return new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
    }

    private static Connection createConnection(ConnectionFactory connectionFactory) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        connection.start();
        return connection;
    }

    private static Session createSession(Connection connection) throws JMSException {
        //设置自动提交 不开启事务
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }


}

package org.springframework.amqp.rabbit.demo.lagou.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author Jie Zhao
 * @date 2021/11/11 14:13
 */
@Component
public class RabbitMessageListener {


	/**
	 * com.rabbitmq.client.Channel to get access to the Channel
	 * org.springframework.amqp.core.Message or one if subclass to get access to the raw AMQP message
	 * org.springframework.messaging.Message to use the messaging abstraction counterpart
	 * @Payload-annotated method arguments including the support of validation
	 * @Header-annotated method arguments to extract a specific header value, including standard AMQP headers defined by AmqpHeaders
	 * @Headers-annotated argument that must also be assignable to java.util.Map for getting access to all headers.
	 * MessageHeaders arguments for getting access to all headers.
	 * MessageHeaderAccessor or AmqpMessageHeaderAccessor for convenient access to all method arguments
	 */
	@RabbitListener(queues = "queue.anno")
	public void whenMessageCome(Message message) throws UnsupportedEncodingException {
		System.out.print(this.getClass().getSimpleName() + ": ");
		System.out.println(new String(message.getBody(), message.getMessageProperties().getContentEncoding()));
	}
}

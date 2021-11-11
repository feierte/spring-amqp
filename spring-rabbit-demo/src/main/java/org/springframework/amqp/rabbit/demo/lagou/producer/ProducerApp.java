package org.springframework.amqp.rabbit.demo.lagou.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.demo.lagou.config.RabbitConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.charset.StandardCharsets;

/**
 * @author Jie Zhao
 * @date 2021/11/11 14:02
 */
public class ProducerApp {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RabbitConfig.class);

		final RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
		MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
				.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
				.setContentEncoding("utf-8")
				.setHeader("mykey", "myvalue")
				.build();
		final Message message = MessageBuilder.withBody("Hello World!!!".getBytes(StandardCharsets.UTF_8))
				.andProperties(messageProperties)
				.build();

		rabbitTemplate.send("ex.anno", "key.anno", message);

		applicationContext.close();
	}
}

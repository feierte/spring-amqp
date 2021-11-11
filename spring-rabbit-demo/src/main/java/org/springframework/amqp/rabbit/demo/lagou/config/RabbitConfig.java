package org.springframework.amqp.rabbit.demo.lagou.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

/**
 * @author Jie Zhao
 * @date 2021/11/11 13:46
 */
@Configuration
@ComponentScan(basePackages = {"org.springframework.amqp.rabbit.demo.lagou"})
@EnableRabbit
public class RabbitConfig {

	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory connectionFactory =
				new CachingConnectionFactory(URI.create("amqp://admin:admin@192.168.159.135:5672/admin"));
		return connectionFactory;
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return new RabbitTemplate(connectionFactory);
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public Queue queue() {
		Queue queue = QueueBuilder.nonDurable("queue.anno")
				.autoDelete()
				.build();
		return queue;
	}

	@Bean
	public Exchange exchange() {
		return new FanoutExchange("ex.anno", false, true, null);
	}


	@Bean
	public Binding binding(Queue queue, Exchange exchange) {
		Binding binding = BindingBuilder.bind(queue)
				.to(exchange)
				.with("key.anno")
				.noargs();
		return binding;
	}

	@Bean("rabbitListenerContainerFactory")
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
		rabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
		rabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
		rabbitListenerContainerFactory.setConcurrentConsumers(10);
		return rabbitListenerContainerFactory;
	}
}

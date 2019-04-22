package com.amir.activemqdemoreceiver;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@SpringBootApplication
public class ActivemqDemoReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivemqDemoReceiverApplication.class, args);
	}

	
	
	//configure-connection factory
	@Bean
	public ConnectionFactory connectionFactory() {
		//note active support 5(tcp,amqp,stomp,mqtt,ws) protocols to connect the broker
			/*Listening for connections at: tcp://localhost:61616?maximumConnections=1000&wireFormat.maxFrameSize=104857600
			Listening for connections at: amqp://localhost:5672?maximumConnections=1000&wireFormat.maxFrameSize=104857600
			Listening for connections at: stomp://localhost:61613?maximumConnections=1000&wireFormat.maxFrameSize=104857600
			Listening for connections at: mqtt://localhost:1883?maximumConnections=1000&wireFormat.maxFrameSize=104857600
			Listening for connections at ws://localhost:61614?maximumConnections=1000&wireFormat.maxFrameSize=104857600
	*/	
		// ActiveMQConnectionFactory is factory class for tcp protocol..each protocol have their own factory class.
		ActiveMQConnectionFactory factory  =  new ActiveMQConnectionFactory("tcp://localhost:61616");
		factory.setUserName("admin");
		factory.setPassword("admin");
		return factory;
	}

	
	//configure listener-factory
	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
	
	@JmsListener(containerFactory="myFactory", destination = "myQueue")
	public void readJmsMessage(String message) {
		System.out.println("RECEIVED:-"+message);
		
	}

}

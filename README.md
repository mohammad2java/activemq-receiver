How to receive message from ActiveMQ
------------------------------------

step-1
------
create spring boot application with following maven dependency
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-activemq</artifactId>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
		</dependency>

step-2
---------
create following beans 

1-ConnectionFactory <br>
2-JmsListenerContainerFactory <br>

Example:
---------

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
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
		

step-3
-----------
create receiver and method ..to make method listening enable using following annotation on method <br>
			
	@JmsListener(containerFactory="myFactory", destination = "myQueue")

Example:
-----------

	@JmsListener(containerFactory="myFactory", destination = "myQueue")
	public void readJmsMessage(String message) {
		System.out.println("RECEIVED:-"+message);	
	}

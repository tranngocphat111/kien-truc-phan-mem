package fit.iuh.messagequeue;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
public class MessageQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageQueueApplication.class, args);
	}

	// 1. ĐỊNH NGHĨA NGƯỜI NHẬN
	public static class MessageReceiver {
		public void handleMessage(String message) {
			System.out.println("===> [NHẬN ĐƯỢC]: " + message);
		}
	}

	// 2. CẤU HÌNH BỘ LẮNG NGHE (LISTENER)
	@Bean
	MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
		// Gọi hàm 'handleMessage' trong class MessageReceiver khi có tin nhắn
		return new MessageListenerAdapter(receiver, "handleMessage");
	}

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
											MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		// Lắng nghe trên channel tên là "test-channel"
		container.addMessageListener(listenerAdapter, new PatternTopic("test-channel"));
		return container;
	}

	@Bean
	MessageReceiver receiver() {
		return new MessageReceiver();
	}

	// 3. CODE TEST GỬI TIN NHẮN (SENDER)
	@Bean
	CommandLineRunner run(StringRedisTemplate template) {
		return args -> {
			System.out.println("Đang gửi tin nhắn...");
			template.convertAndSend("test-channel", "Chào Redis! Đây là tin nhắn từ Spring Boot.");
			System.out.println("Đã gửi xong!");
		};
	}
}




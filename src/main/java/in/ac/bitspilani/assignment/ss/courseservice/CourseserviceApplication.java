package in.ac.bitspilani.assignment.ss.courseservice;

import com.google.gson.Gson;
import in.ac.bitspilani.assignment.ss.courseservice.model.Course;

import in.ac.bitspilani.assignment.ss.courseservice.model.Subject;
import in.ac.bitspilani.assignment.ss.courseservice.rabbitmq.CourseRequestQueueReader;
import in.ac.bitspilani.assignment.ss.courseservice.repository.CourseRepository;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class CourseserviceApplication implements CommandLineRunner {

	public static final String getCourseDetailsQueueName = "GetCourseDetailsQueue";
	public static final String topicExchangeName = "CourseDetailsExchange";
	public static final String sendCourseDetailsQueueName = "SendCourseDetailsQueue";

	@Autowired
	CourseRepository courseRepository;

	@Bean
	Queue getCourseDetailsQueue(){
		return new Queue(getCourseDetailsQueueName, true);
	}

	@Bean
	Queue sendCourseDetailsQueue(){
		return new Queue(sendCourseDetailsQueueName, true);
	}

	@Bean
	TopicExchange topicExchange(){
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding getCourseDetailsBinding(Queue getCourseDetailsQueue, TopicExchange topicExchange){
		return BindingBuilder.bind(getCourseDetailsQueue).to(topicExchange).with("course.details.request");
	}

	@Bean
	Binding sendCourseDetailsBinding(Queue sendCourseDetailsQueue, TopicExchange topicExchange){
		return BindingBuilder.bind(sendCourseDetailsQueue).to(topicExchange).with("course.details.response");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listnerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(getCourseDetailsQueueName);
		container.setMessageListener(listnerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(CourseRequestQueueReader receiver) {
		return new MessageListenerAdapter(receiver, "readMessage");
	}

	public static void main(String[] args) {
		SpringApplication.run(CourseserviceApplication.class, args);

	}

	@Override
	public void run(String... args) {
		//createCourseData();
	}

	public static void sendCourseDetailsMessage(int courseId, CourseRepository courseRepository, RabbitTemplate rabbitTemplate){
		Optional<Course> courseDetails = courseRepository.findById(courseId);
		String courseDetailsJson = new Gson().toJson(courseDetails.orElse(new Course()));
		rabbitTemplate.convertAndSend(topicExchangeName, "course.details.response", courseDetailsJson);
		System.out.println(courseDetailsJson);
	}

	/*private void createCourseData(){
		List<Subject> subjects = new ArrayList<>();
		Subject subject = new Subject(1, "Software Project Management", -1, false, true);
		subjects.add(subject);
		subject = new Subject(2, "Software Quality Management", -1, false, true);
		subjects.add(subject);
		subject = new Subject(3, "Scalable Services", -1, false, true);
		subjects.add(subject);
		subject = new Subject(4, "Blockchain Systens & Environment", -1, false, true);
		subjects.add(subject);
		subject = new Subject(5, "Introduction to DevOps", -1, false, true);
		subjects.add(subject);
		subject = new Subject(6, "Software Architecture", 1, true, true);
		subjects.add(subject);
		subject = new Subject(7, "Agile Methodologies", 1, true, true);
		subjects.add(subject);
		subject = new Subject(8, "Introduction to Cyber Security", -1, false, true);
		subjects.add(subject);
		subject = new Subject(9, "Software Testing", 2, true, true);
		subjects.add(subject);
		subject = new Subject(10, "Software Product Management", 2, true, true);
		subjects.add(subject);
		subject = new Subject(11, "Cross Platform Development", -1, false, true);
		subjects.add(subject);
		subject = new Subject(12, "Artificial Intelligence", -1, false, true);
		subjects.add(subject);
		subject = new Subject(13, "Machine Learning", -1, false, true);
		subjects.add(subject);
		subject = new Subject(14, "Secure Software Engineering", -1, false, true);
		subjects.add(subject);
		subject = new Subject(15, "Cloud Computing", 1, false, true);
		subjects.add(subject);
		subject = new Subject(1, "Software Project Management", -1, false, true);
		subjects.add(subject);
		subject = new Subject(1, "Software Project Management", -1, false, true);
		subjects.add(subject);
		subject = new Subject(1, "Software Project Management", -1, false, true);
		subjects.add(subject);
		subject = new Subject(1, "Software Project Management", -1, false, true);
		subjects.add(subject);

		Course course = new Course(1, "Software Engineering", subjects);
		courseRepository.save(course);
	}*/
}

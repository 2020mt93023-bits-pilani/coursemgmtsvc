package in.ac.bitspilani.assignment.ss.courseservice.rabbitmq;


import in.ac.bitspilani.assignment.ss.courseservice.CourseserviceApplication;
import in.ac.bitspilani.assignment.ss.courseservice.repository.CourseRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CourseRequestQueueReader {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    CourseRepository courseRepository;

    public CourseRequestQueueReader(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void readMessage(String message) {
        System.out.println("Received <" + message + ">");
        int courseId = Integer.parseInt(message);
        CourseserviceApplication.sendCourseDetailsMessage(courseId, courseRepository, rabbitTemplate);
    }

}

package com.zhxq;

import com.zhxq.controller.ExamController;
import com.zhxq.domain.Choice;
import com.zhxq.domain.Question;
import com.zhxq.domain.User;
import com.zhxq.repository.QuestionRepository;
import com.zhxq.service.QuestionService;
import com.zhxq.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


@SpringBootApplication

public class TestApplication{

    public TestApplication(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

    private QuestionRepository questionRepository;

	@Bean
	CommandLineRunner commandLineRunner(UserService UserService) {

		return (args) -> {
			User admin = new User("test", "123456", 1, "H16000608", -1);
			admin.setAuthority("ROLE_ADMIN");
			UserService.register(admin);

			for(int i = 0; i < 4; i++){
				User user = new User("test "+i, "123456", 2,"H1600060"+i);
				user.setAuthority("ROLE_USER");
				UserService.register(user);
			}

			for (int i = 0; i < 3; i++){
                List<Choice> choices = new ArrayList<>();
                choices.add(new Choice("one"));
                choices.add(new Choice("two"));
                choices.add(new Choice("three"));
                choices.add(new Choice("four"));
                Question question = new Question("hello world"+i,"one",choices);
                questionRepository.save(question);
            }

			List<Choice> choices = new ArrayList<>();
			choices.add(new Choice("one"));
			choices.add(new Choice("two"));
			choices.add(new Choice("three"));
			choices.add(new Choice("four"));
			Question question = new Question("multiply", "one;two",choices);
			questionRepository.save(question);
            questionRepository.flush();

		};
	}

}

    @Component
	class ServletCustomizer implements EmbeddedServletContainerCustomizer {
	@Override
	//映射记录内容的 MIME 类型的扩展
	public void customize(ConfigurableEmbeddedServletContainer container) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		mappings.add("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		container.setMimeMappings(mappings);
	}
}


package io.tinder.ai_tinder_backend;

import io.tinder.ai_tinder_backend.profiles.ProfileCreationService;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.ai.chat.ChatResponse;


@SpringBootApplication
public class AiTinderBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileCreationService profileCreationService;

	@Autowired
	private OpenAiChatModel chatModel;

	public AiTinderBackendApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(AiTinderBackendApplication.class, args);
	}

	public void run(String ... args) {
		profileCreationService.createProfiles(0);
		profileCreationService.saveProfilesToDB();

	}

}

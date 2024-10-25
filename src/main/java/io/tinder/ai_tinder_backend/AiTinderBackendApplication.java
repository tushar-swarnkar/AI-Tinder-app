package io.tinder.ai_tinder_backend;

import io.tinder.ai_tinder_backend.conversations.ConversationRepository;
import io.tinder.ai_tinder_backend.matches.MatchRepository;
import io.tinder.ai_tinder_backend.profiles.ProfileCreationService;
import io.tinder.ai_tinder_backend.profiles.ProfileRepository;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.ai.chat.ChatResponse;


@SpringBootApplication
public class AiTinderBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private MatchRepository matchRepository;

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
		clearAllData();
		profileCreationService.saveProfilesToDB();

	}

	private void clearAllData() {
		conversationRepository.deleteAll();
		matchRepository.deleteAll();
		profileRepository.deleteAll();
	}

}

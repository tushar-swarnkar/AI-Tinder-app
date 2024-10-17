package io.tinder.ai_tinder_backend;

import io.tinder.ai_tinder_backend.conversations.ChatMessage;
import io.tinder.ai_tinder_backend.conversations.Conversation;
import io.tinder.ai_tinder_backend.conversations.ConversationRepository;
import io.tinder.ai_tinder_backend.profiles.Gender;
import io.tinder.ai_tinder_backend.profiles.Profile;
import io.tinder.ai_tinder_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class AiTinderBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConversationRepository conversationRepository;

	public static void main(String[] args) {
		SpringApplication.run(AiTinderBackendApplication.class, args);
	}

	public void run(String ... args) {

		profileRepository.deleteAll();
		conversationRepository.deleteAll();

		// create a profile and save it to the db
		Profile profile = new Profile(
				"1",
				"Tushar",
				"Swarnkar",
				"Indian",
				Gender.MALE,
				21,
				"Software Developer",
				"foo.jpg",
				"INTP"
		);
		profileRepository.save(profile);

		Profile profile2 = new Profile(
				"2",
				"Blah",
				"Blacksheep",
				"American",
				Gender.MALE,
				25,
				"Software Developer",
				"foo.jpg",
				"INTP"
		);
		profileRepository.save(profile2);

		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation = new Conversation(
				"1",
				profile.id(),
				List.of(
						new ChatMessage("Hello", profile.id(), LocalDateTime.now())
				)
		);
		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out::println);

	}

}

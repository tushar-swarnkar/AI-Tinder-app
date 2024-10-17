package io.tinder.ai_tinder_backend;

import io.tinder.ai_tinder_backend.profiles.Gender;
import io.tinder.ai_tinder_backend.profiles.Profile;
import io.tinder.ai_tinder_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiTinderBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	public static void main(String[] args) {
		SpringApplication.run(AiTinderBackendApplication.class, args);
	}

	public void run(String ... args) {
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

		profileRepository.findAll().forEach(System.out::println);
	}

}

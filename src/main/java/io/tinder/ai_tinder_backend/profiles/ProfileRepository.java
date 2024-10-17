package io.tinder.ai_tinder_backend.profiles;

// CRUD Operations for Profile: Create, Read, Update, Delete

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {

}

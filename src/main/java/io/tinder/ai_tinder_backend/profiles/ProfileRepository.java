package io.tinder.ai_tinder_backend.profiles;

// CRUD Operations for Profile: Create, Read, Update, Delete

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {

//    aggregation query to get random sample of 1 profile
//    syntax:      { $sample: { size: 1 } }

    @Aggregation(pipeline = {"{ $sample: { size: 1 } }"} )
    Profile getRandomProfile();
}

package io.tinder.ai_tinder_backend.profiles;

public record Profile(
        String id,
        String firstName,
        String lastName,
        String ethncity,
        Gender gender,
        int age,
        String bio,
        String imageUrl,
        String myersBriggsPersonalityType
) {
}

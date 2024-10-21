package io.tinder.ai_tinder_backend.matches;

import io.tinder.ai_tinder_backend.profiles.Profile;

public record Match (
        String id,
        Profile profile1,
        String conversationId) {}

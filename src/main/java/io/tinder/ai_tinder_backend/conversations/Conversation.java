package io.tinder.ai_tinder_backend.conversations;

import java.util.List;

public record Conversation(
        String id, // the id of the conversation
        String profileId, // the ai profile I'm conversing with
        List<ChatMessage> messages // list of messages in the conversation
) {}

package io.tinder.ai_tinder_backend.conversations;

import java.time.LocalDateTime;

public record ChatMessage(
        String messageText,
        String authorId, // id of the 'author' of the message
        LocalDateTime messageTime // time the message was sent
) {
}

package io.tinder.ai_tinder_backend.conversations;

import io.tinder.ai_tinder_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;

    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/conversations")
    public Conversation createNewConversation(@RequestBody CreateConversationRequest request) {

        // check if profile exists
        profileRepository.findById(request.profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Profile not found " + request.profileId
                ));

        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                request.profileId, // the ai profile I'm conversing with
                new ArrayList<>() // empty list of messages
        );
        conversationRepository.save(conversation);
        return conversation;
    }

    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessageToConversation(
            @PathVariable String conversationId,
            @RequestBody ChatMessage chatMessage
    ) {

        // check if conversation exists
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Conversation not found " + conversationId
                ));

        // check if author exists
        profileRepository.findById(chatMessage.authorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Profile not found " + chatMessage.authorId()
                ));

        // TODO: Need to validate that the author of a message happens to be only the profile associated with the message or the user who is logged in

//        CAN I ADD THE TIME BEFORE SAVING THE MESSAGE TO THE CONVERSATION?
//        BECAUSE, THE USER MIGHT BE SENDING THE MESSAGE FROM A DIFFERENT TIMEZONE OR CAN TROLL THE SYSTEM BY CHANGING THE TIME
        ChatMessage messageWithTime = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(), // how do we believe the authorId is real? --> checked above
                LocalDateTime.now()
        );

        // add message to conversation and save
        conversation.messages().add(messageWithTime);
        conversationRepository.save(conversation);
        return conversation;
    }


    public record CreateConversationRequest(
            String profileId
    ) {
    }

}

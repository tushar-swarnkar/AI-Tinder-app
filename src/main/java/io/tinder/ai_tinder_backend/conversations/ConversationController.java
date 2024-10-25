package io.tinder.ai_tinder_backend.conversations;

import io.tinder.ai_tinder_backend.profiles.Profile;
import io.tinder.ai_tinder_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final ConversationService conversationService;

    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository, ConversationService conversationService) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.conversationService = conversationService;
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

    @CrossOrigin(origins = "*")
    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(@PathVariable String conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Conversation not found " + conversationId
                ));
    }

    @CrossOrigin(origins = "*")
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

        String matchProfileId = conversation.profileId();

        Profile profile = profileRepository.findById(matchProfileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find profile with id: " + matchProfileId
                ));
        Profile user = profileRepository.findById(chatMessage.authorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find profile with id: " + chatMessage.authorId()
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

        conversationService.generateProfileResponse(conversation, profile, user);

        conversationRepository.save(conversation);
        return conversation;
    }


    public record CreateConversationRequest(
            String profileId
    ) {
    }

}

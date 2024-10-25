package io.tinder.ai_tinder_backend.matches;

import io.tinder.ai_tinder_backend.conversations.Conversation;
import io.tinder.ai_tinder_backend.conversations.ConversationRepository;
import io.tinder.ai_tinder_backend.profiles.Profile;
import io.tinder.ai_tinder_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
public class MatchController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final MatchRepository matchRepository;

    public MatchController(ConversationRepository conversationRepository, ProfileRepository profileRepository, MatchRepository matchRepository) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.matchRepository = matchRepository;
    }

    public record CreateMatchRequest(String profileId) {}

    @CrossOrigin(origins = "*")
    @PostMapping("/matches")
    public Match createNewMatch(@RequestBody CreateMatchRequest request) {

        // check if profile exists
        Profile profile = profileRepository.findById(request.profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Profile not found " + request.profileId
                ));

        // TODO: make sure that there is no previous conversation with this particular profile already

        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                profile.id(), // the ai profile I'm conversing with
                new ArrayList<>() // empty list of messages
        );

        conversationRepository.save(conversation);

        Match match = new Match (
                UUID.randomUUID().toString(),
                profile,
                conversation.id()
        );
        matchRepository.save(match);

        return match;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/matches")
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

}




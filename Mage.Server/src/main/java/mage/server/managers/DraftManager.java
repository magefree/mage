package mage.server.managers;

import mage.game.draft.Draft;
import mage.server.draft.DraftController;
import mage.view.DraftPickView;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface DraftManager {
    UUID createDraftSession(Draft draft, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId);

    void joinDraft(UUID draftId, UUID userId);

    void destroyChatSession(UUID gameId);

    DraftPickView sendCardPick(UUID draftId, UUID userId, UUID cardId, Set<UUID> hiddenCards);

    void sendCardMark(UUID draftId, UUID userId, UUID cardId);
    
    void setBoosterLoaded(UUID draftId, UUID userId);

    void removeSession(UUID userId);

    void kill(UUID draftId, UUID userId);

    void timeout(UUID gameId, UUID userId);

    void removeDraft(UUID draftId);

    DraftController getControllerByDraftId(UUID draftId);

    Optional<DraftController> getController(UUID tableId);
}

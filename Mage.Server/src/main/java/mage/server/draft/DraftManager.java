

package mage.server.draft;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import mage.game.draft.Draft;
import mage.view.DraftPickView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public enum DraftManager {
    instance;

    private final ConcurrentMap<UUID, DraftController> draftControllers = new ConcurrentHashMap<>();

    public UUID createDraftSession(Draft draft, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId) {
        DraftController draftController = new DraftController(draft, userPlayerMap, tableId);
        draftControllers.put(draft.getId(), draftController);
        return draftController.getSessionId();
    }

    public void joinDraft(UUID draftId, UUID userId) {
        draftControllers.get(draftId).join(userId);
    }

    public void destroyChatSession(UUID gameId) {
        draftControllers.remove(gameId);
    }

    public DraftPickView sendCardPick(UUID draftId, UUID userId, UUID cardId, Set<UUID> hiddenCards) {
        return draftControllers.get(draftId).sendCardPick(userId, cardId, hiddenCards);
    }

    public void sendCardMark(UUID draftId, UUID userId, UUID cardId) {
        draftControllers.get(draftId).sendCardMark(userId, cardId);
    }

    public void removeSession(UUID userId) {
        for (DraftController controller: draftControllers.values()) {
            controller.kill(userId);
        }
    }

    public void kill(UUID draftId, UUID userId) {
        draftControllers.get(draftId).kill(userId);
    }

    public void timeout(UUID gameId, UUID userId) {
        draftControllers.get(gameId).timeout(userId);
    }

    public void removeDraft(UUID draftId) {
        draftControllers.remove(draftId);
    }

    public DraftController getControllerByDraftId(UUID draftId) {
        return draftControllers.get(draftId);
    }

    public Optional<DraftController> getController(UUID tableId) {
        return draftControllers.values().stream().filter(controller -> controller.getTableId().equals(tableId)).findFirst();
    }
}

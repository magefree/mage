package mage.server.draft;

import mage.game.draft.Draft;
import mage.server.managers.DraftManager;
import mage.server.managers.ManagerFactory;
import mage.view.DraftPickView;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DraftManagerImpl implements DraftManager {

    private final ManagerFactory managerFactory;
    private final ConcurrentMap<UUID, DraftController> draftControllers = new ConcurrentHashMap<>();

    public DraftManagerImpl(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Override
    public UUID createDraftSession(Draft draft, ConcurrentHashMap<UUID, UUID> userPlayerMap, UUID tableId) {
        DraftController draftController = new DraftController(managerFactory, draft, userPlayerMap, tableId);
        draftControllers.put(draft.getId(), draftController);
        return draftController.getSessionId();
    }

    @Override
    public void joinDraft(UUID draftId, UUID userId) {
        draftControllers.get(draftId).join(userId);
    }

    @Override
    public void destroyChatSession(UUID gameId) {
        draftControllers.remove(gameId);
    }

    @Override
    public DraftPickView sendCardPick(UUID draftId, UUID userId, UUID cardId, Set<UUID> hiddenCards) {
        return draftControllers.get(draftId).sendCardPick(userId, cardId, hiddenCards);
    }

    @Override
    public void sendCardMark(UUID draftId, UUID userId, UUID cardId) {
        draftControllers.get(draftId).sendCardMark(userId, cardId);
    }
    
    @Override
    public void setBoosterLoaded(UUID draftId, UUID userId) {
        draftControllers.get(draftId).setBoosterLoaded(userId);
    }

    @Override
    public void removeSession(UUID userId) {
        for (DraftController controller : draftControllers.values()) {
            controller.kill(userId);
        }
    }

    @Override
    public void kill(UUID draftId, UUID userId) {
        draftControllers.get(draftId).kill(userId);
    }

    @Override
    public void timeout(UUID gameId, UUID userId) {
        if (draftControllers.containsKey(gameId)) {
            // timeout calls from timer, so it can be too late here (e.g. after real draft ends)
            draftControllers.get(gameId).timeout(userId);
        }
    }

    @Override
    public void removeDraft(UUID draftId) {
        draftControllers.remove(draftId);
    }

    @Override
    public DraftController getControllerByDraftId(UUID draftId) {
        return draftControllers.get(draftId);
    }

    @Override
    public Optional<DraftController> getController(UUID tableId) {
        return draftControllers.values().stream().filter(controller -> controller.getTableId().equals(tableId)).findFirst();
    }
}

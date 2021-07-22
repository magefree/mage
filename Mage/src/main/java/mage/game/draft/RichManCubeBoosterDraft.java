package mage.game.draft;

import java.util.*;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.game.draft.DraftCube.CardIdentity;

/**
 *
 * @author spjspj
 */
public class RichManCubeBoosterDraft extends DraftImpl {

    // custom timeouts per pick on profi timing
    protected int[] customProfiTimes = {70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40};

    protected final Map<String, CardIdentity> cardsInCube = new LinkedHashMap<>();

    public RichManCubeBoosterDraft(DraftOptions options, List<ExpansionSet> sets) {
        super(options, sets);
    }

    @Override
    public void start() {
        cardNum = 0;
        while (!isAbort() && cardNum < 36) {
            openBooster();
            cardNum = 0;
            while (!isAbort() && pickCards()) {
                passLeft();
                fireUpdatePlayersEvent();
            }
        }
        resetBufferedCards();
        this.fireEndDraftEvent();
    }

    @Override
    protected void passLeft() {
        synchronized (players) {
            UUID startId = table.get(0);
            UUID currentId = startId;
            UUID nextId = table.getNext();
            DraftPlayer next = players.get(nextId);
            draftCube.leftCubeCards.clear();
            draftCube.leftCubeCards.addAll(draftCube.getCubeCards());
            cardsInCube.clear();
            for (CardIdentity card : draftCube.leftCubeCards) {
                cardsInCube.put(card.getName(), card);
            }

            while (true) {
                for (DraftPlayer player : players.values()) {
                    if (player != null && player.getDeck() != null) {
                        for (Card card : player.getDeck().getSideboard()) {
                            if (cardsInCube.get(card.getName()) != null) {
                                draftCube.removeFromLeftCards(cardsInCube.get(card.getName()));
                            }
                        }
                    }
                }

                List<Card> nextBooster = draftCube.createBooster();
                next.setBooster(nextBooster);
                if (Objects.equals(nextId, startId)) {
                    break;
                }
                nextId = table.getNext();
                next = players.get(nextId);
            }
        }
    }

    @Override
    protected boolean pickCards() {
        cardNum++;
        for (DraftPlayer player : players.values()) {
            if (cardNum > 36) {
                return false;
            }
            player.setPicking();
            player.getPlayer().pickCard(player.getBooster(), player.getDeck(), this);
        }
        synchronized (this) {
            while (!donePicking()) {
                try {
                    this.wait();
                } catch (InterruptedException ex) {
                }
            }
        }
        return true;
    }

    @Override
    public void firePickCardEvent(UUID playerId) {
        DraftPlayer player = players.get(playerId);
        if (cardNum > 36) {
            cardNum = 36;
        }
        if (cardNum <= 0) {
            cardNum = 1;
        }

        // richman uses custom times
        int time = (int) Math.ceil(customProfiTimes[cardNum - 1] * timing.getCustomTimeoutFactor());
        playerQueryEventSource.pickCard(playerId, "Pick card", player.getBooster(), time);
    }
}

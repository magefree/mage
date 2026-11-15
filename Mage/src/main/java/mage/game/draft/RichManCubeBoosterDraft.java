package mage.game.draft;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.game.draft.DraftCube.CardIdentity;

import java.util.*;

/**
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
        synchronized (players) {
            cardNum = 1;
            boosterNum = 1;
        }
        while (!isAbort() && cardNum <= 36) {
            synchronized (players) {
                openBooster();
                cardNum = 1;
            }
            fireUpdatePlayersEvent();
            while (!isAbort() && pickCards()) {
                // new booster each time, so order is irrelevant
                passBoosterToLeft();
                fireUpdatePlayersEvent();
            }
            synchronized (players) {
                boosterNum++;
            }
        }
        this.boosterSendingEndDraft();
        this.fireEndDraftEvent();
    }

    @Override
    protected void passBoosterToLeft() {
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
                next.setBoosterAndLoad(nextBooster);
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
        synchronized (players) {
            for (DraftPlayer player : players.values()) {
                if (cardNum > 36) {
                    return false;
                }
                player.setPickingAndSending();
            }
        }

        while (!donePicking()) {
            boosterSendingStart();
            picksWait();
        }

        synchronized (players) {
            cardNum++;
        }
        return true;
    }

    @Override
    protected int getRoundPickTimeout() {
        int cardNum = Math.min(36, this.cardNum);

        // richman uses custom times
        return (int) Math.ceil(customProfiTimes[cardNum - 1] * timing.getCustomTimeoutFactor());
    }
}


package mage.game.draft;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import mage.cards.Card;
import mage.cards.ExpansionSet;

/**
 *
 * @author spjspj
 */
public class RichManBoosterDraft extends DraftImpl {

    protected int[] richManTimes = {75, 70, 65, 60, 55, 50, 45, 40, 35, 35, 35, 35, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};

    public RichManBoosterDraft(DraftOptions options, List<ExpansionSet> sets) {
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
            while (true) {
                List<Card> nextBooster = sets.get(cardNum % sets.size()).createBooster();
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
        int time = richManTimes[cardNum - 1] * timing.getFactor();
        playerQueryEventSource.pickCard(playerId, "Pick card", player.getBooster(), time);
    }
} 


package mage.game.draft;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import org.apache.log4j.Logger;

/**
 *
 * @author spjspj
 */
public class RichManBoosterDraft extends DraftImpl {

    private static final Logger logger = Logger.getLogger(RichManBoosterDraft.class);

    // custom timeouts per pick on profi timing
    protected int[] customProfiTimes = {70, 70, 70, 70, 70, 70, 70, 70, 70, 70, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 60, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40};

    public RichManBoosterDraft(DraftOptions options, List<ExpansionSet> sets) {
        super(options, sets);
    }

    @Override
    public void start() {
        cardNum = 1;
        boosterNum = 1;
        while (!isAbort() && cardNum <= 36) {
            openBooster();
            cardNum = 1;
            fireUpdatePlayersEvent();
            while (!isAbort() && pickCards()) {
                // new booster each time, so order is irrelevant
                passBoosterToLeft();
                fireUpdatePlayersEvent();
            }
            boosterNum++;
        }
        this.fireEndDraftEvent();
    }

    @Override
    protected void passBoosterToLeft() {
        synchronized (players) {
            UUID startId = table.get(0);
            UUID currentId = startId;
            UUID nextId = table.getNext();
            DraftPlayer next = players.get(nextId);
            while (true) {
                List<Card> nextBooster = sets.get((cardNum - 1) % sets.size()).createBooster();
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
        for (DraftPlayer player : players.values()) {
            if (cardNum > 36) {
                return false;
            }
            player.setPicking();
            player.getPlayer().pickCard(player.getBooster(), player.getDeck(), this);
        }
        cardNum++;
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
        int cardNum = Math.min(36, this.cardNum);
        // richman uses custom times
        int time = (int) Math.ceil(customProfiTimes[cardNum - 1] * timing.getCustomTimeoutFactor());
        playerQueryEventSource.pickCard(playerId, "Pick card", player.getBooster(), time);
    }
} 

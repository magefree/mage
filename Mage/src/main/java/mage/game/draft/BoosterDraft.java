

package mage.game.draft;

import java.util.List;
import mage.cards.ExpansionSet;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BoosterDraft extends DraftImpl {

    public BoosterDraft(DraftOptions options, List<ExpansionSet> sets) {
        super(options, sets);
    }

    @Override
    public void start() {
        synchronized (players) {
            cardNum = 1;
            boosterNum = 1;
        }
        while (!isAbort() && boosterNum <= numberBoosters) {
            synchronized (players) {
                openBooster();
                cardNum = 1;
            }
            fireUpdatePlayersEvent();
            while (!isAbort() && pickCards()) {
                // pass booster order: left -> right -> left
                if (boosterNum % 2 == 1) {
                    passBoosterToLeft();
                } else {
                    passBoosterToRight();
                }
                fireUpdatePlayersEvent();
            }
            synchronized (players) {
                boosterNum++;
            }
        }
        this.boosterSendingEndDraft();
        this.fireEndDraftEvent();
    }

}

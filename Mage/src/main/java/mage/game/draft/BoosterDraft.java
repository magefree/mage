

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
        cardNum = 0;
        while (!isAbort() && boosterNum < numberBoosters) {
            openBooster();
            cardNum = 0;
            while (!isAbort() && pickCards()) {
                if (boosterNum % 2 == 1) {
                    passLeft();
                } else {
                    passRight();
                }
                fireUpdatePlayersEvent();
            }
        }
        resetBufferedCards();
        this.fireEndDraftEvent();
    }

}

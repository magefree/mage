

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
        cardNum = 1;
        boosterNum = 1;
        while (!isAbort() && boosterNum <= numberBoosters) {
            openBooster();
            cardNum = 1;
            fireUpdatePlayersEvent();
            while (!isAbort() && pickCards()) {
                if ((boosterNum + 1) % 2 == 1) {
                    passLeft();
                } else {
                    passRight();
                }
                fireUpdatePlayersEvent();
            }
            boosterNum++;
        }
        resetBufferedCards();
        this.fireEndDraftEvent();
    }

}

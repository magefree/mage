

package mage.cards;

import java.util.UUID;
import mage.constants.CardType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class LevelerCard extends CardImpl {

    private int maxLevelCounters;

    public LevelerCard(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs) {
        super(ownerId, setInfo, cardTypes, costs);
    }

    public LevelerCard(LevelerCard card) {
        super(card);
        this.maxLevelCounters = card.maxLevelCounters;
    }

    public int getMaxLevelCounters() {
        return maxLevelCounters;
    }

    protected void setMaxLevelCounters(int maxLevelCounters) {
        this.maxLevelCounters = maxLevelCounters;
    }

}



package mage.cards;

import mage.abilities.Ability;
import mage.constants.CardType;

import java.util.List;
import java.util.UUID;

/**
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

    protected void addAbilities(List<Ability> abilities) {
        for (Ability ability : abilities) {
            addAbility(ability);
        }
    }
}

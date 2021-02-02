
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class DayOfJudgment extends CardImpl {

    public DayOfJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private DayOfJudgment(final DayOfJudgment card) {
        super(card);
    }

    @Override
    public DayOfJudgment copy() {
        return new DayOfJudgment(this);
    }

}

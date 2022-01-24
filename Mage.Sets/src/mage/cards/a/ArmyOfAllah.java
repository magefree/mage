
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class ArmyOfAllah extends CardImpl {

    public ArmyOfAllah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");

        // Attacking creatures get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(2, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false));
    }

    private ArmyOfAllah(final ArmyOfAllah card) {
        super(card);
    }

    @Override
    public ArmyOfAllah copy() {
        return new ArmyOfAllah(this);
    }
}

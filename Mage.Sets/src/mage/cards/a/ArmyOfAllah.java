
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author fireshoes
 */
public final class ArmyOfAllah extends CardImpl {
    
    private static final FilterAttackingCreature filter = new FilterAttackingCreature("Attacking creatures");

    public ArmyOfAllah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");

        // Attacking creatures get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(2, 0, Duration.EndOfTurn, filter, false));
    }

    private ArmyOfAllah(final ArmyOfAllah card) {
        super(card);
    }

    @Override
    public ArmyOfAllah copy() {
        return new ArmyOfAllah(this);
    }
}

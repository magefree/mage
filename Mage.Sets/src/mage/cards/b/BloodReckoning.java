
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 */
public final class BloodReckoning extends CardImpl {

    public BloodReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever a creature attacks you or a planeswalker you control, that creature's controller loses 1 life.
        Effect effect = new LoseLifeTargetEffect(1);
        effect.setText("that creature's controller loses 1 life");
        this.addAbility(new AttacksAllTriggeredAbility(effect, false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PLAYER, true, true));
    }

    private BloodReckoning(final BloodReckoning card) {
        super(card);
    }

    @Override
    public BloodReckoning copy() {
        return new BloodReckoning(this);
    }
}

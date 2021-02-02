
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 *
 */
public final class Caltrops extends CardImpl {

    public Caltrops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a creature attacks, Caltrops deals 1 damage to it.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("{this} deals 1 damage to it");
        this.addAbility(new AttacksAllTriggeredAbility(effect, false, StaticFilters.FILTER_PERMANENT_CREATURE,
                SetTargetPointer.PERMANENT, false));
    }

    private Caltrops(final Caltrops card) {
        super(card);
    }

    @Override
    public Caltrops copy() {
        return new Caltrops(this);
    }
}

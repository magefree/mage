
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author daagar
 */
public final class RighteousCause extends CardImpl {

    public RighteousCause(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{W}");

        // Whenever a creature attacks, you gain 1 life.
        this.addAbility(new AttacksAllTriggeredAbility(new GainLifeEffect(1), false));
    }

    private RighteousCause(final RighteousCause card) {
        super(card);
    }

    @Override
    public RighteousCause copy() {
        return new RighteousCause(this);
    }
}

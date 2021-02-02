
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Derpthemeus
 */
public final class MoonlitWake extends CardImpl {

    public MoonlitWake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // Whenever a creature dies, you gain 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new GainLifeEffect(1), false));
    }

    private MoonlitWake(final MoonlitWake card) {
        super(card);
    }

    @Override
    public MoonlitWake copy() {
        return new MoonlitWake(this);
    }
}

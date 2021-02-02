
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventDamageByColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class SphereOfGrace extends CardImpl {

    public SphereOfGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // If a black source would deal damage to you, prevent 2 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageByColorEffect(ObjectColor.BLACK, 2)));
    }

    private SphereOfGrace(final SphereOfGrace card) {
        super(card);
    }

    @Override
    public SphereOfGrace copy() {
        return new SphereOfGrace(this);
    }
}

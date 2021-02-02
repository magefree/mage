
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
public final class SphereOfReason extends CardImpl {

    public SphereOfReason(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // If a blue source would deal damage to you, prevent 2 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageByColorEffect(ObjectColor.BLUE, 2)));
    }

    private SphereOfReason(final SphereOfReason card) {
        super(card);
    }

    @Override
    public SphereOfReason copy() {
        return new SphereOfReason(this);
    }
}

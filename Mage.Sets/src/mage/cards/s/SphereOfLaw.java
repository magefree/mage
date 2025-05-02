
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
public final class SphereOfLaw extends CardImpl {

    public SphereOfLaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // If a red source would deal damage to you, prevent 2 of that damage.
        this.addAbility(new SimpleStaticAbility(new PreventDamageByColorEffect(ObjectColor.RED, 2)));
    }

    private SphereOfLaw(final SphereOfLaw card) {
        super(card);
    }

    @Override
    public SphereOfLaw copy() {
        return new SphereOfLaw(this);
    }
}

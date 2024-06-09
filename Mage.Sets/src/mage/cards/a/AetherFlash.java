
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Quercitron
 */
public final class AetherFlash extends CardImpl {

    public AetherFlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Whenever a creature enters the battlefield, Aether Flash deals 2 damage to it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(2).setText("{this} deals 2 damage to it"),
                StaticFilters.FILTER_PERMANENT_A_CREATURE,
                false, SetTargetPointer.PERMANENT));
    }

    private AetherFlash(final AetherFlash card) {
        super(card);
    }

    @Override
    public AetherFlash copy() {
        return new AetherFlash(this);
    }
}

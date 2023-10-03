
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class Fervor extends CardImpl {

    public Fervor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, false)));
    }

    private Fervor(final Fervor card) {
        super(card);
    }

    @Override
    public Fervor copy() {
        return new Fervor(this);
    }
}

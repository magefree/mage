
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Backfir3
 */
public final class Bedlam extends CardImpl {

    public Bedlam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Creatures can't block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.WhileOnBattlefield)));
    }

    private Bedlam(final Bedlam card) {
        super(card);
    }

    @Override
    public Bedlam copy() {
        return new Bedlam(this);
    }
}

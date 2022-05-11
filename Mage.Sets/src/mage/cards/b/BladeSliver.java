
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author cbt33
 */
public final class BladeSliver extends CardImpl {

    public BladeSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Sliver creatures get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostAllEffect(1, 0, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS, false)));
    }

    private BladeSliver(final BladeSliver card) {
        super(card);
    }

    @Override
    public BladeSliver copy() {
        return new BladeSliver(this);
    }
}
